package com.dev.to_doappcleanarchitecture.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dev.to_doappcleanarchitecture.data.db.ToDoDatabase
import com.dev.to_doappcleanarchitecture.data.entity.ToDoData
import com.dev.to_doappcleanarchitecture.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) :
    AndroidViewModel(application) {
    private val toDoDao =
        ToDoDatabase.getInstance(application).toDoDao()
    private val repository: ToDoRepository = ToDoRepository(toDoDao)

    val getAllData: LiveData<List<ToDoData>> =
        repository.getAllData

    val sortByHighPriority: LiveData<List<ToDoData>> =
        repository.sortByHighPriority
    val sortByLowPriority: LiveData<List<ToDoData>> =
        repository.sortByLowPriority

    fun insertData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoData)
        }
    }

    fun updateData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(toDoData)
        }
    }

    fun deleteData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(toDoData)
        }
    }

    fun deleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
    }

    fun searchData(query: String): LiveData<List<ToDoData>> {
        return repository.searchData(query)
    }
}