package com.dev.to_doappcleanarchitecture.ui.fragment.add

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.dev.to_doappcleanarchitecture.R
import com.dev.to_doappcleanarchitecture.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding

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
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }
}