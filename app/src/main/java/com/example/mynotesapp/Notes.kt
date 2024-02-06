package com.example.mynotesapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Notes(
    val name : String,
    val description : String
):Parcelable
