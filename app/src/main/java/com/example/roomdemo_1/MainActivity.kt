package com.example.roomdemo_1

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo_1.adapter.MyRecyclerViewAdapter
import com.example.roomdemo_1.databinding.ActivityMainBinding
import com.example.roomdemo_1.db.Subscriber
import com.example.roomdemo_1.db.SubscriberDatabase
import com.example.roomdemo_1.db.SubscriberRepository
import com.example.roomdemo_1.factory.SubScriberFactory
import com.example.roomdemo_1.viewmodel.SubscriberViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SubscriberViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubScriberFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this
        initLicyclerView()

        viewModel.message.observe(this, Observer {
            it.getContentIfNotHandled().let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun listItemClick(subscriber: Subscriber) {
//        Toast.makeText(this,"click ok ${subscriber.name}",Toast.LENGTH_SHORT).show()
        viewModel.initUpdateAndDelete(subscriber)
    }

    private fun initLicyclerView() {
        binding.SubscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter { listItem: Subscriber ->
            listItemClick(listItem)
        }
        binding.SubscriberRecyclerView.adapter = adapter
        displaySubscribersList()
    }

    private fun displaySubscribersList() {
        viewModel.subscriber.observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }
}