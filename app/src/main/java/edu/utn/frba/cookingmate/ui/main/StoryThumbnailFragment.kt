package edu.utn.frba.cookingmate.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import edu.utn.frba.cookingmate.R
import edu.utn.frba.cookingmate.models.Story

class StoryThumbnailFragment(val story: Story) : Fragment() {

    companion object {
        fun newInstance(story: Story) = StoryThumbnailFragment(story)
    }

    private lateinit var viewModel: StoryThumbnailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.story_thumbnail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StoryThumbnailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
