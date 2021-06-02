package com.dicoding.consumerapp.activity

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.consumerapp.adapter.FavoriteAdapter
import com.dicoding.consumerapp.databinding.ActivityFavoriteBinding
import com.dicoding.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.dicoding.consumerapp.helper.MappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter

    companion object {
        private const val EXTRA_STATE = "extra_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.setHasFixedSize(true)

        adapter = FavoriteAdapter(this)
        binding.rvFavorite.adapter = adapter

        val handlerThread = HandlerThread("Data Observer")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                loadFavsAsync()
            }
        }
    }

    private fun loadFavsAsync(){
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBarFavorite.visibility = View.VISIBLE

            val defferedFavs = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favourites = defferedFavs.await()
            binding.progressBarFavorite.visibility = View.INVISIBLE
            if (favourites.size > 0) {
                adapter.listFavorite = favourites
            } else {
                adapter.listFavorite = ArrayList()
                showSnackBarMessage("No Data")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavorite)
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(binding.rvFavorite, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        loadFavsAsync()
    }
}