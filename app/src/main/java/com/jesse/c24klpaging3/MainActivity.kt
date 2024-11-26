package com.jesse.c24klpaging3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jesse.c24klpaging3.domain.CharacterModel
import com.jesse.c24klpaging3.presentaion.DetailsScreen
import com.jesse.c24klpaging3.presentaion.HomeScreen
import com.jesse.c24klpaging3.ui.theme.C24KLPaging3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            C24KLPaging3Theme {
             InitialScreen()
            }
        }
    }
}

@Composable
fun InitialScreen() {
    val navController = rememberNavController()
    var currentCharacter by remember { mutableStateOf<CharacterModel?>(null) }
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen{
            navController.navigate("details")
            currentCharacter = it
        } }
        composable("details") { DetailsScreen(item = currentCharacter ?: null) }
    }
}

