package com.kaiser.recipeapp.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.kaiser.recipeapp.model.Ingredient
import com.kaiser.recipeapp.model.Recipe
import com.kaiser.recipeapp.model.RecipeType
import com.kaiser.recipeapp.model.Step

class RecipeDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val allRecipeList: ArrayList<Recipe>
        @SuppressLint("Recycle")
        get() {
            val recipeList = ArrayList<Recipe>()
            val selectQuery = "SELECT  * FROM $TABLE_RECIPES"
            val db = this.readableDatabase
            val result = db.rawQuery(selectQuery, null)
            if (result.moveToFirst()) {
                do {
                    val recipe = Recipe(
                        result.getString(0),
                        result.getString(2),
                        RecipeType.valueOf(result.getString(1)),
                        Ingredient(result.getString(3)),
                        Step(result.getString(4))
                    )
                    recipeList.add(recipe)
                } while (result.moveToNext())
                Log.d("array", recipeList.toString())
            }
            return recipeList
        }

    init {

        Log.d("table", CREATE_TABLE_RECIPES)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_RECIPES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_RECIPES'")
        onCreate(db)
    }

    fun deleteRecipe(recipeName: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_RECIPES, "$KEY_RECIPE_NAME=?", arrayOf(recipeName))
    }

    fun addRecipe(recipe: Recipe): Long {
        val db = this.writableDatabase
        // Creating content values
        val values = ContentValues()
        values.put(KEY_RECIPE_NAME, recipe.recipeName)
        values.put(KEY_RECIPE_TYPE, recipe.recipeType.typeName)
        values.put(KEY_RECIPE_IMAGE_URL, recipe.recipeImageURL)
        values.put(KEY_RECIPE_INGREDIENTS, recipe.recipeIngredients.ingredients)
        values.put(KEY_RECIPE_STEPS, recipe.recipeStep.stepDescription)

        return db.insert(TABLE_RECIPES, null, values)
    }

    fun updateRecipe(recipe: Recipe, oldRecipeName: String): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_RECIPE_NAME, recipe.recipeName)
        values.put(KEY_RECIPE_TYPE, recipe.recipeType.typeName)
        values.put(KEY_RECIPE_IMAGE_URL, recipe.recipeImageURL)
        values.put(KEY_RECIPE_INGREDIENTS, recipe.recipeIngredients.ingredients)
        values.put(KEY_RECIPE_STEPS, recipe.recipeStep.stepDescription)
        return db.update(TABLE_RECIPES, values, "$KEY_RECIPE_NAME=?", arrayOf(oldRecipeName))
    }

    companion object {

        var DATABASE_NAME = "recipe_database"
        private val DATABASE_VERSION = 1
        private val TABLE_RECIPES = "recipes"
        private val KEY_RECIPE_NAME = "name"
        private val KEY_RECIPE_TYPE = "type"
        private val KEY_RECIPE_IMAGE_URL = "url"
        private val KEY_RECIPE_INGREDIENTS = "ingredients"
        private val KEY_RECIPE_STEPS = "steps"

        /*CREATE TABLE students ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone_number TEXT......);*/
        private val CREATE_TABLE_RECIPES = ("CREATE TABLE "
                + TABLE_RECIPES + "(" + KEY_RECIPE_NAME
                + " TEXT PRIMARY KEY," + KEY_RECIPE_TYPE + " TEXT," + KEY_RECIPE_IMAGE_URL + " TEXT ," + KEY_RECIPE_INGREDIENTS + " TEXT," + KEY_RECIPE_STEPS + " TEXT);")
    }
}