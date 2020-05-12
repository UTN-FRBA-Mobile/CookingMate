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
import kotlinx.android.synthetic.main.fragment_recipe.view.*


class RecipesAdapter(
    private val myDataset: MutableList<Recipe>,
    private val onClickViewStepsListener: (Recipe) -> Unit
) :
    RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    // TODO this hould be per recipe
    private var showingIngredients: Boolean = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_recipe, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recipe = myDataset[position]
        val view = holder.view

        view.recipeName.text = recipe.name
        view.viewRecipe.setOnClickListener { onClickViewStepsListener(recipe) }
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop())

        Glide.with(view)
            .load(recipe.imageLink)
            .apply(requestOptions)
            .into(holder.view.recipeImage)

        view.recipeIngredients.text = recipe.ingredientes.joinToString("\n")
        view.viewIngredients.setOnClickListener { toggleIngredients(view, recipe) }
        view.recipeIngredients.visibility = View.INVISIBLE
    }

    override fun getItemCount() = myDataset.size

    private fun toggleIngredients(view: View, recipe: Recipe) {
        showingIngredients = !showingIngredients

        if (showingIngredients) {
            view.recipeImage.imageAlpha = 100
            view.recipeIngredients.visibility = View.VISIBLE
        } else {
            view.recipeImage.imageAlpha = 255
            view.recipeIngredients.visibility = View.INVISIBLE
        }
    }
}
