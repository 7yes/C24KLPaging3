package com.jesse.c24klpaging3.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jesse.c24klpaging3.domain.CharacterModel
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(private val homeApi: HomeApi) :
    PagingSource<Int, CharacterModel>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterModel>): Int? {
       return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterModel> {
       return try {
           val nextPage = params.key ?: 1
           val response = homeApi.getCharacters(nextPage)
           val data: List<CharacterModel>? = response.body()?.results?.map {
               CharacterModel(
                   id = it.id,
                   name = it.name,
                   image = it.image,
                   status = it.status)
           }
           val prevKey: Int? = if (nextPage > 0) nextPage - 1 else null
           val nextKey: Int? = if (data?.isNotEmpty() == true) nextPage + 1 else null
           LoadResult.Page(data = data!!, prevKey = prevKey, nextKey = nextKey)
       }
       catch (e: Exception) {
           LoadResult.Error(e)
       }
    }
}