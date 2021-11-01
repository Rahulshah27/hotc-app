package com.example.hotcapp.presentation.view.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hotcapp.R
import com.example.hotcapp.common.Constants
import com.example.hotcapp.presentation.adapter.SlideShowAdapter
import com.example.hotcapp.presentation.view.HomeActivity
import com.example.hotcapp.presentation.viewmodel.ImageViewModel
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.fragment_slide_show.*
import java.util.logging.Handler

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
        ivArrowBack.setOnClickListener {
            (activity as HomeActivity).onBackPressed()
        }
        viewPagerSlideShow.adapter = SlideShowAdapter()


        viewPagerSlideShow.post {
            viewPagerSlideShow.setCurrentItem(Constants.slidePosition!!,false)
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