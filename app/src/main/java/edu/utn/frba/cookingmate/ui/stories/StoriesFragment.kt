package edu.utn.frba.cookingmate.ui.stories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import edu.utn.frba.cookingmate.R
import edu.utn.frba.cookingmate.models.Recipe

class StoriesFragment(val recipe: Recipe) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stories, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(recipe: Recipe) =
            StoriesFragment(recipe)
    }
}
