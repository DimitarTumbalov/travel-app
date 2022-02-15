package com.synergygfs.travelapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.synergygfs.travelapp.databinding.ItemCityBinding
import com.synergygfs.travelapp.ui.fragments.CitiesFragment
import kotlin.collections.ArrayList

class CitiesAdapter(
    private val citiesCollection: ArrayList<String>,
    private val citiesFragment: CitiesFragment
) :
    RecyclerView.Adapter<CitiesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemCityBinding =
            ItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val city = citiesCollection[position]
        holder.binding?.name?.text = city
    }

    override fun getItemCount() = citiesCollection.size

    class MyViewHolder(binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemCityBinding? = binding
    }

}