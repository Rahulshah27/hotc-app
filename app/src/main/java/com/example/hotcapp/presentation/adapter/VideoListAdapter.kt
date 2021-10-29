package com.example.hotcapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotcapp.R
import com.example.hotcapp.presentation.model.ThumbnailModel
import kotlinx.android.synthetic.main.list_item_video_list.view.*

class VideoListAdapter(val callable:(ThumbnailModel)->Unit): RecyclerView.Adapter<VideoListAdapter.VH>() {

    var videoList:List<ThumbnailModel> = ArrayList<ThumbnailModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView){
        init {
            with(itemView){
                setOnClickListener {
                    callable.invoke(videoList[adapterPosition])
                }
            }
        }
        fun bind(data: ThumbnailModel?){
            with(itemView){
                try {
                    Glide.with(context).load(data?.thumbNail).into(ivImg)
                }catch (e:Exception){
                    Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
                }



            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.list_item_video_list,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(videoList[position])
    }

    override fun getItemCount(): Int {
        return videoList.size
    }
}