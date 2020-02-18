package com.kaiser.recipeapp.fragment.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.kaiser.recipeapp.R
import com.kaiser.recipeapp.base.BaseFragment
import com.kaiser.recipeapp.business.GEvent
import com.kaiser.recipeapp.model.Ingredient
import com.kaiser.recipeapp.model.Recipe
import com.kaiser.recipeapp.model.RecipeType
import com.kaiser.recipeapp.model.Step
import kotlinx.android.synthetic.main.fragment_add_recipe.*

class AddRecipeFragment : BaseFragment() {

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

        initView()
    }

    private fun initView() {
        fragmentListener?.onFragmentCallBack(GEvent.ChangeTitle(getString(R.string.label_add_new_recipe)))
        fragmentListener?.onFragmentCallBack(GEvent.ShowAddButton(false))

        val recipeTypes = resources.getStringArray(R.array.recipe_types)

        val adapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_item, recipeTypes
        )
        spinner_recipe_types.adapter = adapter

        spinner_recipe_types.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        btn_add.setOnClickListener {
            addRecipe()
        }
    }

    private fun addRecipe() {
        val recipe = Recipe(
            recipeName = et_recipe_name.text.toString(),
            recipeType = RecipeType.valueOf(spinner_recipe_types.selectedItem.toString()),
            recipeImageURL = et_recipe_image_link.text.toString(),
            recipeIngredients = Ingredient(et_recipe_ingredients.text.toString()),
            recipeStep = Step(et_recipe_steps.text.toString())
        )
        fragmentListener?.onFragmentCallBack(GEvent.AddRecipe(recipe))
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddRecipeFragment()
    }
}