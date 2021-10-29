package com.example.hotcapp.presentation.model

import android.graphics.Bitmap
import com.example.hotcapp.common.Constants
import java.io.File

data class ImageModel(
    val image: String
)

data class FileModel(
    val path: String,
    val fileType: Constants.FileType,
    val name: String,
    val sizeInMB: Double,
    val extension: String = "",
    val subFiles: Int = 0
)

data class PhotoModel(
    val name:String,
    val image:String
)

data class VideoModel(
    val name:String,
    val thumbNail:String
)

data class ThumbnailModel(
    val thumbNail:String,
    val name:String,
)