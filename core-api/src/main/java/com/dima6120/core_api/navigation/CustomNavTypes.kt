package com.dima6120.core_api.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.dima6120.core_api.model.anime.AnimeBriefDetailsModel
import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.core_api.model.mylist.MyListStatusModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

object CustomNavTypes {

    val AnimeIdType = object : NavType<AnimeId>(isNullableAllowed = false) {

        override fun get(bundle: Bundle, key: String): AnimeId? =
            bundle.getString(key)?.let(Json::decodeFromString)

        override fun parseValue(value: String): AnimeId = Json.decodeFromString(Uri.decode(value))

        override fun serializeAsValue(value: AnimeId): String = Uri.encode(Json.encodeToString(value))

        override fun put(bundle: Bundle, key: String, value: AnimeId) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }

    val MyListStatusModelType = object : NavType<MyListStatusModel>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): MyListStatusModel? =
            bundle.getString(key)?.let(Json::decodeFromString)

        override fun parseValue(value: String): MyListStatusModel =
            Json.decodeFromString(Uri.decode(value))

        override fun serializeAsValue(value: MyListStatusModel): String =
            Uri.encode(Json.encodeToString(value))

        override fun put(bundle: Bundle, key: String, value: MyListStatusModel) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }

    val AnimeBriefDetailsModelType = object : NavType<AnimeBriefDetailsModel>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): AnimeBriefDetailsModel? =
            bundle.getString(key)?.let(Json::decodeFromString)

        override fun parseValue(value: String): AnimeBriefDetailsModel =
            Json.decodeFromString(Uri.decode(value))

        override fun serializeAsValue(value: AnimeBriefDetailsModel): String =
            Uri.encode(Json.encodeToString(value))

        override fun put(bundle: Bundle, key: String, value: AnimeBriefDetailsModel) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }

    val AnimeIdTypeEntry = typeOf<AnimeId>() to AnimeIdType
    val MyListStatusModelTypeEntry = typeOf<MyListStatusModel>() to MyListStatusModelType
    val AnimeBriefDetailsModelTypeEntry = typeOf<AnimeBriefDetailsModel>() to AnimeBriefDetailsModelType
}