package com.example.translator.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.translator.R
import com.example.translator.domain.usesCases.MainInteractor
import com.example.translator.model.data.AppState
import com.example.translator.model.data.DataModel
import com.example.translator.presentation.ui.adapter.MainAdapter
import com.example.translator.presentation.ui.fragments.SearchDialogFragment
import com.example.translator.presentation.viewmodel.SearchOfflineViewModel
import com.example.translator.presentation.viewmodel.SearchOnlineViewModel
import com.example.translator.utils.convertMeaningsToString
import com.example.translator.utils.network.isNetworkAvailable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<AppState, MainInteractor>() {
    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "com.example.translator.presentation.ui.main.MainActivity.BOTTOM_SHEET_FRAGMENT_DIALOG_TAG"
    }

    override lateinit var model: SearchOnlineViewModel
    private val searchWordOfflineViewModel: SearchOfflineViewModel by viewModel()

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private val fabClickListener: View.OnClickListener =
        View.OnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()

            searchDialogFragment.setOnSearchClickListener(onSearchOnlineClickListener)
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)

            initSearchDialogFragment(onSearchOnlineClickListener)
        }

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                openDescriptionActivity(
                    data.word!!,
                    convertMeaningsToString(data.meanings!!),
                    data.meanings[0].imageUrl!!
                )
            }
        }

    private fun openDescriptionActivity(word: String, meanings: String?, imageUrl: String?) {
        startActivity(
            DescriptionActivity.getIntent(
                this@MainActivity,
                word,
                meanings,
                imageUrl
            )
        )
    }

    private val onSearchOnlineClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                isNetworkAvailable = isNetworkAvailable(applicationContext)
                if (isNetworkAvailable) {
                    model.getData(searchWord)
                } else {
                    showNoInternetConnectionDialog()
                }
            }
        }

    private val onSearchOfflineClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                searchWordOfflineViewModel.getData(searchWord)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initSearchOnlineViewModel()
        initSearchOfflineViewModel()
        initRecyclerView()

        search_fab.setOnClickListener(fabClickListener)
    }

    private fun initSearchOnlineViewModel() {
        val searchWordOnlineViewModel: SearchOnlineViewModel by viewModel()
        model = searchWordOnlineViewModel

        model.subscribe().observe(this@MainActivity, {
            renderData(it)
        })
    }

    private fun initSearchOfflineViewModel() {
        searchWordOfflineViewModel.subscribe().observe(this@MainActivity, {
            it?.let {
                openDescriptionActivity(
                    word = it.word,
                    meanings = it.translation,
                    imageUrl = it.imageUrl
                )
            } ?: let {
                Toast.makeText(this, "Вы не искаили это слово", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }

            R.id.menu_search_offline -> {
                initSearchDialogFragment(onSearchOfflineClickListener)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initSearchDialogFragment(listener: SearchDialogFragment.OnSearchClickListener) {
        val searchDialogFragment = SearchDialogFragment.newInstance()

        searchDialogFragment.setOnSearchClickListener(listener)
        searchDialogFragment.show(
            supportFragmentManager,
            BOTTOM_SHEET_FRAGMENT_DIALOG_TAG
        )
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private fun initRecyclerView() {
        with(activity_main_recyclerview) {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = this@MainActivity.adapter
        }
    }
}