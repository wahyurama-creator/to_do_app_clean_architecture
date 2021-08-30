package com.dev.to_doappcleanarchitecture.data.utils

import androidx.room.TypeConverter
import com.dev.to_doappcleanarchitecture.data.Priority

class Converter {
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}