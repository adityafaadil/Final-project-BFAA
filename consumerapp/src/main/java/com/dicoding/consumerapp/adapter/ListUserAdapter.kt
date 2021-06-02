package com.dicoding.consumerapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.consumerapp.activity.DetailActivity
import com.dicoding.consumerapp.data.User
import com.dicoding.consumerapp.databinding.ItemRowUserBinding

class ListUserAdapter (private val listUser: ArrayList<User>): RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    lateinit var context: Context

    inner class ListViewHolder (private val binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(100,100))
                    .into(tvAvatar)

                tvRepository.text = user.repository
                tvUsername.text = user.username
                tvLocation.text = user.location

                itemView.setOnClickListener {
                    val userData = User()
                    userData.username = user.username
                    userData.name = user.username
                    userData.avatar = user.avatar
                    userData.repository = user.repository
                    userData.company = user.company
                    userData.location = user.location
                    userData.followers = user.followers
                    userData.following = user.following
                    userData.isFav = user.isFav

                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DATA, userData)
                    intent.putExtra(DetailActivity.EXTRA_FAVORITE, userData)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }
}