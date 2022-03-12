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
}