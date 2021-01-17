package com.example.translator.utils.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

class AlertDialogFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var alertDialog = getStubAlertDialog(requireContext())
        arguments?.let {
            val title = it.getString(TITLE_EXTRA)
            val message = it.getString(MESSAGE_EXTRA)

            alertDialog = getAlertDialog(requireContext(), title, message)
        }

        return alertDialog
    }

    companion object {

        private const val TITLE_EXTRA =
            "com.example.translator.utils.ui.AlertDialogFragment.TITLE_EXTRA"
        private const val MESSAGE_EXTRA =
            "com.example.translator.utils.ui.AlertDialogFragment.MESSAGE_EXTRA"

        fun newInstance(title: String?, message: String?): AlertDialogFragment {
            val dialogFragment = AlertDialogFragment()
            val args = Bundle()

            args.putString(TITLE_EXTRA, title)
            args.putString(MESSAGE_EXTRA, message)

            dialogFragment.arguments = args
            return dialogFragment
        }
    }
}