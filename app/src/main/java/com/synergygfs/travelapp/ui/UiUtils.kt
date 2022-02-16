package com.synergygfs.travelapp.ui

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class UiUtils {

    companion object {

        fun addFragment(
            activity: FragmentActivity,
            container: Int,
            fragment: Fragment,
            tag: String? = null,
            addToBackStack: Boolean = false,
            allowStateLoss: Boolean = false
        ) {
            activity.runOnUiThread {
                val supportFragmentManager = activity.supportFragmentManager
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(container, fragment, tag)

                if (addToBackStack) transaction.addToBackStack(tag)
                if (allowStateLoss) transaction.commitAllowingStateLoss()
                else transaction.commit()
            }
        }

        fun getFragmentByTag(activity: FragmentActivity, tag: String?): Fragment? {
            return activity.supportFragmentManager.findFragmentByTag(tag)
        }

        fun getTopFragmentTag(activity: FragmentActivity): String? {
            activity.supportFragmentManager.run {
                return when (backStackEntryCount) {
                    0 -> null
                    else -> getBackStackEntryAt(backStackEntryCount - 1).name
                }
            }
        }

        fun hideSoftKeyBoard(activity: Activity) {
            activity.currentFocus?.let {
                val imm =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }

    }

}