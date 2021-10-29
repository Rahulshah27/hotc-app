package com.example.hotcapp.common

import com.example.hotcapp.presentation.model.ImageModel
import com.example.hotcapp.presentation.model.PhotoModel
import com.example.hotcapp.presentation.model.ThumbnailModel
import com.example.hotcapp.presentation.model.VideoModel
import java.io.File

object Constants {

    var videoSelected: ThumbnailModel?=null
    var videoFolderSelected: VideoModel?=null
    var imageSelected: ImageModel?=null
    var folderName: String?=null
    var photoFolderSelected: PhotoModel?=null
    const val FOLDER_NAME:  String = "/emulated/0/HOTC/"
    const val PHOTO: String = "Photos"
    const val VIDEO:String="Videos"
    const val PHOTOBG:String="PhotoBg"
    const val VIDEOBG:String="VideoBg"
    const val BACKGROUND = "Backgrounds"
    const val THUMBNAIL = "Thumbnails"

    enum class FileType {
        FILE,
        FOLDER;

        companion object {
            fun getFileType(file: File) = when (file.isDirectory) {
                true -> FOLDER
                false -> FILE
            }
        }
    }
}