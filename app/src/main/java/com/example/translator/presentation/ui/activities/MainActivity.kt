package com.example.translator.presentation.ui.activities


import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.translator.R
import com.example.translator.model.data.DataModel
import com.example.translator.model.data.SearchResult
import com.example.translator.presentation.presenters.MainPresenterImpl
import com.example.translator.presentation.presenters.Presenter
import com.example.translator.presentation.ui.adapter.MainAdapter
import com.example.translator.presentation.ui.fragments.SearchDialogFragment
import com.example.translator.presentation.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<DataModel>() {

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }

    private var adapter: MainAdapter? = null
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: SearchResult) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setOnSearchFabClick()
    }

    private fun setOnSearchFabClick() {
        search_fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    presenter.getData(searchWord, true)
                }
            })

            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    override fun createPresenter(): Presenter<DataModel, View> = MainPresenterImpl()

    override fun renderData(dataModel: DataModel) {
        when (dataModel) {
            is DataModel.Success -> {
                val searchResult = dataModel.data

                if (searchResult == null || searchResult.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()

                    if (adapter == null) {
                        with(main_activity_recyclerview) {
                            layoutManager = LinearLayoutManager(applicationContext)
                            adapter = MainAdapter(onListItemClickListener, searchResult)
                        }
                    } else {
                        adapter!!.setData(searchResult)
                    }
                }
            }

            is DataModel.Loading -> {
                showViewLoading()

                dataModel.progress?.let {
                    with(progress_bar_horizontal) {
                        visibility = VISIBLE
                        progress_bar_round.visibility = GONE
                        progress = dataModel.progress!!
                    }
                }?.let {
                    progress_bar_horizontal.visibility = GONE
                    progress_bar_round.visibility = VISIBLE
                }
            }

            is DataModel.Error -> {
                showErrorScreen(dataModel.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()

        error_textview.text = error ?: getString(R.string.undefined_error)

        reload_button.setOnClickListener {
            presenter.getData("hi", true)
        }
    }

    private fun showViewSuccess() {
        success_linear_layout.visibility = VISIBLE
        loading_frame_layout.visibility = GONE
        error_linear_layout.visibility = GONE
    }

    private fun showViewLoading() {
        success_linear_layout.visibility = GONE
        loading_frame_layout.visibility = VISIBLE
        error_linear_layout.visibility = GONE
    }

    private fun showViewError() {
        success_linear_layout.visibility = GONE
        loading_frame_layout.visibility = GONE
        error_linear_layout.visibility = VISIBLE
    }
}