package ru.netology.singlealbumapphometask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.singlealbumapphometask.dto.Track
import ru.netology.singlealbumapphometask.repository.TrackRepository
import ru.netology.singlealbumapphometask.repository.TrackRepositoryImpl

class TrackViewModel: ViewModel() {
    private val repository: TrackRepository = TrackRepositoryImpl()
    private val _data = MutableLiveData<List<Track>>()
    val data: LiveData<List<Track>>
        get() = _data

    init {
        getTracks()
    }

    private fun getTracks() {
        repository.getTracks(object: TrackRepository.GetTracksCallBack{
            override fun onSuccess(tracks: List<Track>) {
                _data.postValue(tracks)
            }
        })
    }
    fun playOrPauseTrack(track: Track): Boolean? {
        _data.value = _data.value?.map {
            if (track.id == it.id) {
                it.copy(isPlaying = !track.isPlaying)
            } else {
                it
            }
        }
        return data.value?.first {
            it.id == track.id
        }?.isPlaying
    }

    fun getTrackById(id: Long): Track? {
        return data.value?.first {
            it.id == id
        }
    }

    fun getAll(): List<Track>? {
        return data.value
    }
}