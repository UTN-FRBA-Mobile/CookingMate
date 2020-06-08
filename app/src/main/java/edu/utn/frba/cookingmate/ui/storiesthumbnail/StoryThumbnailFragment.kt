package edu.utn.frba.cookingmate.ui.storiesthumbnail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.utn.frba.cookingmate.R
import edu.utn.frba.cookingmate.models.Story

class StoryThumbnailFragment(val story: Story) : Fragment() {
    companion object {
        fun newInstance(story: Story) =
            StoryThumbnailFragment(
                story
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.story_thumbnail_fragment, container, false)
    }
}
