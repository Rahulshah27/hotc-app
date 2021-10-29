package com.example.hotcapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotcapp.presentation.model.ImageModel
import java.io.File


class ImageViewModel: ViewModel() {

    var imageList = MutableLiveData<List<ImageModel>>()

    fun getImageList(files: List<File>){
        imageList.value = getImageModelsFromFiles(files)
    }

    fun getImageModelsFromFiles(files: List<File>): List<ImageModel> {
        return files.filter { it.absolutePath.contains(".jpg") || it.absolutePath.contains(".png")}.map {

            val myBitmap = it.absolutePath

            ImageModel(myBitmap)
        }
    }
}