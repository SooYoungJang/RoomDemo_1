package com.example.roomdemo_1.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdemo_1.db.Subscriber
import com.example.roomdemo_1.db.SubscriberRepository
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel(),Observable {
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    val subscriber = repository.subscriber

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()

    val clearOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        val name = inputName.value!!
        val email = inputEmail.value!!
        insert(Subscriber(0,name,email))
        inputEmail.value = null
        inputName.value = null
    }
    fun clearAllOrDelete() {
        clearAll()
    }

    fun insert(subscriber: Subscriber) {
        viewModelScope.launch {
            repository.insert(subscriber)
        }
    }

    fun update(subscriber: Subscriber) {
        viewModelScope.launch {
            repository.update(subscriber)
        }
    }
    fun delete(subscriber: Subscriber) {
        viewModelScope.launch {
            repository.delete(subscriber)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

}