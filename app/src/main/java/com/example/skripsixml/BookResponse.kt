package com.example.skripsixml

data class BookResponse(
    val items: List<BookItem>
)

data class BookItem(
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val publishedDate: String,
    val imageLinks: ImageLinks?
)

data class ImageLinks(
    val thumbnail: String
)
