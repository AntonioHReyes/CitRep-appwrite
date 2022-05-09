package com.tonyakitori.citrep.framework.ui.dialogs

import android.app.Dialog
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.tonyakitori.citrep.R
import com.tonyakitori.citrep.databinding.DialogVerificationBinding

class VerificationDialog(context: Context) : Dialog(context, R.style.CustomDialogTheme) {

    private val binding by lazy { DialogVerificationBinding.inflate(layoutInflater) }

    var dialogStatus: VerificationStatus = VerificationStatus.PROGRESS
        set(value) {
            field = value
            handleViewWithStatus()
        }

    init {
        setContentView(binding.root)
        setCancelable(false)
    }

    fun showDialog() {
        handleViewWithStatus()
        show()
    }

    private fun handleViewWithStatus() = with(binding) {
        when (dialogStatus) {
            VerificationStatus.PROGRESS -> {
                progress.isVisible = true
                bindTexts(
                    "Verificando tu email",
                    "Estamos verificando tu email, este proceso puede tomar unos segundos"
                )
            }
            VerificationStatus.SUCCESS -> {
                progress.isVisible = false
                bindTexts("Verificación exitosa", "Tu correo ha sido verificado de manera exitosa")
            }
            VerificationStatus.ERROR -> {
                progress.isVisible = false
                bindTexts(
                    "Error en la verificación",
                    "Lo sentimos no fue posible verificar tu email, intentalo de nuevo mas tarde",
                    R.color.red
                )
            }
        }
    }

    private fun bindTexts(title: String, content: String, color: Int? = null) = with(binding) {
        dialogTitle.text = title
        dialogContent.text = content

        color?.let {
            dialogTitle.setTextColor(ContextCompat.getColor(context, color))
        }
    }

    enum class VerificationStatus {
        PROGRESS, SUCCESS, ERROR
    }

}