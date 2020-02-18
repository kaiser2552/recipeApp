package com.kaiser.recipeapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.kaiser.recipeapp.R
import com.kaiser.recipeapp.model.Recipe
import com.squareup.picasso.Picasso

class ListRecipeAdapter(private var context: Context, var recipes: ArrayList<Recipe>) :
    BaseAdapter() {
    class ViewHolder(row: View) {
        var labelRecipeName: TextView = row.findViewById(R.id.lbl_recipe_name) as TextView
        var imageRecipe: ImageView = row.findViewById(R.id.image_recipe) as ImageView
        var labelRecipeType: TextView = row.findViewById(R.id.lbl_recipe_type) as TextView
        var labelIngredients: TextView = row.findViewById(R.id.lbl_recipe_ingredient) as TextView
    }

    @SuppressLint("InflateParams")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (p1 == null) {
            val layoutInflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.list_recipe_view, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = p1
            viewHolder = p1.tag as ViewHolder
        }
        val recipe: Recipe = getItem(p0) as Recipe
        viewHolder.labelRecipeName.text = recipe.recipeName
        viewHolder.labelRecipeType.text = recipe.recipeType.typeName
        Picasso.with(context)
            .load(recipe.recipeImageURL)
            .placeholder(android.R.drawable.ic_menu_report_image)
            .error(android.R.drawable.ic_menu_report_image)
            .resize(
                120,
                120
            ).into(viewHolder.imageRecipe)

        viewHolder.labelIngredients.text = recipe.recipeIngredients.ingredients
        return view as View
    }

    override fun getItem(p0: Int): Any {
        return recipes[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return recipes.size
    }

}