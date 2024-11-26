package com.jesse.c24klpaging3.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jesse.c24klpaging3.domain.CharacterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Homerepository @Inject constructor(private val homeApi: HomeApi) {

    companion object {
        const val MAX_ITEMS_PER_PAGE = 10
        const val PREFETCH_DISTANCE = 3
    }

    fun getCharacters(): Flow<PagingData<CharacterModel>> {
        return Pager(config = PagingConfig(pageSize = MAX_ITEMS_PER_PAGE, prefetchDistance = PREFETCH_DISTANCE),
            pagingSourceFactory = { CharacterPagingSource(homeApi) }).flow
    }

}