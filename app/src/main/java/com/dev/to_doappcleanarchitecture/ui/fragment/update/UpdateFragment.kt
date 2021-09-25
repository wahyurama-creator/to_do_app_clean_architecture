package com.dev.to_doappcleanarchitecture.ui.fragment.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dev.to_doappcleanarchitecture.R
import com.dev.to_doappcleanarchitecture.data.entity.ToDoData
import com.dev.to_doappcleanarchitecture.databinding.FragmentUpdateBinding
import com.dev.to_doappcleanarchitecture.ui.viewmodel.SharedViewModel
import com.dev.to_doappcleanarchitecture.ui.viewmodel.ToDoViewModel

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(
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
        binding.args = args

        setHasOptionsMenu(true)

        binding.currentSpinnerPriorities.onItemSelectedListener =
            mSharedViewModel.listener
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmRemoveItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoveItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete '${args.currentItem.title}'?")
        builder.setMessage("Are you sure to remove '${args.currentItem.title}'?")
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteData(args.currentItem)
            Toast.makeText(
                requireContext(),
                "Successfully removed! ${args.currentItem.title}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.create().show()
    }

    private fun updateItem() {
        val title = binding.currentEtName.text.toString()
        val description = binding.currentEtDescription.text.toString()
        val getPriority =
            binding.currentSpinnerPriorities.selectedItem.toString()

        if (mSharedViewModel.validateData(title, description)) {
            val updateItem = ToDoData(
                args.currentItem.id,
                title,
                mSharedViewModel.parsePriority(getPriority),
                description
            )

            mToDoViewModel.updateData(updateItem)
            Toast.makeText(
                requireContext(),
                "Successfully updated!",
                Toast.LENGTH_SHORT
            ).show()

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(
                requireContext(),
                "Please fill out all fields!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}