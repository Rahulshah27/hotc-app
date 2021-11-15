package com.example.hotcapp.presentation.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.hotcapp.R
import com.example.hotcapp.common.Constants
import com.example.hotcapp.presentation.model.FileModel
import com.example.hotcapp.presentation.view.photos.PhotosFragment
import com.example.hotcapp.presentation.view.videos.VideosFragment
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.File

class HomeFragment: Fragment() {
    private lateinit var PATH: String
    private  val ARG_PATH: String = "/emulated/0/HOTC/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filePath = arguments?.getString(ARG_PATH)
        if (filePath == null) {
            Toast.makeText(context, "Path should not be null!", Toast.LENGTH_SHORT).show()
            return
        }
        PATH = filePath

        kotlin.runCatching {
            val fileName = getFileModelsFromFiles(getFilesFromPath(PATH))

            tvFolderName.text = fileName[0].name
            val bgFile = getFilesFromPath(PATH).get(0).listFiles()!!.filter {
                it.name == Constants.BACKGROUND
            }.mapNotNull {
                Glide.with(requireContext()).load(it.listFiles()!!.get(0).absolutePath).into(ivBg)
            }

        }.onFailure {
            Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
        }

        mbtPhotos.setOnClickListener {
            (activity as HomeActivity).addReplaceFragment(PhotosFragment(),2,PATH)
        }
        mbtVideos.setOnClickListener {
            (activity as HomeActivity).addReplaceFragment(VideosFragment(),2,PATH)
        }
    }

    fun getFilesFromPath(path: String, showHiddenFiles: Boolean = true, onlyFolders: Boolean = true): List<File> {
        val file = File(path)
        return file.listFiles()
            .filter { onlyFolders }
            .toList()
    }
    fun getFileModelsFromFiles(files: List<File>): List<FileModel> {
        return files.map {
            FileModel(it.path, Constants.FileType.getFileType(it), it.name, convertFileSizeToMB(it.length()), it.extension, it.listFiles()?.size
                ?: 0)
        }
    }

    fun convertFileSizeToMB(sizeInBytes: Long): Double {
        return (sizeInBytes.toDouble()) / (1024 * 1024)
    }
}