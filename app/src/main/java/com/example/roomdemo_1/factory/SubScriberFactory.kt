package com.example.roomdemo_1.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomdemo_1.db.SubscriberRepository
import com.example.roomdemo_1.viewmodel.SubscriberViewModel
import java.lang.IllegalArgumentException

class SubScriberFactory(private val repository: SubscriberRepository) : ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SubscriberViewModel::class.java)) {
            return SubscriberViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}