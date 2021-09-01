package com.dev.to_doappcleanarchitecture.ui.fragment.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dev.to_doappcleanarchitecture.R
import com.dev.to_doappcleanarchitecture.data.ToDoViewModel
import com.dev.to_doappcleanarchitecture.data.entity.ToDoData
import com.dev.to_doappcleanarchitecture.databinding.FragmentAddBinding
import com.dev.to_doappcleanarchitecture.ui.fragment.SharedViewModel

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val viewModel: ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(
            layoutInflater,
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

        setHasOptionsMenu(true)

        binding.spinnerPriorities.onItemSelectedListener =
            sharedViewModel.listener
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertData() {
        val mTitle = binding.etName.text.toString()
        val mPriority =
            binding.spinnerPriorities.selectedItem.toString()
        val mDescription = binding.etDescription.text.toString()

        val validation =
            sharedViewModel.validateData(mTitle, mDescription)
        if (validation) {
            val newData = ToDoData(
                0,
                mTitle,
                sharedViewModel.parsePriority(mPriority),
                mDescription
            )
            viewModel.insertData(newData)
            Toast.makeText(
                requireContext(),
                "Successfully added!",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(
                requireContext(),
                "Please fill out all fields",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    companion object {
        const val HIGH_PRIORITY = "High Priority"
        const val MEDIUM_PRIORITY = "Medium Priority"
        const val LOW_PRIORITY = "Low Priority"
    }
}