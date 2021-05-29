package com.dicoding.githubuser.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.dicoding.githubuser"
    const val SCHEME = "content"

    class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favourite_user"
            const val AVATAR = "avatar"
            const val USERNAME = "username"
            const val NAME = "name"
            const val REPOSITORY = "repository"
            const val COMPANY = "company"
            const val LOCATION = "location"
            const val FAVORITE = "isFav"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}