package com.example.hotcapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotcapp.R
import com.example.hotcapp.presentation.model.ImageModel
import kotlinx.android.synthetic.main.list_item_image.view.*

class ImageAdapter(val callable:(ImageModel)->Unit): RecyclerView.Adapter<ImageAdapter.VH>() {

    var imageList:List<ImageModel> = ArrayList<ImageModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView){
        init {
            with(itemView){
                setOnClickListener {
                    callable.invoke(imageList[adapterPosition])
                }
            }
        }
        fun bind(data: ImageModel?){
            with(itemView){
                try {
                    Glide.with(context).load(data?.image).into(ivImage)
                }catch (e:Exception){
                    Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
                }



            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.list_item_image,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(imageList.get(position))
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}