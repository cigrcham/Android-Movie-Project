package com.example.androidmoviesproject

import com.example.androidmoviesproject.data.model.actorMovie.Cast
import com.example.androidmoviesproject.data.model.actorMovie.ModelCredits
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//fun main() {
////    val jsonElement = Gson().toJson(getList())
////    println(jsonElement)
//    val jsonString =
//        """[{"cast":[{},{},{},{},{}],"id":1},{"id":2},{"id":3},{"id":4}]"""
//    val gson = Gson()
//    val listType = object : TypeToken<List<ModelCredits>>() {}.type
//
//    val objectList = gson.fromJson<List<ModelCredits>>(jsonString, listType)
//    println(objectList)
//}
//
//data class Work(
//    val name: String, val id: String
//)
//
//fun getList(): List<ModelCredits> {
//    return listOf(
//        ModelCredits(id = 1, cast = listOf(Cast(), Cast(), Cast(), Cast(), Cast())),
//        ModelCredits(id = 2),
//        ModelCredits(id = 3),
//        ModelCredits(id = 4)
//    )
//}