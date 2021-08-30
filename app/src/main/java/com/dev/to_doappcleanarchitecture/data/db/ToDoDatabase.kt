package com.dev.to_doappcleanarchitecture.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dev.to_doappcleanarchitecture.data.entity.ToDoData
import com.dev.to_doappcleanarchitecture.data.utils.Converter

@Database(
    entities = [ToDoData::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao

    companion object {
        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getInstance(context: Context): ToDoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "todo_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                // return
                instance
            }
        }
    }
}