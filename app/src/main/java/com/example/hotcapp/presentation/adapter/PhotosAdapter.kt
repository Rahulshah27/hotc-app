package com.example.hotcapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotcapp.R
import com.example.hotcapp.presentation.model.PhotoModel
import kotlinx.android.synthetic.main.list_item_photos.view.*


class PhotoAdapter(val callable:(PhotoModel)->Unit):RecyclerView.Adapter<PhotoAdapter.VH>() {

    var photoList:List<PhotoModel> = ArrayList<PhotoModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class VH(itemView:View):RecyclerView.ViewHolder(itemView){
        init {
            with(itemView){
                setOnClickListener {
                    callable.invoke(photoList[adapterPosition])
                }
            }
        }
        fun bind(data:PhotoModel){
            with(itemView){
                if (data.image.trim().isEmpty()){
                    this.visibility = View.GONE
                }
                Glide.with(context).load(data.image).into(ivPhotos)
                tvName.text = data.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.list_item_photos,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(photoList[position])
    }

    override fun getItemCount(): Int {
        val size = 5/0
        return size
    }
}