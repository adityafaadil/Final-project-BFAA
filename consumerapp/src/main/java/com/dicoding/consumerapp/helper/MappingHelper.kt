package com.dicoding.consumerapp.helper

import android.database.Cursor
import com.dicoding.consumerapp.data.UserFavorite
import com.dicoding.consumerapp.db.DatabaseContract
import java.util.*

object MappingHelper {

    fun mapCursorToArrayList(favsCursor: Cursor?): ArrayList<UserFavorite>{
        val favsList = ArrayList<UserFavorite>()
        favsCursor?.apply {
            while (moveToNext()) {
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.NAME))
                val repository = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.REPOSITORY))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COMPANY))
                val location = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOCATION))
                val favourite = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FAVORITE))

                favsList.add(UserFavorite(avatar, username, name, repository, company, location, favourite))
            }
        }
        return favsList
    }
}