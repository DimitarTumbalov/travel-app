package com.synergygfs.travelapp.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.synergygfs.travelapp.R
import com.synergygfs.travelapp.databinding.FragmentAddCityBinding

class AddCityFragment: Fragment() {

    lateinit var binding: FragmentAddCityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_bot)
        exitTransition = inflater.inflateTransition(R.transition.slide_top)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_city, container, false
        )

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

}