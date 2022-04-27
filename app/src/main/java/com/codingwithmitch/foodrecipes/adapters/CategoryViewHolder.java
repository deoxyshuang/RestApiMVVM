package com.codingwithmitch.foodrecipes.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.codingwithmitch.foodrecipes.RecipeListActivity;
import com.codingwithmitch.foodrecipes.databinding.LayoutCategoryListItemBinding;
import com.codingwithmitch.mylibrary.models.Recipe;


public class CategoryViewHolder extends RecyclerView.ViewHolder  {

    LayoutCategoryListItemBinding binding;

    public CategoryViewHolder(@NonNull LayoutCategoryListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    void bind(Recipe recipe, RecipeListActivity activity) {
        binding.setRecipe(recipe);
        binding.setRecipeList(activity);
        binding.executePendingBindings();
    }
}
