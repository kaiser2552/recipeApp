package com.kaiser.recipeapp.base

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.kaiser.recipeapp.business.GEvent

open class BaseFragment : Fragment() {

    protected var fragmentListener: OnFragmentListener? = null

    // This list will be disposed on onStop()

    protected val mHandler = Handler()

    open fun fragmentTag(): String = this.javaClass.simpleName

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("LifeCycle", "${fragmentTag()} -- onAttach")

        if (context is OnFragmentListener) {
            fragmentListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LifeCycle", "${fragmentTag()} -- onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("LifeCycle", "${fragmentTag()} -- onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("LifeCycle", "${fragmentTag()} -- onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("LifeCycle", "${fragmentTag()} -- onActivityCreated")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("LifeCycle", "${fragmentTag()} -- onViewStateRestored")
    }

    override fun onStart() {
        super.onStart()
        Log.d("LifeCycle", "${fragmentTag()} -- onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LifeCycle", "${fragmentTag()} -- onResume")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        Log.d("LifeCycle", "${fragmentTag()} -- onCreateOptionsMenu")
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        Log.d("LifeCycle", "${fragmentTag()} -- onPrepareOptionsMenu")
    }

    override fun onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu()
        Log.d("LifeCycle", "${fragmentTag()} -- onDestroyOptionsMenu")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("LifeCycle", "${fragmentTag()} -- onOptionsItemSelected: itemId = ${item.itemId}")
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onPause() {
        Log.d("LifeCycle", "${fragmentTag()} -- onPause")
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("LifeCycle", "${fragmentTag()} -- onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        mHandler.removeCallbacksAndMessages(null)

        Log.d("LifeCycle", "${fragmentTag()} -- onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("LifeCycle", "${fragmentTag()} -- onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("LifeCycle", "${fragmentTag()} -- onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        fragmentListener = null

        Log.d("LifeCycle", "${fragmentTag()} -- onDetach")
        super.onDetach()
    }

    private var mToast: Toast? = null
    protected fun toast(resId: Int) {
        mToast?.cancel()
        mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT)
        mToast?.show()
    }

    protected fun toast(message: String) {
        mToast?.cancel()
        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        mToast?.show()
    }

    open fun onBackPressed() {
        fragmentListener?.onFragmentBackPressed()
    }

    fun setActionBarTitle(@StringRes resId: Int) {
        setActionBarTitle(getString(resId))
    }

    @Suppress("UNUSED_PARAMETER")
    fun setActionBarTitle(title: String) {
        // (activity as BaseActivity).setActionBarTitle(title)
        // Do not need to set title when using new theme
        (activity as BaseActivity).setActionBarTitle("")
    }

    fun setDisplayHomeAsUpEnabled(enable: Boolean) {
        (activity as BaseActivity).setDisplayHomeAsUpEnabled(enable)
    }

    fun isFragmentInstanceInBackStack(fragment: Class<out Fragment>): Boolean {
        return (activity as BaseActivity).isFragmentInstanceInBackStack(fragment)
    }

    protected fun changeFragment(baseFragment: BaseFragment) {
        (activity as BaseActivity).changeFragment(baseFragment)
    }

    fun gotoSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", activity!!.packageName, null)
        intent.data = uri
        startActivityForResult(intent, REQUEST_PERMISSION_SETTING)
    }

    companion object {
        const val REQUEST_PERMISSION_SETTING = 331
    }

    interface OnFragmentListener {
        fun onFragmentBackPressed()
        fun onFragmentCallBack(event: GEvent)
    }
}
