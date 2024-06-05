package com.duoc.tablehub

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.duoc.tablehub.models.Usuario


@Composable
fun SignUp(navController: NavController){
    var nickname by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var mail by remember { mutableStateOf("") }
    var pswd by remember { mutableStateOf("") }
    var confirm_pswd by remember { mutableStateOf("") }
    val contexto = LocalContext.current
    val viewModel : MainViewModel = viewModel()
    val userCreationResult by viewModel.userCreationResult.collectAsState()

    LaunchedEffect(userCreationResult) {
        when (val result = userCreationResult) {
            is MainViewModel.UserCreationResult.Success -> {
                val respuesta = result.data as String

                if (respuesta == "OK"){
                    println("WAR: LOGIN OK")
                    navController.navigate("inicioLogin")
                } else {
                    Toast.makeText(
                        contexto, "Error: $respuesta", Toast.LENGTH_SHORT
                    ).show()
                }
            }
            is MainViewModel.UserCreationResult.Error -> {
                Toast.makeText(
                    contexto, "Error: ${result.message}", Toast.LENGTH_SHORT
                ).show()
            }
            else -> Unit
        }
    }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(Color.White, Color.Black)
                )
            )
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ){
            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "icono",
                modifier = Modifier
                    .padding(5.dp)
                    .size(200.dp)
            )
            Text("Nuevo Usuario", fontSize = 30.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ){
                Column {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = {nombre = it},
                        label = {Text("Nombre", color = Color.White)},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        modifier = Modifier.width(180.dp)
                    )
                }
                Column {
                    OutlinedTextField(
                        value = apellido,
                        onValueChange = {apellido = it},
                        label = {Text("Apellido", color = Color.White)},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        modifier = Modifier.width(180.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = nickname,
                onValueChange = {nickname = it},
                label = {Text("Nickname", color = Color.White)},
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = mail,
                onValueChange = {mail = it},
                label = {Text("Correo electrónico", color = Color.White)},
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = pswd,
                onValueChange = {pswd = it},
                label = {Text("Contraseña", color = Color.White)},
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = confirm_pswd,
                onValueChange = {confirm_pswd = it},
                label = {Text("Confirmar contraseña", color = Color.White)},
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                println("WAR: Nombre: $nombre")
                println("WAR: Apellido: $apellido")
                println("WAR: Mail: $mail")
                println("WAR: Pswd: $pswd")

                if (pswd == confirm_pswd){
                    println("WAR: USUARIO CREADO")
                    Toast.makeText(
                        contexto, "Usuario Creado", Toast.LENGTH_SHORT
                    ).show()
                    viewModel.CrearUsuario(mail, pswd, nombre, apellido)
                    val nuevoUsuario = Usuario(mail, nickname, nombre, apellido, null) // Crear objeto Usuario para Room
                    viewModel.insertarUsuarioEnRoom(nuevoUsuario)
                } else {
                    Toast.makeText(
                        contexto, "Error: Las contraseñas no coinciden", Toast.LENGTH_SHORT
                    ).show()
                }
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(color = Color.Red),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text("Crear cuenta")
            }
        }
    }
}
