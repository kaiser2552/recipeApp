package com.kaiser.recipeapp.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kaiser.recipeapp.R
import com.kaiser.recipeapp.business.GEvent

open class BaseActivity : AppCompatActivity(), BaseFragment.OnFragmentListener {

    protected var currentFragmentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentFragmentTag = savedInstanceState?.getString(ARG_CURRENT_FRAGMENT_TAG)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(ARG_CURRENT_FRAGMENT_TAG, currentFragmentTag)
    }

    override fun onFragmentBackPressed() {
        onBackPressed()
    }

    override fun onFragmentCallBack(event: GEvent) {
        Log.i("BaseActivity", "onFragmentCallBack -- $event")
    }

    /*override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }*/

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun setDisplayHomeAsUpEnabled(enable: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(enable)
    }

    private var mToast: Toast? = null
    protected fun toast(resId: Int) {
        mToast?.cancel()
        mToast = Toast.makeText(this, resId, Toast.LENGTH_SHORT)
        mToast?.show()
    }

    protected fun toast(message: String? = null) {
        mToast?.cancel()
        message?.let {
            mToast = Toast.makeText(this, it, Toast.LENGTH_SHORT)
            mToast?.show()
        }
    }

    /**
     * Clear all fragment in stack and add [fragment] to stack.
     */
    protected fun setFragment(fragment: BaseFragment) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        changeFragment(fragment)
    }

    /**
     * Remove top fragment in stack and add new one.
     */
    fun replaceFragmentAtTopStack(newFragment: Fragment) {
        if (currentFragmentTag == newFragment.javaClass.simpleName) {
            return
        }
        supportFragmentManager.popBackStack()
        changeFragment(newFragment)
    }

    fun changeFragment(newFragment: Fragment) {
        changeFragment(newFragment, true)
    }

    fun changeFragment(newFragment: Fragment, isDuplicateAllowed: Boolean) {
        if (!isDuplicateAllowed && currentFragmentTag == newFragment.javaClass.simpleName) {
            return
        }
        currentFragmentTag = newFragment.javaClass.simpleName
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, newFragment, currentFragmentTag)
                .addToBackStack(currentFragmentTag)
                .commit()
    }

    fun isFragmentOnTop(fragment: Class<out Fragment>): Boolean {
        return currentFragmentTag == fragment.simpleName
    }

    fun isFragmentInstanceInBackStack(fragment: Class<out Fragment>): Boolean {
        return supportFragmentManager.findFragmentByTag(fragment.simpleName) != null
    }

    fun setRootFragment(newFragment: Fragment) {
        currentFragmentTag = newFragment.javaClass.simpleName
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, newFragment, currentFragmentTag)
                .addToBackStack(currentFragmentTag)
                .commit()
    }


    fun gotoAppSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, REQUEST_PERMISSION_SETTING)
    }

    companion object {
        private const val ARG_CURRENT_FRAGMENT_TAG = "ARG_CURRENT_fragment_tag"

        const val REQUEST_PERMISSION_SETTING = 331
    }
}
