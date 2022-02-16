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
import com.synergygfs.travelapp.R
import com.synergygfs.travelapp.data.models.Landmark
import com.synergygfs.travelapp.databinding.ItemLandmarkBinding
import com.synergygfs.travelapp.ui.MainActivity
import com.synergygfs.travelapp.ui.UiUtils
import com.synergygfs.travelapp.ui.fragments.CityFragment
import com.synergygfs.travelapp.ui.fragments.LandmarkFragment
import java.util.*

class LandmarksAdapter(
    private val cityFragment: CityFragment,
    private var landmarksCollection: Vector<Landmark>
) :
    RecyclerView.Adapter<LandmarksAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemLandmarkBinding =
            ItemLandmarkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return MyViewHolder(binding)
    }

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val activity = cityFragment.activity as MainActivity?
        val itemView = holder.itemView

        val landmark = landmarksCollection[position]

        holder.binding?.apply {
            name.text = landmark.name
            description.text = landmark.description
        }

        // Open landmark fragment
        itemView.setOnClickListener {
            activity?.let { activity ->
                if (UiUtils.getFragmentByTag(activity, LandmarkFragment.TAG) == null) {
                    val landmarkFragment = LandmarkFragment()
                    val args = Bundle()
                    args.putString("landmarkName", landmark.name)
                    args.putString("landmarkDescription", landmark.description)
                    landmarkFragment.arguments = args

                    UiUtils.addFragment(
                        activity as FragmentActivity,
                        R.id.fragment_container,
                        landmarkFragment,
                        LandmarkFragment.TAG,
                        true
                    )
                }
            }
        }

        // Delete landmark
        itemView.setOnLongClickListener {
            activity?.let {
                val dialog = Dialog(activity)
                dialog.setContentView(R.layout.dialog_confirm_action)

                val body = dialog.findViewById<TextView>(R.id.body)
                val confirmBtn = dialog.findViewById<MaterialButton>(R.id.confirm_btn)
                val cancelBtn = dialog.findViewById<MaterialButton>(R.id.cancel_btn)

                // set body text
                body.text = activity.getString(R.string.confirm_landmark_delete)

                // set cancel btn click listener
                cancelBtn.setOnClickListener {
                    dialog.dismiss()
                }
                confirmBtn.apply {
                    text = activity.getString(R.string.delete)

                    // set confirm btn click listener

                    setOnClickListener {
                        dialog.dismiss()

                        val deletedRow = activity.dbHelper?.deleteLandmarkById(landmark.id)

                        if (deletedRow != null && deletedRow > -1) {
                            (UiUtils.getFragmentByTag(
                                activity,
                                CityFragment.TAG
                            ) as CityFragment?)?.apply {
                                val landmarkToDeleteIndex =
                                    landmarksCollection.indexOf(landmarksCollection.find { it.id == landmark.id })
                                landmarksCollection.removeAt(landmarkToDeleteIndex)
                                adapter?.notifyItemRemoved(landmarkToDeleteIndex)
                            }

                            Toast.makeText(
                                activity,
                                activity.getString(R.string.landmark_deleted),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else
                            Toast.makeText(
                                activity,
                                activity.getString(R.string.deletion_failed),
                                Toast.LENGTH_SHORT
                            ).show()
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

    class MyViewHolder(binding: ItemLandmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemLandmarkBinding? = binding
    }

    override fun getItemCount() = landmarksCollection.size

}