package com.dicoding.githubuser.helper

import android.database.Cursor
import com.dicoding.githubuser.data.UserFavorite
import com.dicoding.githubuser.db.DatabaseContract
import java.util.ArrayList

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