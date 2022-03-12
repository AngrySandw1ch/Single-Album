package ru.netology.singlealbumapphometask.dto

data class SoundHelixSource(
    val id: Long,
    val title: String,
    val subtitle: String,
    val artist: String,
    val published: String,
    val genre: String,
    val tracks: List<Track>
)