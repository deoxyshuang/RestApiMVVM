package com.codingwithmitch.foodrecipes;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codingwithmitch.foodrecipes.databinding.ActivityRecipeBinding;
import com.codingwithmitch.mylibrary.models.Recipe;
import com.codingwithmitch.foodrecipes.viewmodels.RecipeViewModel;

public class RecipeActivity extends BaseActivity {

    private static final String TAG = "RecipeActivity";

    // UI components
//    private AppCompatImageView mRecipeImage;
//    private TextView mRecipeTitle, mRecipeRank;
//    private LinearLayout mRecipeIngredientsContainer;
//    private ScrollView mScrollView;
    private RecipeViewModel mRecipeViewModel;
    private ActivityRecipeBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_recipe, null, false);
        setContentView(binding.getRoot());

//        mRecipeImage = findViewById(R.id.recipe_image);
//        mRecipeTitle = findViewById(R.id.recipe_title);
//        mRecipeRank = findViewById(R.id.recipe_social_score);
//        mRecipeIngredientsContainer = findViewById(R.id.ingredients_container);
//        mScrollView = findViewById(R.id.parent);

        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        binding.setData(mRecipeViewModel);
        binding.setLifecycleOwner(this);

        showProgressBar(true);
        subscribeObservers();
        getIncomingIntent();
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("recipe")){
            Recipe recipe = getIntent().getParcelableExtra("recipe");
            Log.d(TAG, "getIncomingIntent: " + recipe.getTitle());
            mRecipeViewModel.searchRecipeById(recipe.getRecipe_id());
        }
    }

    private void subscribeObservers(){
        mRecipeViewModel.getRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                if(recipe != null){
                    if(recipe.getRecipe_id().equals(mRecipeViewModel.getRecipeId())){
                        setRecipeProperties(recipe);
                        mRecipeViewModel.setRetrievedRecipe(true);
                    }
                }
            }
        });

        mRecipeViewModel.isRecipeRequestTimedOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean && !mRecipeViewModel.didRetrieveRecipe()){
                    Log.d(TAG, "onChanged: timed out..");
                    displayErrorScreen("Error retrieving data. Check network connection.");
                }
            }
        });
    }

    private void displayErrorScreen(String errorMessage){
//        mRecipeTitle.setText("Error retrieveing recipe...");
//        mRecipeRank.setText("");
        TextView textView = new TextView(this);
        if(!errorMessage.equals("")){
            textView.setText(errorMessage);
        }
        else{
            textView.setText("Error");
        }
        textView.setTextSize(15);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        binding.ingredientsContainer.addView(textView);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(R.drawable.ic_launcher_background)
                .into(binding.recipeImage);

//        showParent();
        showProgressBar(false);
    }

    private void setRecipeProperties(Recipe recipe){
        if(recipe != null){
            binding.setRecipe(recipe);

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(recipe.getImage_url())
                    .into(binding.recipeImage);

//            mRecipeTitle.setText(recipe.getTitle());
//            mRecipeRank.setText(String.valueOf(Math.round(recipe.getSocial_rank())));

            binding.ingredientsContainer.removeAllViews();
            for(String ingredient: recipe.getIngredients()){
                TextView textView = new TextView(this);
                textView.setText(ingredient);
                textView.setTextSize(15);
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                binding.ingredientsContainer.addView(textView);
            }
        }

//        showParent();
        showProgressBar(false);
    }

//    private void showParent(){
//        mScrollView.setVisibility(View.VISIBLE);
//    }
}














