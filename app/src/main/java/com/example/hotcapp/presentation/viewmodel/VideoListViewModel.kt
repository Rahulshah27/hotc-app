package com.example.hotcapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotcapp.presentation.model.ThumbnailModel
import java.io.File

class VideoListViewModel:ViewModel() {
    var videoList = MutableLiveData<List<ThumbnailModel>>()

    fun getVideoList(files: List<File>){
        videoList.value = getVideoModelsFromFiles(files)
    }

    fun getVideoModelsFromFiles(files: List<File>): List<ThumbnailModel> {
        return files.filter { it.absolutePath.contains(".jpg") || it.absolutePath.contains(".png") }.map {

            val myBitmap = it.absolutePath

            ThumbnailModel(myBitmap,it.name)
        }
    }
}