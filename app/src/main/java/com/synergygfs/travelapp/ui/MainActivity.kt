package com.synergygfs.travelapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.synergygfs.travelapp.R
import com.synergygfs.travelapp.databinding.ActivityMainBinding
import com.synergygfs.travelapp.ui.fragments.CitiesFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Adding main fragment
        UiUtils.addFragment(this, R.id.fragment_container, CitiesFragment(), CitiesFragment.TAG)
    }
}