package com.dicoding.githubuser.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubuser.activity.DetailActivity
import com.dicoding.githubuser.databinding.ItemRowUserBinding
import com.dicoding.githubuser.data.UserFavorite
import com.dicoding.githubuser.helper.CustomOnItemClickListener

class FavoriteAdapter(private val activity: Activity) : RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {

    var listFavorite = ArrayList<UserFavorite>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)

            notifyDataSetChanged()
        }

    inner class ListViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userFavorite: UserFavorite){
            with(binding) {
                Glide.with(itemView.context)
                    .load(userFavorite.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(tvAvatar)

                tvRepository.text = userFavorite.repository
                tvUsername.text = userFavorite.username
                tvLocation.text = userFavorite.location

                itemView.setOnClickListener(
                    CustomOnItemClickListener(
                        adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback{
                            override fun onItemClicked(view: View, position: Int) {
                                val intent = Intent(activity, DetailActivity::class.java)
                                intent.putExtra(DetailActivity.EXTRA_POSITION, position)
                                intent.putExtra(DetailActivity.EXTRA_NOTE, userFavorite)
                                activity.startActivity(intent)
                            }
                        }
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listFavorite.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }
}