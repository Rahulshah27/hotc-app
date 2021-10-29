package com.example.hotcapp.presentation.view.photos

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotcapp.R
import com.example.hotcapp.common.Constants
import com.example.hotcapp.presentation.adapter.ImageAdapter
import com.example.hotcapp.presentation.model.ImageModel
import com.example.hotcapp.presentation.view.HomeActivity
import com.example.hotcapp.presentation.viewmodel.ImageViewModel
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.fragment_image.*

class ImageFragment : Fragment() {

    private var imageViewModel:ImageViewModel?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        imageViewModel = ViewModelProvider(this)[ImageViewModel::class.java]
        loadData()
    }

    private fun initView() {
        tvName.text = Constants.photoFolderSelected?.name
        ivArrowBack.setOnClickListener {
            (activity as HomeActivity).onBackPressed()
        }

        rvImage.adapter = ImageAdapter(::imageClicked)
        rvImage.setHasFixedSize(true)
        rvImage.layoutManager = GridLayoutManager(requireContext(),4,RecyclerView.VERTICAL,false)


    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun imageClicked(imageModel: ImageModel) {
        Constants.imageSelected = imageModel
        (activity as HomeActivity).addReplaceFragment(SingleImageFragment(),2,Constants.folderName!!)
    }

    private fun loadData() {
        try {

            val fileName = (activity as HomeActivity).getFilesFromPath(Constants.folderName!!).filter {
                it.name == Constants.PHOTO
            }.map {f1->
                f1.listFiles()!!.filter {
                        f2->f2.name == Constants.photoFolderSelected!!.name
                }.map {
                        f3->
                    imageViewModel?.getImageList(f3.listFiles()!!.toList().filter { it.name != Constants.BACKGROUND })
                }
            }


            imageViewModel?.imageList?.observe(viewLifecycleOwner,{
                (rvImage.adapter as ImageAdapter).imageList = it
            })



        }catch (e:Exception){
            Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
        }
    }

}