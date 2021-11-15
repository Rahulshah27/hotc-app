package com.example.hotcapp.presentation.view.videos

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.hotcapp.R
import com.example.hotcapp.common.Constants
import com.example.hotcapp.presentation.adapter.VideoListAdapter
import com.example.hotcapp.presentation.model.ThumbnailModel
import com.example.hotcapp.presentation.view.HomeActivity
import com.example.hotcapp.presentation.view.HomeFragment
import com.example.hotcapp.presentation.viewmodel.VideoListViewModel
import kotlinx.android.synthetic.main.app_bar.*

import kotlinx.android.synthetic.main.fragment_list_videos.bgImg
import kotlinx.android.synthetic.main.fragment_list_videos.rvVideos


class VideoListFragment : Fragment() {

    private var thumbNailViewModel: VideoListViewModel?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        thumbNailViewModel = ViewModelProvider(this)[VideoListViewModel::class.java]
        loadData()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initView() {
        tvName.text = Constants.videoFolderSelected?.name
        ivArrowBack.setOnClickListener {
            (activity as HomeActivity).onBackPressed()
        }
        ivHome.setOnClickListener {
            (activity as HomeActivity).addReplaceFragment(HomeFragment(),1,Constants.FOLDER_NAME)
        }
        Glide.with(requireContext()).load(Constants.VIDEOBACKGROUND).into(bgImg)
        rvVideos.adapter = VideoListAdapter(::videoClicked)
        rvVideos.setHasFixedSize(true)


    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun videoClicked(videoModel: ThumbnailModel) {
        Constants.videoSelected = videoModel
        (activity as HomeActivity).addReplaceFragment(SingleVideoFragment(),2,Constants.folderName!!)
    }

    private fun loadData() {
        try {
            //For background
            val nameFolder = arguments?.getString(Constants.FOLDER_NAME,"")

            val fileNames = nameFolder?.let { (activity as HomeActivity).getFilesFromPath(it) }
            val file = fileNames?.get(0)

            file?.listFiles()?.filter {
                it.name == Constants.VIDEO
            }?.mapNotNull {
                it.listFiles()!!.forEach {
                    if (it.name.trim() == Constants.BACKGROUND){
                        Glide.with(requireContext()).load(it.listFiles()!!.get(0).absoluteFile).into(bgImg)
                    }
                }
            }


            //Video list thumbnails
            val fileName = (activity as HomeActivity).getFilesFromPath(Constants.folderName!!).filter {
                it.name == Constants.VIDEO
            }.mapNotNull {f1->
                f1.listFiles()!!.filter {
                        f2->f2.name == Constants.videoFolderSelected!!.name
                }.mapNotNull {
                        f3->
                    f3.listFiles()!!.filter {

                        it.name == Constants.THUMBNAIL


                    }.mapNotNull {

                        thumbNailViewModel?.getVideoList(
                            it.listFiles()!!.toList()
                                .sortedBy { it.name })
                    }
                }
            }
            thumbNailViewModel?.videoList?.observe(viewLifecycleOwner,{
                (rvVideos.adapter as VideoListAdapter).videoList = it
            })

        }catch (e:Exception){
            Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
        }
    }

}