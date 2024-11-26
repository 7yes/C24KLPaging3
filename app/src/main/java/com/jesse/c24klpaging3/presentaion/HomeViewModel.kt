package com.jesse.c24klpaging3.presentaion

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.jesse.c24klpaging3.data.Homerepository
import com.jesse.c24klpaging3.domain.CharacterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repo: Homerepository): ViewModel() {
    val characters : Flow<PagingData<CharacterModel>> = repo.getCharacters()
}