package com.kaiser.recipeapp.fragment.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.kaiser.recipeapp.R
import com.kaiser.recipeapp.base.BaseFragment
import com.kaiser.recipeapp.business.GEvent
import com.kaiser.recipeapp.model.Ingredient
import com.kaiser.recipeapp.model.Recipe
import com.kaiser.recipeapp.model.RecipeType
import com.kaiser.recipeapp.model.Step
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_add_recipe.*

class EditRecipeFragment : BaseFragment() {

    private lateinit var recipe: Recipe
    private var oldRecipeName = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_recipe, container, false)
    }

    override fun onResume() {
        super.onResume()

        setActionBarTitle(R.string.menu_home)
        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipe = arguments?.getParcelable(ARG_RECIPE)!!

        initView()
    }

    private fun initView() {
        fragmentListener?.onFragmentCallBack(GEvent.ShowAddButton(false))
        fragmentListener?.onFragmentCallBack(GEvent.ChangeTitle(getString(R.string.label_edit_recipe)))

        val recipeTypes = resources.getStringArray(R.array.recipe_types)
        val adapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_item, recipeTypes
        )
        spinner_recipe_types.adapter = adapter

        oldRecipeName = recipe.recipeName

        //add info
        et_recipe_name.setText(recipe.recipeName)
        et_recipe_image_link.setText(recipe.recipeImageURL)
        et_recipe_ingredients.setText(recipe.recipeIngredients.ingredients)
        et_recipe_steps.setText(recipe.recipeStep.stepDescription)
        Picasso.with(context).load(recipe.recipeImageURL).into(top_image)

        val spinnerPosition = adapter.getPosition(recipe.recipeType.typeName)
        spinner_recipe_types.setSelection(spinnerPosition)

        btn_add.text = getString(R.string.edit)
        btn_add.setOnClickListener {
            editRecipe()
        }
    }

    private fun editRecipe() {
        val recipe = Recipe(
            recipeName = et_recipe_name.text.toString(),
            recipeType = RecipeType.valueOf(spinner_recipe_types.selectedItem.toString()),
            recipeImageURL = et_recipe_image_link.text.toString(),
            recipeIngredients = Ingredient(et_recipe_ingredients.text.toString()),
            recipeStep = Step(et_recipe_steps.text.toString())
        )
        fragmentListener?.onFragmentCallBack(GEvent.UpdateRecipe(recipe,oldRecipeName))
    }

    companion object {
        private const val ARG_RECIPE = "ARG_RECIPE"

        @JvmStatic
        fun newInstance(recipe: Recipe) = EditRecipeFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_RECIPE, recipe)
            }
        }
    }
}