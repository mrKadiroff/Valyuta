package com.example.login_avatar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.login_avatar.databinding.UserListBinding
import com.example.login_avatar.models.User
import com.example.login_avatar.models.Valyuta

class UserAdapter(var list: List<Valyuta>,var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<UserAdapter.Vh>() {

    inner class Vh(var userListBinding: UserListBinding) :
        RecyclerView.ViewHolder(userListBinding.root){

        fun onBind(valyuta: Valyuta) {
            userListBinding.login.text = valyuta.CcyNm_UZ
            userListBinding.root.setOnClickListener {
                onItemClickListener.onItemClick(valyuta)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(UserListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener{
        fun onItemClick(valyuta: Valyuta)
    }
}