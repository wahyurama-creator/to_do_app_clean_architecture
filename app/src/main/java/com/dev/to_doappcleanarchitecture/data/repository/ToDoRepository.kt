package com.dev.to_doappcleanarchitecture.data.repository

import androidx.lifecycle.LiveData
import com.dev.to_doappcleanarchitecture.data.db.ToDoDao
import com.dev.to_doappcleanarchitecture.data.entity.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData) =
        toDoDao.insertData(toDoData)

    suspend fun updateData(toDoData: ToDoData) =
        toDoDao.updateData(toDoData)

    suspend fun deleteData(toDoData: ToDoData) =
        toDoDao.deleteData(toDoData)

    suspend fun deleteAllData() = toDoDao.deleteAllData()
}