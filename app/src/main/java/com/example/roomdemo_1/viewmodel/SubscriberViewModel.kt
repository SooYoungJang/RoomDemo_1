package com.example.roomdemo_1.viewmodel

import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdemo_1.db.Subscriber
import com.example.roomdemo_1.db.SubscriberRepository
import com.example.roomdemo_1.event.Event
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel(), Observable {
    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    val subscriber = repository.subscriber
    var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateAndDelete: Subscriber

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()

    val clearOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        if (isUpdateOrDelete) {
            subscriberToUpdateAndDelete.name = inputName.value!!
            subscriberToUpdateAndDelete.email = inputEmail.value!!
            update(subscriberToUpdateAndDelete)
        } else {
            val name = inputName.value!!
            val email = inputEmail.value!!
            insert(Subscriber(0, name, email))
            inputEmail.value = null
            inputName.value = null
        }

    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateAndDelete)
        } else {
            clearAll()
        }
    }

    fun insert(subscriber: Subscriber) {
        viewModelScope.launch {
            val newRowId = repository.insert(subscriber)
            if (newRowId > -1) {
                statusMessage.value = Event("SubScriber Inserted Susccessfully $newRowId")
            } else {
                statusMessage.value = Event("Error Occurred")
            }
        }
    }

    fun update(subscriber: Subscriber) {
        viewModelScope.launch {
            val rowId = repository.update(subscriber)
            if (rowId > 0) {
                inputName.value = null
                inputEmail.value = null
                isUpdateOrDelete = false
                saveOrUpdateButtonText.value = "Save"
                clearOrDeleteButtonText.value = "Clear All"
                statusMessage.value = Event("SubScriber Updated Susccessfully $rowId ")
            } else {
                statusMessage.value = Event("Error Updated Occurred")
            }
        }
    }

    fun delete(subscriber: Subscriber) {
        viewModelScope.launch {
            val rowId = repository.delete(subscriber)
            if (rowId > 0) {
                inputName.value = null
                inputEmail.value = null
                isUpdateOrDelete = false
                saveOrUpdateButtonText.value = "Save"
                clearOrDeleteButtonText.value = "Clear All"
                statusMessage.value = Event("SubScriber Deleted Susccessfully $rowId")
            } else {
                statusMessage.value = Event("SubScriber Deleted Occurred")
            }
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            val rowId = repository.deleteAll()
            if (rowId > 0) {
                statusMessage.value = Event("SubScribers clearAll Susccessfully $rowId")
            } else {
                statusMessage.value = Event("SubScribers clearAll Occurred")
            }
        }
    }

    fun initUpdateAndDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateAndDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearOrDeleteButtonText.value = "Delete"
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}