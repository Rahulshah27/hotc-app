package com.example.hotcapp.presentation.view.photos

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
import com.example.hotcapp.presentation.adapter.PhotoAdapter
import com.example.hotcapp.presentation.model.PhotoModel
import com.example.hotcapp.presentation.view.HomeActivity
import com.example.hotcapp.presentation.view.HomeFragment
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.fragment_photos.*
import java.io.File

class PhotosFragment: Fragment()  {
    private lateinit var PATH: String
    companion object {
        private const val ARG_PATH: String = "HOTC"
        fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var path: String = ""

        fun build(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString(ARG_PATH, path)
            fragment.arguments = args;
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivArrowBack.setOnClickListener {
            (activity as HomeActivity).onBackPressed()
        }
        rvPhotos.adapter = PhotoAdapter(::itemClicked)
        rvPhotos.setHasFixedSize(true)
        tvName.setText(R.string.photo)
        loadData()

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun itemClicked(photoModel: PhotoModel) {
        (activity as HomeActivity).addReplaceFragment(ImageFragment(),2, Constants.folderName!!)
        Constants.photoFolderSelected = photoModel
    }

    private fun loadData() {
        try {
            val nameFolder = arguments?.getString(Constants.FOLDER_NAME,"")

            val fileName = nameFolder?.let { (activity as HomeActivity).getFilesFromPath(it) }!!
            //Getting Main Folder Under HOTC
            val file = fileName?.get(0)
            file.listFiles()?.filter {
                it.name == Constants.BACKGROUND
            }?.mapNotNull {
                it.listFiles()!!.forEach {
                    if (it.name.trim() == Constants.PHOTO){
                        Glide.with(requireContext()).load(it.listFiles()!!.get(0).absoluteFile).into(bgImg)
                    }
                }
            }
            Constants.folderName = file?.path
            val mainFile = file?.listFiles()?.filter {
                it.name.trim() == Constants.BACKGROUND
            }?.mapNotNull {
                it.listFiles().filter {
                    it.name.trim() == Constants.PHOTOBG
                }.mapNotNull {
                    (rvPhotos.adapter as PhotoAdapter).photoList= getPhotoModelsFromFiles(it.listFiles()!!.toList().sortedBy { it.name })

                }


            }


        }catch (e:Exception){
//            Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
        }

    }
    fun getPhotoModelsFromFiles(files: List<File>): List<PhotoModel> {
        return files.map {
            val myBitmap = it.listFiles()?.get(0)?.absolutePath!!

            PhotoModel(it.name,myBitmap)
        }
    }

}