package com.synergygfs.travelapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.synergygfs.travelapp.R
import com.synergygfs.travelapp.data.models.Landmark
import com.synergygfs.travelapp.databinding.FragmentLandmarkBinding

class LandmarkFragment : Fragment() {

    private lateinit var binding: FragmentLandmarkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
        exitTransition = inflater.inflateTransition(R.transition.slide_right)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_landmark, container, false
        )

        // Retrieve the passed data
        val landmark = arguments?.getParcelable("landmark") ?: Landmark()

        binding.name.text = landmark.name
        binding.description.text = landmark.description

        return binding.root
    }

    companion object {
        const val TAG = "LandmarkFragment"
    }

}