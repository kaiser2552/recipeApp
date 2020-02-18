package com.kaiser.recipeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kaiser.recipeapp.R
import com.kaiser.recipeapp.adapter.ListRecipeAdapter
import com.kaiser.recipeapp.base.BaseFragment
import com.kaiser.recipeapp.business.GEvent
import com.kaiser.recipeapp.fragment.recipe.RecipeDetailFragment
import com.kaiser.recipeapp.model.*
import kotlinx.android.synthetic.main.fragment_list_recipe.*

class ListRecipeFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_list_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun onResume() {
        super.onResume()

        setActionBarTitle(R.string.menu_home)
        setHasOptionsMenu(false)
        setDisplayHomeAsUpEnabled(false)
    }

    private fun initView() {
        fragmentListener?.onFragmentCallBack(GEvent.ChangeTitle(getString(R.string.label_list_recipes)))
        fragmentListener?.onFragmentCallBack(GEvent.GetRecipes)
        fragmentListener?.onFragmentCallBack(GEvent.ShowAddButton(true))

        val arrayRecipes: ArrayList<Recipe> = StorageData.listRecipe
        list_recipe.adapter = ListRecipeAdapter(this.requireContext(), arrayRecipes)
        list_recipe.setOnItemClickListener { _, _, position, _ ->
            changeFragment(RecipeDetailFragment.newInstance(arrayRecipes[position]))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListRecipeFragment()
    }
}