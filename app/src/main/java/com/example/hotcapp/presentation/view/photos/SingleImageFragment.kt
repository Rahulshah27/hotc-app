package com.example.hotcapp.presentation.view.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.hotcapp.R
import com.example.hotcapp.common.Constants
import com.example.hotcapp.presentation.view.HomeActivity
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.fragment_single_image.*

class SingleImageFragment  : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivArrowBack.setOnClickListener {
            (activity as HomeActivity).onBackPressed()

        }

        tvName.text = null
        Glide.with(requireContext()).load(Constants.imageSelected!!.image).into(ivImg)
    }
}