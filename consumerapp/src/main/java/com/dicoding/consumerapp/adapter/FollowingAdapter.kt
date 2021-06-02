package com.dicoding.consumerapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.consumerapp.activity.DetailActivity
import com.dicoding.consumerapp.data.User
import com.dicoding.consumerapp.databinding.ItemRowUserBinding

class FollowingAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<FollowingAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    inner class ListViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(tvAvatar)

                tvRepository.text = user.repository
                tvUsername.text = user.username
                tvLocation.text = user.location

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(user)
                    Toast.makeText(
                        itemView.context,
                        "@${user.username}",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DATA, user)
                    intent.putExtra(DetailActivity.EXTRA_FAVORITE, user)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }
}