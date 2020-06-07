package edu.utn.frba.cookingmate.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.*
import com.bumptech.glide.request.RequestOptions
import edu.utn.frba.cookingmate.R
import edu.utn.frba.cookingmate.models.Recipe
import edu.utn.frba.cookingmate.viewmodels.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_recipe.view.*

class RecipesAdapter(
    private val recipes: List<Recipe>,
    private val onClickViewStepsListener: (Recipe) -> Unit,
    private val showStoriesFragment: (View, Recipe) -> Unit
) :
    RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private lateinit var recipeViewModels: Map<String, RecipeViewModel>

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_recipe, parent, false)

        recipeViewModels = recipes.map { recipe ->
            recipe.id to
                    RecipeViewModel(
                        recipe,
                        false
                    )
        }.toMap()

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recipeViewModel = recipeViewModels.entries.toList()[position].value // TODO improve
        val view = holder.view

        view.recipeName.text = recipeViewModel.recipe.name
        view.viewRecipe.setOnClickListener { onClickViewStepsListener(recipeViewModel.recipe) }

        Glide.with(view)
            .load(recipeViewModel.recipe.imageLink)
            .apply(RequestOptions().transforms(CenterCrop()))
            .into(holder.view.recipeImage)

        view.recipeIngredients.text = recipeViewModel.recipe.ingredients.joinToString("\n")
        view.viewIngredients.setOnClickListener {
            toggleIngredients(
                recipeViewModel.recipe.id,
                view
            )
        }
        view.recipeIngredients.visibility = View.INVISIBLE

        showStoriesFragment(view.fragmentRecipeStoriesThumbnailContainer, recipeViewModel.recipe)
    }

    override fun getItemCount() = recipes.size

    private fun toggleIngredients(recipeId: String, view: View) {
        val recipeViewModel = recipeViewModels[recipeId] // TODO improve
        recipeViewModel!!.ingredientsVisible = !recipeViewModel.ingredientsVisible

        if (recipeViewModel.ingredientsVisible) {
            view.recipeImage.imageAlpha = 100
            view.recipeIngredients.visibility = View.VISIBLE
        } else {
            view.recipeImage.imageAlpha = 255
            view.recipeIngredients.visibility = View.INVISIBLE
        }
    }
}
