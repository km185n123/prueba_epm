package com.prueba.julianlopez.appintergrupo.Login.view.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.prueba.julianlopez.appintergrupo.Login.view.adapter.LogAdapter
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.Log
import com.prueba.julianlopez.appintergrupo.Login.viewmodel.LogViewModel
import com.prueba.julianlopez.appintergrupo.R
import kotlinx.android.synthetic.main.content_dash_board.*

class LogActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_dash_board)
        setTitle("LogDao")

         loadRecyclerView()

        val logViewModel:LogViewModel = ViewModelProviders.of(this).get(LogViewModel::class.java)
        logViewModel.getListLog().observe(this, Observer {

            loadDataInAdapter(it)

        })


    }

    private fun loadRecyclerView() {

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        prospectRecycler.layoutManager = layoutManager
        prospectRecycler.setHasFixedSize(true)
    }

    private fun loadDataInAdapter(it: List<Log>?) {

        val ServicesAdapter = it?.let { it1 -> LogAdapter(this, it1) }
        prospectRecycler.adapter = ServicesAdapter
    }
}
