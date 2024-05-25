package com.duoc.tablehub

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun SeleccionarPlan(navController: NavController, mail: String) {
    val contexto = LocalContext.current

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
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Selecciona tu plan", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            CartaProducto(codigo = "40000#$mail", nombre = "Plus", precio = 100.0,
                descripcion = "Con este plan colaboras un poco mejor con el equipo de desarrollo.",
                mail = mail, navController = navController)
            CartaProducto(codigo = "40001#$mail", nombre = "Pro", precio = 500.0,
                descripcion = "¡Con este plan serás un usuario pro! ¡Qué pro es eso!",
                mail = mail, navController = navController)
            CartaProducto(codigo = "40002#$mail", nombre = "Premium", precio = 1000.0,
                descripcion = "Con este plan serás el mejor usuario " +
                        "de la app. Mucho mejor que el pro.",
                mail = mail, navController = navController)
        }
        Toast.makeText(contexto, "Bienvenido $mail", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun CartaProducto(codigo: String, nombre: String, precio: Double, descripcion: String, mail: String,
                  navController: NavController) {
    val contexto = LocalContext.current
    val viewModel : MainViewModel = viewModel()
    val userCreationResult by viewModel.userCreationResult.collectAsState()

    LaunchedEffect(userCreationResult) {
        when (val result = userCreationResult) {
            is MainViewModel.UserCreationResult.Success -> {
                val respuesta = result.data as String

                if (respuesta == "OK") {
                    println("Producto creado")
                    navController.navigate("home/${mail}")
                } else {
                    Toast.makeText(contexto, "Error: $respuesta", Toast.LENGTH_SHORT).show()
                }
            }
            is MainViewModel.UserCreationResult.Error -> {
                Toast.makeText(contexto, "Error: ${result.message}", Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = nombre, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = "$precio")
            Text(text = descripcion, maxLines = 3, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    println("WAR: Codigo: $codigo")
                    println("WAR: Nombre: $nombre")
                    println("WAR: Descripcion: $descripcion")
                    println("WAR: Precio: $precio")
                    println("WAR: Correo: $mail")

                    viewModel.CrearPlan(codigo, nombre, descripcion, precio, mail)
                },
                modifier = Modifier.fillMaxWidth()
                ) {
                Text(text = "Suscribirse")
            }
        }
    }
}

