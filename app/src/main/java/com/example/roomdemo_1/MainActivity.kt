package com.example.roomdemo_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.roomdemo_1.databinding.ActivityMainBinding
import com.example.roomdemo_1.db.SubscriberDatabase
import com.example.roomdemo_1.db.SubscriberRepository
import com.example.roomdemo_1.factory.SubScriberFactory
import com.example.roomdemo_1.viewmodel.SubscriberViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SubscriberViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubScriberFactory(repository)
        viewModel = ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this
        displaySubscribersList()

    }
    private fun displaySubscribersList() {
        viewModel.subscriber.observe(this, Observer {
            Log.d("tgggg"," test value = ${it.toString()}")
        })
    }
}