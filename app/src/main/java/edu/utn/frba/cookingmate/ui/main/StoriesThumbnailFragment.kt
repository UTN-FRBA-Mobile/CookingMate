package edu.utn.frba.cookingmate.ui.main

import android.graphics.drawable.GradientDrawable
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import edu.utn.frba.cookingmate.R
import edu.utn.frba.cookingmate.models.Recipe
import kotlinx.android.synthetic.main.stories_thumbnail_fragment.*

class StoriesThumbnailFragment(private val recipe: Recipe) : Fragment() {
    private lateinit var recipeStoriesThumbnailRecyclerView: RecyclerView

    companion object {
        fun newInstance(recipe: Recipe) = StoriesThumbnailFragment(recipe)
    }

    private lateinit var viewModel: StoriesThumbnailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.stories_thumbnail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StoriesThumbnailViewModel::class.java)
        // TODO: Use the ViewModel

        val viewManager =
            LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        val viewAdapter = RecipeStoriesThumbnailAdapter(recipe.stories)

        recipeStoriesThumbnailRecyclerView = recipeStoriesThumbnailRecycler.apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }
}
