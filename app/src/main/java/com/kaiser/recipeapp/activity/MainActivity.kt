package com.kaiser.recipeapp.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.inputmethod.InputMethodManager
import com.kaiser.recipeapp.R
import com.kaiser.recipeapp.base.BaseActivity
import com.kaiser.recipeapp.business.GEvent
import com.kaiser.recipeapp.database.RecipeDatabaseHelper
import com.kaiser.recipeapp.fragment.ListRecipeFragment
import com.kaiser.recipeapp.fragment.recipe.AddRecipeFragment
import com.kaiser.recipeapp.model.Recipe
import com.kaiser.recipeapp.model.StorageData
import kotlinx.android.synthetic.main.app_bar_home.*

class MainActivity : BaseActivity() {

    override fun onFragmentCallBack(event: GEvent) {
        super.onFragmentCallBack(event)

        when (event) {
            is GEvent.ChangeTitle -> {
                changeAppTitle(event.title)
            }

            is GEvent.GetRecipes -> {
                StorageData.listRecipe = getRecipes()
            }

            is GEvent.RemoveRecipe -> {
                removeRecipe(event.recipeName)
            }

            is GEvent.AddRecipe -> {
                addRecipe(event.recipe)
            }

            is GEvent.UpdateRecipe -> {
                updateRecipe(event.recipe, event.oldRecipeName)
            }

            is GEvent.ShowAddButton -> {
                showAddButton(event.boolean)
            }
        }
    }

    private fun changeAppTitle(title: String){
        toolbar_title.text = title
    }

    private fun getRecipes(): ArrayList<Recipe>{
        return mDataHelper.allRecipeList
    }

    private fun addRecipe(recipe: Recipe){
        val result = mDataHelper.addRecipe(recipe)
        if(result > 0){
            toast(R.string.message_add_success)
            gotoHomeScreen()
        } else {
            toast(R.string.message_add_fail)
        }
    }

    private fun removeRecipe(recipeName: String) {
        val result = mDataHelper.deleteRecipe(recipeName)
        if(result > 0){
            toast(R.string.message_remove_success)
            gotoHomeScreen()
        } else {
            toast(R.string.message_remove_fail)
        }
    }

    private fun updateRecipe(recipe: Recipe, oldRecipeName: String){
        val result = mDataHelper.updateRecipe(recipe, oldRecipeName)
        if(result > 0){
            toast(R.string.message_update_success)
            gotoHomeScreen()
        } else {
            toast(R.string.message_update_fail)
        }
    }


    private fun gotoHomeScreen() {
        hideKeyboard(this)
        setFragment(ListRecipeFragment.newInstance())
    }

    private fun showAddButton(boolean: Boolean){
        if(boolean){
            fab.show()
        } else {
            fab.hide()
        }
    }

    private val mHandler = Handler()
    private val mDataHelper = RecipeDatabaseHelper(this)

    private fun hideKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            changeFragment(AddRecipeFragment.newInstance())
        }

        if (savedInstanceState == null) {
            changeFragment(ListRecipeFragment.newInstance())
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressed()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        mHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()

        changeAppTitle(getString(R.string.label_list_recipes))
    }
}