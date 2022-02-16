package com.synergygfs.travelapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        // Set up the CitiesAdapter
        val citiesRv = binding.citiesRv
        val lm = LinearLayoutManager(requireContext())
        citiesRv.layoutManager = lm
        adapter = CitiesAdapter(this, citiesCollection)
        citiesRv.adapter = adapter

        // Add dividers between RecyclerView items
        val dividerItemDecoration = DividerItemDecoration(
            citiesRv.context,
            lm.orientation
        )
        citiesRv.addItemDecoration(dividerItemDecoration)

        // Update the RecyclerView UI
        binding.noCities.isVisible = adapter?.itemCount ?: 0 < 1

        // Register an observer to the adapter so it can update the RecyclerView UI
        adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)

                binding.noCities.isVisible = adapter?.itemCount ?: 0 < 1
            }

            override fun onChanged() {
                super.onChanged()

                binding.noCities.isVisible = adapter?.itemCount ?: 0 < 1
            }
        })

        binding.addCityBtn.setOnClickListener {
            activity?.let {
                if (UiUtils.getFragmentByTag(activity, AddCityFragment.TAG) == null) {
                    UiUtils.addFragment(
                        activity,
                        R.id.fragment_container,
                        AddCityFragment(),
                        AddCityFragment.TAG,
                        true
                    )
                }
            }
        }

        return binding.root
    }

    companion object {
        const val TAG = "CitiesFragment"
    }

}