package ru.netology.singlealbumapphometask.dto

data class Track(
    val id: Long = 0,
    val file: String = "",
    var isPlaying: Boolean = false
)