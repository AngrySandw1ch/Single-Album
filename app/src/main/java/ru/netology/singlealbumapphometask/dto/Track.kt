package ru.netology.singlealbumapphometask.dto

data class Track(
    val id: Long,
    val file: String,
    var isPlayed: Boolean = false
)