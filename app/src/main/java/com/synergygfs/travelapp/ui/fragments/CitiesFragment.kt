package com.synergygfs.travelapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.synergygfs.travelapp.R
import com.synergygfs.travelapp.ui.adapters.CitiesAdapter
import com.synergygfs.travelapp.databinding.FragmentCitiesBinding

class CitiesFragment: Fragment() {

    lateinit var binding: FragmentCitiesBinding

    private var adapter: CitiesAdapter? = null

    var citiesCollection = arrayListOf("New York", "San Diego", "Washington")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        (activity as AppCompatActivity).supportActionBar?.title = "Travel App"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_cities, container, false
        )

        val citiesRv = binding.citiesRv
        val lm = LinearLayoutManager(requireContext())
        citiesRv.layoutManager = lm
        adapter = CitiesAdapter(citiesCollection, this)
        citiesRv.adapter = adapter

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_cities, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        const val TAG = "CitiesFragment"
    }

}