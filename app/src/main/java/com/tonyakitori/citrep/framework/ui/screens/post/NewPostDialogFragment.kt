package com.tonyakitori.citrep.framework.ui.screens.post

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.tonyakitori.citrep.R
import com.tonyakitori.citrep.databinding.FragmentNewPostDialogBinding
import com.tonyakitori.citrep.domain.exceptions.StorageExceptions
import com.tonyakitori.citrep.framework.ui.adapters.PostEvidenceAdapter
import com.tonyakitori.citrep.framework.ui.helpers.CaptureImagesFragment
import com.tonyakitori.citrep.framework.ui.screens.post.NewPostDialogViewModel.NewPostActions
import com.tonyakitori.citrep.framework.ui.screens.post.NewPostDialogViewModel.NewPostActions.ERROR_UPLOAD_IMAGE
import com.tonyakitori.citrep.framework.utils.checkPermissions
import com.tonyakitori.citrep.framework.utils.longToast
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewPostDialogFragment : CaptureImagesFragment() {

    private val newPostViewModel: NewPostDialogViewModel by viewModel()

    private lateinit var binding: FragmentNewPostDialogBinding

    private val bindingContext by lazy { binding.root.context }
    private val adapter by lazy { PostEvidenceAdapter(bindingContext) }

    override val compressCameraCapturedImages: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewPostDialogBinding.inflate(inflater, container, false)

        setUpObservers()
        setUpViews()

        return binding.root
    }

    private fun setUpObservers() {
        newPostViewModel.evidenceList.observe(viewLifecycleOwner, ::handleEvidencesList)
        newPostViewModel.addEvidenceError.observe(viewLifecycleOwner, ::handleAddEvidenceError)
    }

    private fun handleEvidencesList(list: ArrayList<Uri>) = with(binding) {
        itemImages.apply {
            try {
                adapter.addNewEvidences(list)
            } catch (e: StorageExceptions.MaxFilesLoaded) {
                longToast(getString(R.string.max_evidences_loaded))
            }
        }
    }

    private fun handleAddEvidenceError(action: NewPostActions?) {
        when (action) {
            ERROR_UPLOAD_IMAGE -> {
                longToast(getString(R.string.error_get_image))
            }
            null -> {}
        }
    }

    private fun setUpViews() = with(binding) {
        toolbarPost.apply {
            toolbarClose.setOnClickListener { dismiss() }
            toolbarButton.setOnClickListener { newPostViewModel.startPostComplain() }
        }

        itemImages.apply {

            rcvImagesAttach.adapter = adapter

            attachFile.mainContainer.setOnClickListener {
                showImageSelector()
            }
        }
    }

    private fun showImageSelector() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        pictureDialog.setTitle("Selecciona una opción").setCancelable(false)
        val pictureDialogItems =
            arrayOf("Seleccionar imagen de la galeria", "Tomar una foto", "Cancelar")
        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> choosePhotoFromGallery()
                1 -> {
                    try {
                        takePhotoFromCamera()
                    } catch (e: SecurityException) {
                        checkPermissions(listOf(Manifest.permission.CAMERA)) {
                            if (it) {
                                takePhotoFromCamera()
                            } else {
                                longToast(bindingContext.getString(R.string.give_persmissions))
                            }
                        }
                    }
                }
                2 -> {}
            }
        }
        pictureDialog.show()
    }

    override fun onCameraImageLoaded(imageUri: Uri, imageCompress: Bitmap?) {
        imageCompress?.let {
            newPostViewModel.addEvidenceToList(it)
        } ?: run {
            newPostViewModel.addEvidenceToList(uri = imageUri)
        }
    }

    override fun onGalleryImageLoaded(imageUri: Uri) {
        newPostViewModel.addEvidenceToList(uri = imageUri)
    }
}