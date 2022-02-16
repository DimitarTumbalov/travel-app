package com.synergygfs.travelapp.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import com.synergygfs.travelapp.R
import com.synergygfs.travelapp.data.DbHelper
import com.synergygfs.travelapp.databinding.ActivityMainBinding
import com.synergygfs.travelapp.ui.fragments.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var dbHelper: DbHelper? = null

    var citiesFragment = CitiesFragment()

    private var lastDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.topAppBar)

        dbHelper = DbHelper(this)

        // Add main fragment to FragmentContainer - CitiesFragment
        UiUtils.addFragment(this, R.id.fragment_container, citiesFragment, CitiesFragment.TAG)

        // Update the TopAppBar when FragmentBackStack is changed
        supportFragmentManager.addOnBackStackChangedListener {
            // Hide the keyboard when a fragment is opened/closed
            UiUtils.hideSoftKeyBoard(this)

            when (UiUtils.getTopFragmentTag(this)) {
                AddCityFragment.TAG -> {
                    binding.topAppBar.apply {
                        title = "Add City"
                        navigationIcon = AppCompatResources.getDrawable(
                            this@MainActivity,
                            R.drawable.ic_arrow_back
                        )
                        setNavigationOnClickListener {
                            onBackPressed()
                        }
                    }

                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                AddLandmarkFragment.TAG -> {
                    binding.topAppBar.apply {
                        title = "Add Landmark"
                        navigationIcon = AppCompatResources.getDrawable(
                            this@MainActivity,
                            R.drawable.ic_arrow_back
                        )
                        setNavigationOnClickListener {
                            onBackPressed()
                        }
                    }

                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                CityFragment.TAG -> {
                    binding.topAppBar.apply {
                        title = "City with landmarks"
                        navigationIcon = AppCompatResources.getDrawable(
                            this@MainActivity,
                            R.drawable.ic_arrow_back
                        )
                        setNavigationOnClickListener {
                            onBackPressed()
                        }
                    }

                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                LandmarkFragment.TAG -> {
                    binding.topAppBar.apply {
                        title = "Landmark"
                        navigationIcon = AppCompatResources.getDrawable(
                            this@MainActivity,
                            R.drawable.ic_arrow_back
                        )
                        setNavigationOnClickListener {
                            onBackPressed()
                        }
                    }

                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                else -> {
                    binding.topAppBar.title = "Cities"
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
            }
        }
    }

    override fun onDestroy() {
        // Close the database
        dbHelper?.close()
        super.onDestroy()
    }

    fun showDialog(dialog: Dialog) {
        if (lastDialog?.isShowing == true)
            lastDialog?.dismiss()

        dialog.show()
        lastDialog = dialog
    }

}