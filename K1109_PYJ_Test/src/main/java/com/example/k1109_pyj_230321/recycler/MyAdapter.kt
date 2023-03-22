package com.example.k1109_pyj_230321.recycler

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.k1109_pyj_230321.databinding.ItemMainBinding
import com.example.k1109_pyj_230321.model.ItemModel


class MyViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)

class MyAdapter(val context: Context, val datas: List<ItemModel>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun getItemCount(): Int{
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = MyViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyViewHolder).binding

        //add......................................
        val user = datas?.get(position)
        binding.firstNameView.text = user?.RSTR_NM
        val urlImg = user?.FOOD_IMG_URL

        Glide.with(context)
            .asBitmap()
            .load(urlImg)
            .into(object : CustomTarget<Bitmap>(100, 100) {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    binding.avatarView.setImageBitmap(resource)
                    Log.d("lsy", "width : ${resource.width}, height: ${resource.height}")
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }
            })

    }
}
