package com.kaiser.recipeapp.business

import com.kaiser.recipeapp.model.Recipe

sealed class GEvent {
    data class ShowAddButton(val boolean: Boolean) : GEvent()
    data class ChangeTitle(val title: String) : GEvent()
    data class AddRecipe(val recipe: Recipe) : GEvent()
    data class RemoveRecipe(val recipeName: String) : GEvent()
    data class UpdateRecipe(val recipe: Recipe, val oldRecipeName: String) : GEvent()
    object GetRecipes : GEvent()
}
