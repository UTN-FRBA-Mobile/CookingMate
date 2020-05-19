package edu.utn.frba.cookingmate.ui.steps

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import edu.utn.frba.cookingmate.R
import edu.utn.frba.cookingmate.models.Recipe
import kotlinx.android.synthetic.main.fragment_steps.*


class StepsFragment(val recipe: Recipe) : Fragment(), Player.EventListener {
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

        stepsTitle = recipe.name

        playerView = video_view

//        playbackStateListener = PlaybackStateListener()
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
        releasePlayer()
    }

    private fun initializePlayer() {
        if (player == null) {
            val trackSelector = DefaultTrackSelector(context!!)
            trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd())
            player = SimpleExoPlayer.Builder(context!!).setTrackSelector(trackSelector).build()
        }
        playerView?.player = player
        val uri = Uri.parse(getString(R.string.media_url_hls))
        val mediaSource = buildMediaSource(uri)

        player?.let {
            it.playWhenReady = playWhenReady
            it.seekTo(currentWindow, playbackPosition)
            it.addListener(this)
            it.prepare(ConcatenatingMediaSource(mediaSource), false, false)
        }
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_ENDED) {
            //TODO solo lo pongo a modo de prueba, para mostrar las funcionalidades que queremos agregar en este punto
            Toast.makeText(context!!, "TODO: Los botones son a modo ilustrativo", Toast.LENGTH_LONG).show()
            playerView!!.hideController()
            next!!.visibility = View.VISIBLE
            replay!!.visibility = View.VISIBLE
            story!!.visibility = View.VISIBLE
        }

    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val defaultDataSourceFactory = DefaultDataSourceFactory(context!!, "cookingMate")
        return HlsMediaSource.Factory(defaultDataSourceFactory).createMediaSource(uri)
    }

    private fun releasePlayer() {
        player?.let {
            playbackPosition = it.currentPosition;
            currentWindow = it.currentWindowIndex;
            playWhenReady = it.playWhenReady;
            it.release()
            player == null
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(recipe: Recipe) =
            StepsFragment(recipe).apply {
                arguments = Bundle().apply {}
            }
    }
}