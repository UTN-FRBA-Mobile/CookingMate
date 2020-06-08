package edu.utn.frba.cookingmate.ui.storiesthumbnail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.utn.frba.cookingmate.R

class StoryThumbnailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.story_thumbnail_fragment, container, false)
    }
}
