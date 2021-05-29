package com.dicoding.githubuser.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var avatar: String? = null,
    var username: String? = null,
    var name: String? = null,
    var repository: String? = null,
    var company: String? = null,
    var location: String? = null,
    var followers: String? = null,
    var following: String? = null,
    var isFav: String? = null
): Parcelable