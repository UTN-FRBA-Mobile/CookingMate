package edu.utn.frba.cookingmate.ui.main

import android.content.Context
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
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        val myDataset =
            mutableListOf(
                Recipe("id2", "recipe 2"),
                Recipe("id1", "recipe lean"),
                Recipe("id3", "recipe 3")
            )

        val viewManager = LinearLayoutManager(this.context)
        val viewAdapter = RecipesAdapter(myDataset) { listener!!.onViewRecipeSteps(it) }

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
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onViewRecipeSteps(recipe: Recipe)
    }
}
