package com.example.translator.presentation.ui.activities

import android.os.Bundle
import com.example.translator.R
import com.example.translator.domain.usesCases.MainInteractor
import com.example.translator.model.data.AppState
import com.example.translator.model.data.DataModel
import com.example.translator.presentation.ui.adapter.HistoryAdapter
import com.example.translator.presentation.viewmodel.HistoryViewModel
import kotlinx.android.synthetic.main.activity_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : BaseActivity<AppState, MainInteractor>() {
    override lateinit var model: HistoryViewModel

    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        iniViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        model.getData("")
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private fun iniViewModel() {
        if (activity_history_recyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }

        val viewModel: HistoryViewModel by viewModel()

        model = viewModel
        model.subscribe().observe(this@HistoryActivity, { renderData(it) })
    }

    private fun initViews() {
        activity_history_recyclerview.adapter = adapter
    }
}