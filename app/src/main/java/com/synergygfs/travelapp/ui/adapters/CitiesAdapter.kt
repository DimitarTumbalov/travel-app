package com.synergygfs.travelapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.synergygfs.travelapp.data.TravelAppContractContract
import com.synergygfs.travelapp.data.models.City
import com.synergygfs.travelapp.databinding.ItemCityBinding
import com.synergygfs.travelapp.ui.MainActivity
import com.synergygfs.travelapp.ui.fragments.CitiesFragment
import java.util.*

class CitiesAdapter(
    private val citiesFragment: CitiesFragment,
    private var citiesCollection: Vector<City>
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

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val activity = citiesFragment.activity as MainActivity?

        val city = citiesCollection[position]

        holder.binding?.apply {
            name.text = city.name
            description.text = city.description
        }

        holder.itemView.setOnLongClickListener {
            val deletedRow = activity?.dbHelper?.deleteById(
                TravelAppContractContract.CityEntity.TABLE_NAME,
                city.id
            )

            if (deletedRow != null && deletedRow > -1) {
                activity.citiesFragment.apply {
                    val cityToDeleteIndex =
                        citiesCollection.indexOf(citiesCollection.find { it.id == city.id })
                    citiesCollection.removeAt(cityToDeleteIndex)
                    adapter?.notifyItemRemoved(cityToDeleteIndex)
                }

                Toast.makeText(activity, "Row delete", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(activity, "Deletion failed", Toast.LENGTH_SHORT).show()

            true
        }
    }

    class MyViewHolder(binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemCityBinding? = binding
    }

    override fun getItemCount() = citiesCollection.size

}