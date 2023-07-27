package com.example.androidmoviesproject.data.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.androidmoviesproject.data.model.actorMovie.ModelCredits
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * To use Room Database converter
 */
@ProvidedTypeConverter
class CreditsConverter {

    @TypeConverter
    fun convertToCredits(value: String): ModelCredits? {
        if (value.isNullOrEmpty()) {
            val gson: Gson = Gson()
            val listType: Type = object : TypeToken<ModelCredits>() {}.type
            return gson.fromJson<ModelCredits>(value, listType)
        }
        return null
    }

    @TypeConverter
    fun convertToString(credits: ModelCredits): String? {
        return Gson().toJson(credits) ?: null
    }
}