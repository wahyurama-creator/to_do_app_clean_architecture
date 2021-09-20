package com.dev.to_doappcleanarchitecture.ui.fragment.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.to_doappcleanarchitecture.data.entity.ToDoData
import com.dev.to_doappcleanarchitecture.databinding.RowLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var dataList = emptyList<ToDoData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = dataList.size

    class ViewHolder(private val binding: RowLayoutBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun bind(toDoData: ToDoData) {
            binding.toDoData = toDoData
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater =
                    LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                return ViewHolder(binding)
            }
        }
    }

    fun setData(todoData: List<ToDoData>) {
        this.dataList = todoData
        notifyDataSetChanged()
    }

}