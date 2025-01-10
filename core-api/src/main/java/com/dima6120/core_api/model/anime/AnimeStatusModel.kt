package com.dima6120.core_api.model.anime

import com.dima6120.core_api.model.mylist.ListStatusModel

enum class AnimeStatusModel(val acceptableListStatusModels: Set<ListStatusModel>) {
    NOT_YET_AIRED(
        ListStatusModel.entries
            .filter { it != ListStatusModel.COMPLETED && it != ListStatusModel.WATCHING }
            .toSet()
    ),

    CURRENTLY_AIRING(
        ListStatusModel.entries
            .filter { it != ListStatusModel.COMPLETED }
            .toSet()
    ),

    FINISHED_AIRING(ListStatusModel.entries.toSet())

}