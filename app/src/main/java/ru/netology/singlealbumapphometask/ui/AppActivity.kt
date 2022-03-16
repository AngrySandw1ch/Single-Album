package ru.netology.singlealbumapphometask.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.singlealbumapphometask.adapter.OnInteractionListener
import ru.netology.singlealbumapphometask.adapter.TrackAdapter
import ru.netology.singlealbumapphometask.databinding.ActivityAppBinding
import ru.netology.singlealbumapphometask.dto.Track
import ru.netology.singlealbumapphometask.observer.MediaLifecycleObserver
import ru.netology.singlealbumapphometask.viewmodel.TrackViewModel

const val BASE_URL = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-"

class AppActivity : AppCompatActivity() {
    lateinit var binding: ActivityAppBinding
    private val viewModel: TrackViewModel by viewModels()
    var currentTrack: Track? = Track()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mediaObserver = MediaLifecycleObserver()
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycle.addObserver(mediaObserver)

        if (savedInstanceState != null) {
            currentTrack = viewModel.getTrackById(savedInstanceState.getLong("currentTrackId"))
        }




        val adapter = TrackAdapter(object : OnInteractionListener {
            override fun onPlayOrPause(track: Track) {
                val trackStatus = viewModel.playOrPauseTrack(track)
                trackStatus?.let {
                    if (!it) {
                        mediaObserver.pause()
                        return
                    }
                }
                mediaObserver.apply {
                    if (currentTrack?.id != track.id && currentTrack?.id != 0L) {
                        currentTrack?.let { viewModel.playOrPauseTrack(it) }
                    }
                    player?.reset()
                    player?.setDataSource(this@AppActivity, Uri.parse("$BASE_URL${track.file}"))
                }.play()
                currentTrack = track.copy(isPlaying = !track.isPlaying)
            }
        })

        mediaObserver.player?.setOnCompletionListener { mPlayer ->
            currentTrack = currentTrack?.copy(isPlaying = false)
            currentTrack?.let { track ->
                viewModel.playOrPauseTrack(track)
            }
            mPlayer.reset()
            mPlayer.setDataSource(this@AppActivity, Uri.parse(BASE_URL + "2.mp3"))
            mPlayer.start()
            return@setOnCompletionListener
        }

        binding.recycler.adapter = adapter
        viewModel.data.observe(this) {
            adapter.submitList(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        currentTrack?.let { outState.putLong("currentTrackId", it.id) }
    }
}