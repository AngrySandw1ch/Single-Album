package ru.netology.singlealbumapphometask.observer

import android.media.MediaPlayer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MediaLifecycleObserver: LifecycleObserver {
    var player: MediaPlayer? = MediaPlayer()

    fun play() {
        player?.setOnPreparedListener {
            it.start()
            it.playbackParams
        }
        player?.prepareAsync()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        player?.pause()

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        player?.release()
        player = null
    }
}