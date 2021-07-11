package com.meinvodafone.testflow.data.models

import com.google.gson.annotations.SerializedName

data class Movie(
    var id: Long=0L,
    var title: String,
    @SerializedName("abstract")
    var details: String?="",
    @SerializedName("media")
    var mediaList: List<MediaMetaData>? = emptyList(),
    @SerializedName("published_date")
    var publishDate: String
)

class MediaMetaData(
    @SerializedName("media-metadata")
    val mediaUrl: List<MediaUrls>
)

class MediaUrls(val url: String)

class MoviesList(@SerializedName("results") val moviesList: List<Movie>)
