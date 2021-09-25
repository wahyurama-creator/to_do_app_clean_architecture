package com.dev.to_doappcleanarchitecture.ui.fragment.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.to_doappcleanarchitecture.R
import com.dev.to_doappcleanarchitecture.data.entity.ToDoData
import com.dev.to_doappcleanarchitecture.databinding.FragmentListBinding
import com.dev.to_doappcleanarchitecture.ui.fragment.list.adapter.SwipeToDelete
import com.dev.to_doappcleanarchitecture.ui.viewmodel.SharedViewModel
import com.dev.to_doappcleanarchitecture.ui.viewmodel.ToDoViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentListBinding
    private val listAdapter: ListAdapter by lazy { ListAdapter() }
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        binding.recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }

            swipeToDelete(this)
        }

        mToDoViewModel.getAllData.observe(
            viewLifecycleOwner,
            { data ->
                mSharedViewModel.checkDatabaseEmpty(data)
                listAdapter.setData(data)
            })

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        inflater.inflate(R.menu.list_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> deleteAllData()
            R.id.menu_priority_high -> {
                mToDoViewModel.sortByHighPriority.observe(
                    this,
                    { listData ->
                        listAdapter.setData(listData)
                    })
            }
            R.id.menu_priority_low -> {
                mToDoViewModel.sortByLowPriority.observe(this, {
                    listAdapter.setData(it)
                })
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllData() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteAllData()
            Toast.makeText(
                requireContext(),
                "Successfully removed everything!",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure to remove everything?")
        builder.create().show()
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val itemToDelete =
                    listAdapter.dataList[viewHolder.adapterPosition]
                mToDoViewModel.deleteData(itemToDelete)
                listAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                restoreDeletedData(
                    viewHolder.itemView,
                    itemToDelete,
                    viewHolder.adapterPosition
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(
        view: View,
        item: ToDoData,
        position: Int
    ) {
        val snackBar =
            Snackbar.make(view, "Deleted ${item.title}", LENGTH_SHORT)
                .setAction("Undo") {
                    mToDoViewModel.insertData(item)
                    listAdapter.notifyItemChanged(position)
                }
                .show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {
        var searchQuery = query
        searchQuery = "%$searchQuery%"

        mToDoViewModel.searchData(searchQuery)
            .observe(this, { listData ->
                listData?.let {
                    listAdapter.setData(it)
                }
            })
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }
}