package com.kaiser.recipeapp.model

data class Recipe(
    var recipeName: String,
    var recipeImageURL: String,
    var recipeType: RecipeType,
    var recipeIngredients: List<Ingredient>,
    var recipeStep: List<Step>
)

data class Ingredient(
    var ingredientName: String
)

data class Step(
    var stepDescription: String
)

data class RecipeType(
    var typeName: String
)
