package com.kaiser.recipeapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    var recipeName: String,
    var recipeImageURL: String,
    var recipeType: RecipeType,
    var recipeIngredients: Ingredient,
    var recipeStep: Step
) : Parcelable

@Parcelize
data class Ingredient(
    var ingredients: String
) : Parcelable

@Parcelize
data class Step(
    var stepDescription: String
) : Parcelable

enum class RecipeType(val typeName: String) {
    Breakfast("Breakfast"), Lunch("Lunch"), Dinner("Dinner")
}
