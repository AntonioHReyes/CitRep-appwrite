package com.tonyakitori.citrep.framework.ui.helpers

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import com.tonyakitori.citrep.R
import com.tonyakitori.citrep.framework.utils.CompressUtils
import com.tonyakitori.citrep.framework.utils.longToast
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

abstract class CaptureImagesFragment : DialogFragment() {

    private var outputFileUri: Uri? = null
    abstract val compressCameraCapturedImages: Boolean

    private val cameraRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when (it.resultCode) {
                AppCompatActivity.RESULT_OK -> {
                    handleCameraData()
                }
                else -> {}
            }
        }

    private val galleryRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            when (it.resultCode) {

                AppCompatActivity.RESULT_OK -> {
                    val selectedImage = it?.data?.data

                    selectedImage?.let { uri ->
                        onGalleryImageLoaded(uri)
                    }
                }

                else -> {}
            }

        }

    private fun handleCameraData() {
        if (compressCameraCapturedImages) {
            outputFileUri?.let { uri ->
                val bitmapCompressed = uri.path?.let { path ->
                    CompressUtils.compressImage(path)
                }

                onCameraImageLoaded(uri, bitmapCompressed)
            } ?: run {
                longToast(getString(R.string.capture_image_error))
            }
        } else {
            outputFileUri?.let { uri -> onCameraImageLoaded(uri) } ?: run {
                longToast(getString(R.string.capture_image_error))
            }
        }
    }

    fun choosePhotoFromGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        galleryRequest.launch(galleryIntent)
    }

    fun takePhotoFromCamera() {
        val file = createImageFile()

        val output = FileProvider.getUriForFile(
            requireContext(),
            "${this.requireContext().packageName}.provider",
            file ?: this.requireContext().cacheDir
        )
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        intent.putExtra(MediaStore.EXTRA_OUTPUT, output)
        cameraRequest.launch(intent)

    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"

        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            this.requireContext().cacheDir /* directory */
        )

        outputFileUri = image.toUri()

        return image
    }


    abstract fun onCameraImageLoaded(imageUri: Uri, imageCompress: Bitmap? = null)
    abstract fun onGalleryImageLoaded(imageUri: Uri)

}