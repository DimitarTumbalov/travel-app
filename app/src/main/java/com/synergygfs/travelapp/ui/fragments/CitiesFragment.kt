package com.synergygfs.travelapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.synergygfs.travelapp.R
import com.synergygfs.travelapp.data.models.City
import com.synergygfs.travelapp.databinding.FragmentCitiesBinding
import com.synergygfs.travelapp.ui.MainActivity
import com.synergygfs.travelapp.ui.UiUtils
import com.synergygfs.travelapp.ui.adapters.CitiesAdapter
import java.util.*

class CitiesFragment : Fragment() {

    private lateinit var binding: FragmentCitiesBinding

    var adapter: CitiesAdapter? = null

    var citiesCollection = Vector<City>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_cities, container, false
        )

        val activity = activity as MainActivity?

        citiesCollection = activity?.dbHelper?.getAllCities() ?: Vector()

        val citiesRv = binding.citiesRv
        citiesRv.layoutManager = LinearLayoutManager(requireContext())
        adapter = CitiesAdapter(this, citiesCollection)
        citiesRv.adapter = adapter

        binding.addCityBtn.setOnClickListener {
            activity?.let {
                UiUtils.addFragment(
                    activity,
                    R.id.fragment_container,
                    activity.addCitiesFragment,
                    AddCityFragment.TAG,
                    true
                )
            }
        }

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