package edu.utn.frba.cookingmate.ui.stories

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.RequestOptions
import edu.utn.frba.cookingmate.R
import edu.utn.frba.cookingmate.models.Story
import edu.utn.frba.cookingmate.services.APIService
import kotlinx.android.synthetic.main.fragment_stories.*

class StoriesFragment(private val stories: List<Story>, private val selectedProfileId: String) :
    Fragment() {
    val name: String = "StoriesFragment"
    private var listener: OnFragmentInteractionListener? = null
    private val posXY = IntArray(2)
    private var currentStoryPosition =
        stories.indexOfFirst { it.profileId == selectedProfileId }

    companion object {
        @JvmStatic
        fun newInstance(stories: List<Story>, profileId: String) =
            StoriesFragment(stories, profileId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stories, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        storyImage.getLocationOnScreen(posXY)
        storyImage.setOnTouchListener { _, event -> onImageTouch(event) }

        showStory()
    }

    private fun onImageTouch(event: MotionEvent): Boolean {
        val imageX = event.x.toInt() - posXY[0]

        if (imageX < storyImage.width / 2) {
            if (currentStoryPosition > 0) {
                currentStoryPosition--
                showStory()
            }
        } else {
            if (currentStoryPosition < stories.size - 1) {
                currentStoryPosition++
                showStory()
            } else {
                listener!!.onViewMainFeed()
            }
        }

        return false
    }

    private fun showStory() {
        val storyToShow = stories[currentStoryPosition]
        val profile = APIService.profilesMap[storyToShow.profileId]!!

        Glide.with(view)
            .load(storyToShow.imageLink)
            .apply(RequestOptions().transform(FitCenter()))
            .into(storyImage)

        authorName.text = profile.name
        Glide.with(view)
            .load(profile.profileImageLink)
            .apply(RequestOptions().transform(CircleCrop()))
            .into(authorImage)
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
        fun onViewMainFeed()
    }
}
