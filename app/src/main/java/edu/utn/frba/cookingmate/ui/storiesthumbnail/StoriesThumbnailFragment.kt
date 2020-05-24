package edu.utn.frba.cookingmate.ui.storiesthumbnail

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
import kotlinx.android.synthetic.main.stories_thumbnail_fragment.*

class StoriesThumbnailFragment(private val recipe: Recipe) : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var recipeStoriesThumbnailRecyclerView: RecyclerView

    companion object {
        fun newInstance(recipe: Recipe) =
            StoriesThumbnailFragment(
                recipe
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.stories_thumbnail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewManager =
            LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        val viewAdapter =
            StoriesThumbnailAdapter(
                recipe,
                { listener!!.onViewRecipeStories(it) }
            )

        recipeStoriesThumbnailRecyclerView = recipeStoriesThumbnailRecycler.apply {
            setHasFixedSize(false)
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
        fun onViewRecipeStories(recipe: Recipe)
    }
}
