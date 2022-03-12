package ru.netology.singlealbumapphometask.repository

import com.google.gson.Gson
import okhttp3.*
import ru.netology.singlealbumapphometask.dto.SoundHelixSource
import java.io.IOException
import java.util.concurrent.TimeUnit

class TrackRepositoryImpl : TrackRepository {
    private val url: String = "https://raw.githubusercontent.com/netology-code/andad-homeworks/master/09_multimedia/data/album.json"

    private val gson = Gson()
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    private val request = Request.Builder()
        .url(url)
        .build()

    override fun getTracks(callback: TrackRepository.GetTracksCallBack) {
        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        throw IOException("Connection error")
                    }
                    val body = response.body?.string() ?: throw RuntimeException("body is null")
                    try {
                        callback.onSuccess(gson.fromJson(body, SoundHelixSource::class.java).tracks)
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

            })

    }
}