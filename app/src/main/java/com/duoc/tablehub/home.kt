package com.duoc.tablehub

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(navController: NavController){
    Scaffold (
        bottomBar = { CustomBottomBar(navController = navController) },
        content = { innerPadding ->
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.White,
                                Color.Black
                            )
                        )
                    )
            ){
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.newlogo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(200.dp)
                    )
                    Row (modifier = Modifier.padding(5.dp)){
                        Image(painter = painterResource(id = R.drawable.armageddon), contentDescription = "",
                            modifier = Modifier
                                .padding(5.dp)
                                .size(150.dp))
                        Column {
                            Image(painter = painterResource(id = R.drawable.storm), contentDescription = "",
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(150.dp))
                        }
                    }
                    Row (modifier = Modifier.padding(5.dp)){
                        Image(painter = painterResource(id = R.drawable.kill), contentDescription = "",
                            modifier = Modifier
                                .padding(5.dp)
                                .size(150.dp))
                        Column {
                            Image(painter = painterResource(id = R.drawable.warcry), contentDescription = "",
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(150.dp))
                        }
                    }
                    Row (modifier = Modifier.padding(10.dp)){
                        Image(painter = painterResource(id = R.drawable.inquisitor), contentDescription = "",
                            modifier = Modifier
                                .padding(5.dp)
                                .size(150.dp))
                        Column {
                            Image(painter = painterResource(id = R.drawable.fantasy), contentDescription = "",
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(150.dp))
                        }
                    }
                }
            }
        }
    )

}

@Composable
fun CustomBottomBar(navController: NavController){
    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        color = Color.LightGray
    ){

    }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        IconButton(
            onClick = {navController.navigateUp()},
            colors = IconButtonDefaults.iconButtonColors(containerColor = Color.DarkGray)

        ) {
            Icon(Icons.Filled.Home, contentDescription = null, tint = Color.White)
        }
        IconButton(
            onClick = {navController.navigateUp()},
            colors = IconButtonDefaults.iconButtonColors(containerColor = Color.DarkGray)
        ) {
            Icon(Icons.Filled.Favorite, contentDescription = null, tint = Color.White)
        }
        IconButton(
            onClick = {navController.navigateUp()},
            colors = IconButtonDefaults.iconButtonColors(containerColor = Color.DarkGray)
        ) {
            Icon(Icons.Filled.AccountCircle, contentDescription = null, tint = Color.White)
        }
    }
}
