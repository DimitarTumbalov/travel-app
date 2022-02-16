package com.synergygfs.travelapp.ui.adapters

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.synergygfs.travelapp.Constants.Companion.DELETION_ERROR_CITY_WITH_LANDMARKS
import com.synergygfs.travelapp.R
import com.synergygfs.travelapp.data.models.City
import com.synergygfs.travelapp.databinding.ItemCityBinding
import com.synergygfs.travelapp.ui.MainActivity
import com.synergygfs.travelapp.ui.UiUtils
import com.synergygfs.travelapp.ui.fragments.CitiesFragment
import com.synergygfs.travelapp.ui.fragments.CityFragment
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
        val itemView = holder.itemView

        val city = citiesCollection[position]

        holder.binding?.apply {
            name.text = city.name
            description.text = city.description
        }

        // Open city fragment
        itemView.setOnClickListener {
            activity?.let { activity ->
                if (UiUtils.getFragmentByTag(activity, CityFragment.TAG) == null) {
                    val cityFragment = CityFragment()
                    val args = Bundle()
                    args.putInt("cityId", city.id)
                    args.putString("cityName", city.name)
                    args.putString("cityDescription", city.description)
                    cityFragment.arguments = args

                    UiUtils.addFragment(
                        activity as FragmentActivity,
                        R.id.fragment_container,
                        cityFragment,
                        CityFragment.TAG,
                        true
                    )
                }
            }
        }

        // Delete city
        itemView.setOnLongClickListener {
            activity?.let {
                val dialog = Dialog(activity)
                dialog.setContentView(R.layout.dialog_confirm_action)

                val body = dialog.findViewById<TextView>(R.id.body)
                val confirmBtn = dialog.findViewById<MaterialButton>(R.id.confirm_btn)
                val cancelBtn = dialog.findViewById<MaterialButton>(R.id.cancel_btn)

                // set body text
                body.text = activity.getString(R.string.confirm_city_delete)

                // set cancel btn click listener
                cancelBtn.setOnClickListener {
                    dialog.dismiss()
                }

                confirmBtn.apply {
                    text = activity.getString(R.string.delete)

                    // set confirm btn click listener
                    setOnClickListener {
                        dialog.dismiss()

                        val deletedRows = activity.dbHelper?.deleteCityById(city.id)

                        when (deletedRows) {
                            null -> {
                                Toast.makeText(
                                    activity,
                                    activity.getString(R.string.deletion_failed),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            -1 -> {
                                Toast.makeText(
                                    activity,
                                    activity.getString(R.string.deletion_failed),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            DELETION_ERROR_CITY_WITH_LANDMARKS -> {
                                Toast.makeText(
                                    activity,
                                    activity.getString(R.string.cant_delete_city_with_landmarks),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else -> {
                                activity.citiesFragment.apply {
                                    val cityToDeleteIndex =
                                        citiesCollection.indexOf(citiesCollection.find { it.id == city.id })
                                    citiesCollection.removeAt(cityToDeleteIndex)
                                    adapter?.notifyItemRemoved(cityToDeleteIndex)
                                }

                                Toast.makeText(
                                    activity,
                                    activity.getString(R.string.city_deleted),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    // set cancelable and dismiss listener
                    dialog.setCancelable(true)

                    activity.showDialog(dialog)

                    val window: Window? = dialog.window
                    window?.setLayout(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )
                    window?.setBackgroundDrawableResource(R.color.transparent)
                }
            }

            true
        }
    }

    class MyViewHolder(binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemCityBinding? = binding
    }

    override fun getItemCount() = citiesCollection.size

}