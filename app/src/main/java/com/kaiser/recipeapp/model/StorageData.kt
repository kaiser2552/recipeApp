package com.kaiser.recipeapp.model

sealed class StorageData {
    companion object {
        var listRecipe: ArrayList<Recipe> = arrayListOf()
    }
}