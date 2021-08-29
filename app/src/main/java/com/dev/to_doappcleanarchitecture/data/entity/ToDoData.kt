package com.dev.to_doappcleanarchitecture.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.to_doappcleanarchitecture.data.Priority

@Entity(tableName = "todo_table")
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String
)