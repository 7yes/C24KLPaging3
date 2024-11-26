package com.jesse.c24klpaging3.presentaion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.jesse.c24klpaging3.R
import com.jesse.c24klpaging3.domain.CharacterModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel(), onClickLis:(CharacterModel)->Unit) {

    val characters = homeViewModel.characters.collectAsLazyPagingItems()
    //CharacterList(characters)
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

    when {
        //Carga inicial
        characters.loadState.refresh is LoadState.Loading && characters.itemCount == 0 -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp), color = Color.White
                )
            }
        }

        //Estado vacio
        characters.loadState.refresh is LoadState.NotLoading && characters.itemCount == 0 -> {
            Text(text = "Todavía no hay personajes")
        }

        characters.loadState.hasError -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Red), contentAlignment = Alignment.Center
            ) {
                Text(text = "Ha ocurrido un error")
            }
        }

        else -> {
            CharactersList(characters,onClickLis)

            if (characters.loadState.append is LoadState.Loading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp), color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun CharactersList(
    characters: LazyPagingItems<CharacterModel>,
    onClickLis: (CharacterModel) -> Unit
) {
    LazyColumn {
        items(characters.itemCount) {
            characters[it]?.let { currentItem ->
                SmallItem(currentItem,onClickLis)
            }
        }
    }
}

@Composable
fun SmallItem(
    currentItem: CharacterModel,
    onClickLis: (CharacterModel) -> Unit
) {
    val color = if (currentItem.status == "Alive") Color.Green else Color.Red
   // val context = LocalContext.current
    //Simpple option
//    Box(modifier = Modifier
//        .fillMaxWidth()
//        .padding(10.dp)
//        .height(200.dp)
//        .background(Color.Black),
//        contentAlignment = Alignment.Center
//        ) {
//        Text(text = currentItem.name, color = Color.White)
//    }

//complete option
    Box(
        modifier = Modifier
            .padding(24.dp)
            .clip(RoundedCornerShape(24))
            .border(2.dp, Color.Green, shape = RoundedCornerShape(0, 24, 0, 24))
            .fillMaxWidth()
            .clickable {
                onClickLis(currentItem)
            }
            .height(250.dp), contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = currentItem.image,
            contentDescription = "character image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(alpha = 0f),
                            Color.Black.copy(alpha = 0.6f),
                            Color.Black.copy(alpha = 1f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = currentItem.name, color = Color.White, fontSize = 18.sp)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(74.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(text = currentItem.status, color = color, fontSize = 18.sp)
        }
    }
}
