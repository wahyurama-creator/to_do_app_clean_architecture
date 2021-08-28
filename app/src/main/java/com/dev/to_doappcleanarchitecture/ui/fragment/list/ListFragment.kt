package com.dev.to_doappcleanarchitecture.ui.fragment.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev.to_doappcleanarchitecture.R
import com.dev.to_doappcleanarchitecture.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

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

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        binding.root.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }
}