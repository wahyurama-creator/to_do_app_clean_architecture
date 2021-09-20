package com.dev.to_doappcleanarchitecture.ui.fragment

import android.os.Build
import android.view.View
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.dev.to_doappcleanarchitecture.R
import com.dev.to_doappcleanarchitecture.data.entity.ToDoData
import com.dev.to_doappcleanarchitecture.data.utils.Priority
import com.dev.to_doappcleanarchitecture.ui.fragment.list.ListFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapter {
    companion object {
        @BindingAdapter("navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(
            button: FloatingActionButton,
            navigate: Boolean
        ) {
            button.setOnClickListener {
                if (navigate) {
                    button.findNavController()
                        .navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(
            view: View,
            data: MutableLiveData<Boolean>
        ) {
            when (data.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority) {
            when (priority) {
                Priority.HIGH -> {
                    view.setSelection(0)
                }
                Priority.MEDIUM -> {
                    view.setSelection(1)
                }
                Priority.LOW -> {
                    view.setSelection(2)
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(view: CardView, priority: Priority) {
            when (priority) {
                Priority.HIGH -> {
                    view.setCardBackgroundColor(
                        view.context.getColor(
                            R.color.red
                        )
                    )
                }
                Priority.MEDIUM -> {
                    view.setCardBackgroundColor(
                        view.context.getColor(
                            R.color.yellow
                        )
                    )
                }
                Priority.LOW -> {
                    view.setCardBackgroundColor(
                        view.context.getColor(
                            R.color.green
                        )
                    )
                }
            }
        }

        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(
            view: ConstraintLayout,
            currentItem: ToDoData
        ) {
            view.setOnClickListener {
                val action =
                    ListFragmentDirections.actionListFragmentToUpdateFragment(
                        currentItem
                    )
                view.findNavController().navigate(action)
            }
        }
    }
}