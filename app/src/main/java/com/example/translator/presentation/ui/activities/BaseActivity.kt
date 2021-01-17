package com.example.translator.presentation.ui.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.translator.R
import com.example.translator.domain.usesCases.IMainInteractor
import com.example.translator.model.data.AppState
import com.example.translator.model.data.DataModel
import com.example.translator.presentation.viewmodel.BaseViewModel
import com.example.translator.utils.network.isNetworkAvailable
import com.example.translator.utils.ui.AlertDialogFragment
import kotlinx.android.synthetic.main.loading_layout.*

abstract class BaseActivity<T : AppState, I : IMainInteractor> : AppCompatActivity() {

    companion object {
        private const val DIALOG_FRAGMENT_TAG =
            "com.example.translator.presentation.ui.base.BaseActivity.DIALOG_FRAGMENT_TAG"
    }

    abstract val model: BaseViewModel<T>

    protected var isNetworkAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        isNetworkAvailable = isNetworkAvailable(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        isNetworkAvailable = isNetworkAvailable(applicationContext)
        if (!isNetworkAvailable && isDialogNull()) {
            showNoInternetConnectionDialog()
        }
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    private fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message)
            .show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun isDialogNull(): Boolean {
        return supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    abstract fun setDataToAdapter(data: List<DataModel>)

    protected fun renderData(appState: T) {
        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                appState.data?.let {
                    if (it.isEmpty()) {
                        showAlertDialog(
                            getString(R.string.dialog_tittle_sorry),
                            getString(R.string.empty_server_response_on_success)
                        )
                    } else {
                        setDataToAdapter(it)
                    }
                }
            }

            is AppState.Loading -> {
                showViewLoading()
                appState.progress?.let {
                    progress_bar_horizontal.visibility = View.VISIBLE
                    progress_bar_round.visibility = View.GONE
                    progress_bar_horizontal.progress = appState.progress
                }?.let {
                    progress_bar_horizontal.visibility = View.GONE
                    progress_bar_round.visibility = View.VISIBLE
                }
            }

            is AppState.Error -> {
                showViewWorking()
                showAlertDialog(getString(R.string.error_stub), appState.error.message)
            }
        }
    }

    private fun showViewWorking() {
        loading_frame_layout.visibility = View.GONE
    }

    private fun showViewLoading() {
        loading_frame_layout.visibility = View.VISIBLE
    }
}