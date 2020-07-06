package edu.utn.frba.cookingmate.ui.steps

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import edu.utn.frba.cookingmate.R
import edu.utn.frba.cookingmate.models.Recipe
import edu.utn.frba.cookingmate.models.Step
import edu.utn.frba.cookingmate.services.APIService
import edu.utn.frba.cookingmate.services.CameraService
import edu.utn.frba.cookingmate.services.StateService
import kotlinx.android.synthetic.main.fragment_steps.*
import kotlinx.android.synthetic.main.player_overlay.*
import kotlinx.android.synthetic.main.player_overlay.addStoryWrapper

class StepsFragment(
    val recipe: Recipe,
    val commentFunction: ((String) -> Unit) -> Unit,
    var stepNumber: Int
) : Fragment(), Player.EventListener {
    val name = "StepsFragment"
    private var playWhenReady: Boolean = true
    private var currentWindow: Int = 0
    private var playbackPosition: Long = 0
    private var playerView: PlayerView? = null
    private var player: SimpleExoPlayer? = null
    private lateinit var stepsTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        playerView = video_view
        configurePlayerOverlay()

        loadStep()
        addComment.setOnClickListener {
            CameraService.takePicture(this) { intent, _ ->
                startActivityForResult(
                    intent,
                    CameraService.TAKE_PICTURE_REQUEST_CODE
                )
            }
        }
    }

    private fun configurePlayerOverlay() {
        val dismissOverlay = {
            currentWindow = 0
            playbackPosition = 0
            playerView?.useController = true
            player_overlay?.visibility = View.INVISIBLE
        }

        replay.setOnClickListener {
            dismissOverlay()
            initializePlayer()
        }

        previous.setOnClickListener {
            dismissOverlay()
            stepNumber--
            initializePlayer()
        }

        next.setOnClickListener {
            dismissOverlay()
            stepNumber++
            initializePlayer()
        }

    }

    fun loadStep() {
        val step = recipe.steps[stepNumber]
        stepsTitle = step.description
        loadComments(step)
    }

    fun loadComments(step: Step) {
        val aComment = step.comments.getOrNull(0)
        aComment?.let {
            comment.text = it.text
            Glide.with(context).load(it.imageLink).into(commentPicture)
        } ?: run {
            clearComments()
        }
    }

    fun clearComments() {
        comment.text = "No hay comentarios :("
        commentPicture.setImageDrawable(null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            CameraService.TAKE_PICTURE_REQUEST_CODE ->
                if (resultCode == Activity.RESULT_OK) {
                    val profile = StateService.getCurrentProfile()

                    val imageBitmap = data!!.extras.get("data") as Bitmap

                    commentFunction { comment ->
                        APIService.addComment(
                            recipe.id,
                            profile,
                            imageBitmap,
                            comment,
                            stepNumber
                        ) {
//                        APIService.getRecipe(MainFragment.recipeIdCamera!!) { updatedRecipe ->
//                            recipes =
//                                recipes.map { if (it.id == MainFragment.recipeIdCamera) updatedRecipe else it }
//
//                            updateRecipes()
//                        }
                        }
                    }
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_steps, container, false)
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        stopPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun initializePlayer() {
        if (player == null) {
            val trackSelector = DefaultTrackSelector(context!!)
            trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd())
            player = SimpleExoPlayer.Builder(context!!).setTrackSelector(trackSelector).build()
        }
        playerView?.player = player
        val uri = Uri.parse(recipe.steps[stepNumber].videoUrl)
        val mediaSource = buildMediaSource(uri)

        player?.let {
            it.playWhenReady = playWhenReady
            it.seekTo(currentWindow, playbackPosition)
            it.addListener(this)
            it.prepare(mediaSource, false, false)
        }
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_ENDED) {
            playerView!!.useController = false
            player_overlay?.visibility = View.VISIBLE


            if (recipe.stories.any { it.profileId == StateService.getCurrentProfile().id }) {
                addStoryWrapper.visibility = View.GONE
//            } else {
//                addStoryWrapper.setOnClickListener {  }
            }

            previous.also {
                if (stepNumber != 0) {
                    enableButton(it)
                } else {
                    disableButton(it)
                }
            }

            next.also {
                if (stepNumber < recipe.steps.size - 1) {
                    enableButton(it)
                } else {
                    disableButton(it)
                }
            }
        }

    }

    private fun disableButton(image: ImageView) {
        val buttonDrawable = DrawableCompat.wrap(image.background)
        DrawableCompat.setTint(buttonDrawable, Color.GRAY)
        image.background = buttonDrawable
        image.isClickable = false
    }

    private fun enableButton(image: ImageView) {
        val buttonDrawable = DrawableCompat.wrap(image.background)
        DrawableCompat.setTint(buttonDrawable, Color.WHITE)
        image.background = buttonDrawable
        image.isClickable = true
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val defaultDataSourceFactory = DefaultDataSourceFactory(context!!, "cookingMate")
        return HlsMediaSource.Factory(defaultDataSourceFactory).createMediaSource(uri)
    }

    private fun stopPlayer() {
        player?.let {
            playbackPosition = it.currentPosition;
            currentWindow = it.currentWindowIndex;
            playWhenReady = it.playWhenReady;
            it.playWhenReady = false
        }
    }

    private fun releasePlayer() {
        player?.release()
    }

    companion object {
        @JvmStatic
        fun newInstance(
            recipe: Recipe,
            commentFunction: ((String) -> Unit) -> Unit,
            stepNumber: Int = 0
        ) =
            StepsFragment(recipe, commentFunction, stepNumber).apply {
                arguments = Bundle().apply {}
            }
    }
}
