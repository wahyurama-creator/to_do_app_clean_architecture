package com.dev.to_doappcleanarchitecture.ui.fragment

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.dev.to_doappcleanarchitecture.R
import com.dev.to_doappcleanarchitecture.data.Priority
import com.dev.to_doappcleanarchitecture.ui.fragment.add.AddFragment

class SharedViewModel(application: Application) :
    AndroidViewModel(application) {
    fun validateData(
        title: String,
        description: String
    ): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(
                description
            )
        ) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

    fun parsePriority(priority: String): Priority {
        return when (priority) {
            AddFragment.HIGH_PRIORITY -> Priority.HIGH
            AddFragment.MEDIUM_PRIORITY -> Priority.MEDIUM
            AddFragment.LOW_PRIORITY -> Priority.LOW
            else -> Priority.LOW
        }
    }

    val listener: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        (parent?.getChildAt(0) as TextView).setTextColor(
                            ContextCompat.getColor(
                                application,
                                R.color.red
                            )
                        )
                    }
                    1 -> {
                        (parent?.getChildAt(0) as TextView).setTextColor(
                            ContextCompat.getColor(
                                application,
                                R.color.yellow
                            )
                        )
                    }
                    2 -> {
                        (parent?.getChildAt(0) as TextView).setTextColor(
                            ContextCompat.getColor(
                                application,
                                R.color.green
                            )
                        )
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
}