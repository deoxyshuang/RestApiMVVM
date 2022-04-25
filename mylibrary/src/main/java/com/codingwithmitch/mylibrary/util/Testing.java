package com.codingwithmitch.mylibrary.util;

import android.util.Log;

import com.codingwithmitch.mylibrary.models.Recipe;

import java.util.List;

public class Testing {

    public static void printRecipes(List<Recipe>list, String tag){
        for(Recipe recipe: list){
            Log.d(tag, "onChanged: " + recipe.getTitle());
        }
    }
}
