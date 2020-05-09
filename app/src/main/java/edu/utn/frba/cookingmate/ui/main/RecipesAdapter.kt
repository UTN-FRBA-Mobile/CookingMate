package edu.utn.frba.cookingmate.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.utn.frba.cookingmate.R
import edu.utn.frba.cookingmate.models.Recipe
import kotlinx.android.synthetic.main.fragment_recipe.view.*

class RecipesAdapter(
    private val myDataset: MutableList<Recipe>,
    private val onClickListener: (Recipe) -> Unit
) :
    RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

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

        holder.view.findViewById<TextView>(R.id.recipeName).text = recipe.name
        holder.view.viewRecipe.setOnClickListener { onClickListener(recipe) }
    }

    override fun getItemCount() = myDataset.size
}
