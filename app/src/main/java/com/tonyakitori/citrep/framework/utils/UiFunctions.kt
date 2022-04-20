package com.tonyakitori.citrep.framework.utils

import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso

fun AppCompatActivity.longToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun AppCompatActivity.shortToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.longToast(text: String) {
    Toast.makeText(this.requireContext(), text, Toast.LENGTH_LONG).show()
}

fun Fragment.shortToast(text: String) {
    Toast.makeText(this.requireContext(), text, Toast.LENGTH_SHORT).show()
}

infix fun ImageView.tint(color: Int?) {
    ImageViewCompat.setImageTintList(
        this,
        if (color != null) ColorStateList.valueOf(color) else null
    )
}

fun ImageView.loadUrl(url: String) {
    Picasso.get().load(url).into(this)
}