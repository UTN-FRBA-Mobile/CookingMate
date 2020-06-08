package edu.utn.frba.cookingmate.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.utn.frba.cookingmate.R
import edu.utn.frba.cookingmate.models.Recipe
import edu.utn.frba.cookingmate.services.APIService
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {
    val name = "MainFragment"
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        APIService.loadProfiles { APIService.getRecipes { setRecipes(it) } }
    }

    private fun setRecipes(recipes: List<Recipe>) {
        val viewManager = LinearLayoutManager(this.context)
        val viewAdapter = RecipesAdapter(
            listener,
            recipes
        ) { listener!!.onViewRecipeSteps(it) }

        recyclerView = feedRecycler.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onViewRecipeSteps(recipe: Recipe)
        fun onViewRecipeStories(recipe: Recipe, profileId: String)
    }
}
