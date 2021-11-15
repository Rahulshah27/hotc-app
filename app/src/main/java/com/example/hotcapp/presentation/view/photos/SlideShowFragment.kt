package com.example.hotcapp.presentation.view.photos

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.hotcapp.R
import com.example.hotcapp.common.Constants
import com.example.hotcapp.presentation.adapter.SlideShowAdapter
import com.example.hotcapp.presentation.view.HomeActivity
import com.example.hotcapp.presentation.view.HomeFragment
import com.example.hotcapp.presentation.viewmodel.ImageViewModel
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.fragment_slide_show.*


class SlideShowFragment : Fragment() {
    private lateinit var sliderHandler: Handler
    private var imageViewModel: ImageViewModel? = null
    private lateinit var sliderRunnable: Runnable
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slide_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        imageViewModel = ViewModelProvider(this)[ImageViewModel::class.java]
        loadData()

    }

    private fun initView() {
        try {


            ivArrowBack.setOnClickListener {
                (activity as HomeActivity).onBackPressed()
            }
            ivHome.setOnClickListener {
                (activity as HomeActivity).addReplaceFragment(HomeFragment(),1,Constants.FOLDER_NAME)
            }
            Glide.with(requireContext()).load(Constants.PHOTOBACKGROUND).into(bgImg)
            viewPagerSlideShow.adapter = SlideShowAdapter()


            viewPagerSlideShow.post {
                viewPagerSlideShow.setCurrentItem(Constants.slidePosition!!, false)
            }
            ivPause.setOnClickListener {
                sliderHandler.removeCallbacks(sliderRunnable)
                ivPause.visibility = View.GONE
                ivPlayPause.visibility = View.VISIBLE
            }
            ivSlideShow.setOnClickListener {

                ivPlayPause.visibility = View.GONE
                ivPause.visibility = View.VISIBLE

                sliderHandler = Handler(Looper.getMainLooper())
                sliderRunnable = Runnable {
                    if (viewPagerSlideShow != null) {
                        if (viewPagerSlideShow.currentItem == (viewPagerSlideShow.adapter as SlideShowAdapter).mFiles.lastIndex) {
                            viewPagerSlideShow.setCurrentItem(0, true)
                        } else {
                            viewPagerSlideShow.currentItem = viewPagerSlideShow.currentItem + 1
                            sliderHandler.postDelayed(sliderRunnable, 3000)
                        }
                    }
                }
                sliderHandler.postDelayed(sliderRunnable, 3000)


                viewPagerSlideShow.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    private var currentPage: Int = 0
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        currentPage = position
                        sliderHandler.removeCallbacks(sliderRunnable)
                        sliderHandler.postDelayed(sliderRunnable, 3000)

                    }
                })


            }
        }catch (e:Exception){
            Log.v("Error",e.message!!)
        }
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
                (viewPagerSlideShow.adapter as SlideShowAdapter).mFiles = it
            })


        }catch (e:Exception){
            Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
        }

    }

}