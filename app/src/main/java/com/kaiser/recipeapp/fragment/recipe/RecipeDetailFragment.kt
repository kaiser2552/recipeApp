package com.kaiser.recipeapp.fragment.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kaiser.recipeapp.R
import com.kaiser.recipeapp.base.BaseFragment
import com.kaiser.recipeapp.business.GEvent
import com.kaiser.recipeapp.model.Recipe
import kotlinx.android.synthetic.main.fragment_recipe_detail.*
import com.squareup.picasso.Picasso

class RecipeDetailFragment : BaseFragment() {

    private lateinit var recipe: Recipe

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipe = arguments?.getParcelable(ARG_RECIPE)!!

        initView()
    }

    private fun initView() {
        fragmentListener?.onFragmentCallBack(GEvent.ChangeTitle(getString(R.string.label_recipe_detail)))
        fragmentListener?.onFragmentCallBack(GEvent.ShowAddButton(false))

        recipe_name.text = recipe.recipeName
        recipe_type.text = recipe.recipeType.typeName
        Picasso.with(context).load(recipe.recipeImageURL).into(recipe_thumbnail)
        recipe_steps_detail.text = recipe.recipeStep.stepDescription
        recipe_ingredients.text = recipe.recipeIngredients.ingredients

        btn_go_back.setOnClickListener {
            onBackPressed()
        }

        btn_remove.setOnClickListener {
            removeRecipe()
        }

        btn_edit.setOnClickListener {
            editRecipe()
        }
    }

    private fun removeRecipe() {
        fragmentListener?.onFragmentCallBack(GEvent.RemoveRecipe(recipe.recipeName))
    }

    private fun editRecipe() {
        changeFragment(EditRecipeFragment.newInstance(recipe))
    }

    companion object {
        private const val ARG_RECIPE = "ARG_RECIPE"

        @JvmStatic
        fun newInstance(recipe: Recipe) = RecipeDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_RECIPE, recipe)
            }
        }
    }
}