package com.synergygfs.travelapp.ui.fragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.synergygfs.travelapp.Constants
import com.synergygfs.travelapp.R
import com.synergygfs.travelapp.data.TravelAppContractContract
import com.synergygfs.travelapp.data.models.Landmark
import com.synergygfs.travelapp.databinding.FragmentAddLandmarkBinding
import com.synergygfs.travelapp.ui.MainActivity
import com.synergygfs.travelapp.ui.UiUtils

class AddLandmarkFragment : Fragment() {

    private lateinit var binding: FragmentAddLandmarkBinding

    private var isNameValid = false
    private var isDescriptionValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_bot)
        exitTransition = inflater.inflateTransition(R.transition.slide_top)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_landmark, container, false
        )

        val cityId = arguments?.getInt("cityId") ?: -1

        val landmarkName = binding.name
        val landmarkDescription = binding.description
        val addBtn = binding.addBtn

        landmarkName.doOnTextChanged { text, _, _, _ ->
            // Check if name is valid
            isNameValid = text?.trim()?.matches(Constants.VALIDATION_REGEX_NAME) == true

            // Check if addBtn should be enabled
            addBtn.isEnabled = isNameValid && isDescriptionValid
        }

        landmarkDescription.doOnTextChanged { text, _, _, _ ->
            // Check if description is valid
            isDescriptionValid =
                text?.trim()?.matches(Constants.VALIDATION_REGEX_DESCRIPTION) == true

            // Check if addBtn should be enabled
            addBtn.isEnabled = isNameValid && isDescriptionValid
        }

        addBtn.setOnClickListener {
            val name = landmarkName.text.toString()
            val description = landmarkDescription.text.toString()

            // Create a new map of values, where column names are the keys
            val values = ContentValues().apply {
                put(TravelAppContractContract.LandmarkEntity.COLUMN_NAME_CITY_ID, cityId)
                put(TravelAppContractContract.LandmarkEntity.COLUMN_NAME_NAME, name)
                put(TravelAppContractContract.LandmarkEntity.COLUMN_NAME_DESCRIPTION, description)
            }

            // Insert the new row, returning the primary key value of the new row
            (activity as MainActivity?)?.let { activity ->
                val newRowId = activity.dbHelper?.insert(
                    TravelAppContractContract.LandmarkEntity.TABLE_NAME,
                    values
                )

                if (newRowId != null && newRowId > -1) { // If landmark creation is successful, pop the fragment, update the landmarksCollection and show a toast
                    (UiUtils.getFragmentByTag(activity, CityFragment.TAG) as CityFragment?)?.apply {
                        landmarksCollection.add(
                            Landmark(
                                newRowId.toInt(),
                                name,
                                description
                            )
                        )
                        landmarksCollection.sortBy { it.name }
                        adapter?.notifyDataSetChanged()
                    }

                    Toast.makeText(
                        activity,
                        activity.getString(R.string.landmark_added),
                        Toast.LENGTH_SHORT
                    ).show()
                    activity.onBackPressed()
                } else // Show a toast that landmark creation failed
                    Toast.makeText(
                        activity,
                        activity.getString(R.string.saving_failed),
                        Toast.LENGTH_SHORT
                    ).show()
            }

        }

        return binding.root
    }

    companion object {
        const val TAG = "AddLandmarkFragment"
    }

}