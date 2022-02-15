package com.synergygfs.travelapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import com.synergygfs.travelapp.R
import com.synergygfs.travelapp.data.DbHelper
import com.synergygfs.travelapp.databinding.ActivityMainBinding
import com.synergygfs.travelapp.ui.fragments.AddCityFragment
import com.synergygfs.travelapp.ui.fragments.CitiesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var dbHelper: DbHelper? = null

    var citiesFragment = CitiesFragment()
    var addCitiesFragment = AddCityFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.topAppBar)

        dbHelper = DbHelper(this)

        // Adding main fragment
        UiUtils.addFragment(this, R.id.fragment_container, citiesFragment, CitiesFragment.TAG)

        supportFragmentManager.addOnBackStackChangedListener {
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
                else -> {
                    binding.topAppBar.title = "Travel App"
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
            }
        }
    }

    override fun onDestroy() {
        dbHelper?.close()
        super.onDestroy()
    }
}