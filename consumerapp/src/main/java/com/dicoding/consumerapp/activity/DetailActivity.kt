package com.dicoding.consumerapp.activity

import android.content.ContentValues
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.consumerapp.R
import com.dicoding.consumerapp.adapter.PageAdapter
import com.dicoding.consumerapp.data.User
import com.dicoding.consumerapp.data.UserFavorite
import com.dicoding.consumerapp.databinding.ActivityDetailBinding
import com.dicoding.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.AVATAR
import com.dicoding.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.COMPANY
import com.dicoding.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.dicoding.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.FAVORITE
import com.dicoding.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.LOCATION
import com.dicoding.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.NAME
import com.dicoding.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.REPOSITORY
import com.dicoding.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.USERNAME


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    //private lateinit var favoriteHelper: FavoriteHelper
    private lateinit var imgAvatar: String

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_FAVORITE = "extra_favorite"
        const val EXTRA_POSITION = "extra_position"
    }

    private var favorites: UserFavorite? = null
    private var isFav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        //favoriteHelper.open()

        favorites = intent.getParcelableExtra(EXTRA_NOTE)
        if (favorites != null) {
            val favoriteUser = intent.getParcelableExtra<UserFavorite>(EXTRA_NOTE) as UserFavorite
            Glide.with(this)
                .load(favoriteUser.avatar)
                .into(binding.detailAvatar)

            binding.detailUsername.text = favoriteUser.username
            binding.detailName.text = favoriteUser.name
            binding.detailRepository.text = favoriteUser.repository
            binding.detailCompany.text = favoriteUser.company
            binding.detailLocation.text = favoriteUser.location

            imgAvatar = favoriteUser.avatar.toString()
            val checked: Int = R.drawable.ic_baseline_favorite_24
            binding.fav.setImageResource(checked)
        } else {
            val user = intent.getParcelableExtra<User>(EXTRA_DATA) as User
            Glide.with(this)
                .load(user.avatar)
                .into(binding.detailAvatar)

            binding.detailUsername.text = user.username
            binding.detailName.text = user.name
            binding.detailRepository.text = user.repository
            binding.detailCompany.text = user.company
            binding.detailLocation.text = user.location
            imgAvatar = user.avatar.toString()
        }

        binding.fav.setOnClickListener {
            btnFav()
        }

        val pageAdapter = PageAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = pageAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        supportActionBar?.elevation = 0f
    }

    private fun btnFav() {
        val unchecked: Int = R.drawable.ic_baseline_favorite_border_24
        val cheked: Int = R.drawable.ic_baseline_favorite_24

        if (isFav == true) {
            //favoriteHelper.deleteById(favorites?.username.toString())
            binding.fav.setImageResource(unchecked)
            isFav = false
        } else {
            val avatar = imgAvatar
            val username = binding.detailUsername.text.toString()
            val name = binding.detailName.text.toString()
            val repository = binding.detailRepository.text.toString()
            val company = binding.detailCompany.text.toString()
            val location = binding.detailLocation.text.toString()
            val fav = "1"

            val values = ContentValues()
            values.put(AVATAR, avatar)
            values.put(USERNAME, username)
            values.put(NAME, name)
            values.put(REPOSITORY, repository)
            values.put(COMPANY, company)
            values.put(LOCATION, location)
            values.put(FAVORITE, fav)

            isFav = true
            contentResolver.insert(CONTENT_URI, values)
            binding.fav.setImageResource(cheked)
        }
    }

   /** override fun onDestroy() {
        super.onDestroy()
        favoriteHelper.close()
    }**/
}