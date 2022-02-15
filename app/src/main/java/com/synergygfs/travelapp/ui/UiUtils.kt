package com.synergygfs.travelapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class UiUtils {

    companion object{

        fun addFragment(
            activity: FragmentActivity,
            container: Int,
            fragment: Fragment,
            tag: String? = null,
            addToBackStack: Boolean = false,
            allowStateLoss: Boolean = false
        ) {
            activity.runOnUiThread {
                val transaction = activity.supportFragmentManager.beginTransaction()
                transaction.add(container, fragment, tag)
                if (addToBackStack) transaction.addToBackStack(tag)
                if (allowStateLoss) transaction.commitAllowingStateLoss()
                else transaction.commit()
            }
        }

    }

}