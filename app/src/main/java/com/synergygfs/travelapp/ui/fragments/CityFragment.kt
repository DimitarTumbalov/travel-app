package com.synergygfs.travelapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.synergygfs.travelapp.R
import com.synergygfs.travelapp.data.models.Landmark
import com.synergygfs.travelapp.databinding.FragmentCityBinding
import com.synergygfs.travelapp.ui.MainActivity
import com.synergygfs.travelapp.ui.UiUtils
import com.synergygfs.travelapp.ui.adapters.LandmarksAdapter
import java.util.*

class CityFragment : Fragment() {

    private lateinit var binding: FragmentCityBinding

    var adapter: LandmarksAdapter? = null

    var landmarksCollection = Vector<Landmark>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
        exitTransition = inflater.inflateTransition(R.transition.slide_right)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_city, container, false
        )

        val activity = activity as MainActivity?
        val description = binding.description

        // Retrieve the passed data
        val cityId = arguments?.getInt("cityId") ?: -1
        binding.name.text = arguments?.getString("cityName") ?: ""
        description.text = arguments?.getString("cityDescription") ?: ""

        landmarksCollection = activity?.dbHelper?.getLandmarksByCityId(cityId) ?: Vector()

        // Set up the LandmarksAdapter
        val landmarksRv = binding.landmarksRv
        val lm = LinearLayoutManager(requireContext())
        landmarksRv.layoutManager = lm
        adapter = LandmarksAdapter(this, landmarksCollection)
        landmarksRv.adapter = adapter

        // Add dividers between RecyclerView items
        val dividerItemDecoration = DividerItemDecoration(
            landmarksRv.context,
            lm.orientation
        )
        landmarksRv.addItemDecoration(dividerItemDecoration)

        // Expand/collapse description TextView on click
        description.setOnClickListener {
            if (description.maxLines == 8)
                description.maxLines = Integer.MAX_VALUE
            else
                description.maxLines = 8
        }

        binding.addLandmarkBtn.setOnClickListener {
            activity?.let {
                if (UiUtils.getFragmentByTag(activity, AddLandmarkFragment.TAG) == null) {
                    val addLandmarkFragment = AddLandmarkFragment()
                    val args = Bundle()
                    args.putInt("cityId", cityId)
                    addLandmarkFragment.arguments = args

                    UiUtils.addFragment(
                        activity,
                        R.id.fragment_container,
                        addLandmarkFragment,
                        AddLandmarkFragment.TAG,
                        true
                    )
                }
            }
        }

        return binding.root
    }

    companion object {
        const val TAG = "CityFragment"
    }

}