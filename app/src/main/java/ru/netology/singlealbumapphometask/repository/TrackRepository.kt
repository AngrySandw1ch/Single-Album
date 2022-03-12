package ru.netology.singlealbumapphometask.repository

import ru.netology.singlealbumapphometask.dto.Track

interface TrackRepository {
    fun getTracks(callback: GetTracksCallBack){}

    interface GetTracksCallBack {
        fun onSuccess(tracks: List<Track>) {}
        fun onError(e: Exception) {}
    }
}