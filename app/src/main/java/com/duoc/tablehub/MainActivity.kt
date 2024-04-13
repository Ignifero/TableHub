package com.duoc.tablehub

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duoc.tablehub.ui.theme.TableHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiAplicacion()
        }
    }
}

@Composable
fun MiAplicacion(){
    val navController =rememberNavController()
    NavHost(navController = navController, startDestination = "inicioLogin") {
        composable("inicioLogin"){ InicioLogin(navController = navController)}
        composable("home"){ Home(navController = navController)}
    }
}

@Composable
fun InicioLogin(navController: NavController){
    var mail by remember { mutableStateOf("") }
    var pswd by remember { mutableStateOf("") }
    val contexto = LocalContext.current

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        androidx.compose.ui.graphics.Color.White,
                        androidx.compose.ui.graphics.Color.Black
                    )
                )
            )
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier.size(300.dp)
            )
            Spacer(modifier = Modifier.height(72.dp))
            OutlinedTextField(
                value = mail,
                onValueChange = {mail = it},
                label = {Text("Correo Electrónico", color = androidx.compose.ui.graphics.Color.White)},
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = pswd,
                onValueChange = {pswd = it},
                label = {Text("Contraseña", color = androidx.compose.ui.graphics.Color.White)},
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            )
            Spacer(modifier = Modifier.height(18.dp))
            Button(onClick = {
                println("WAR: MAIL: $mail")
                println("WAR: PASS: $pswd")

                if (mail == "warhammer" && pswd == "40000"){
                    navController.navigate("home")
                } else {
                    Toast.makeText(
                        contexto,
                        "Credenciales Inválidas",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(color = androidx.compose.ui.graphics.Color.Red),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.ui.graphics.Color.Red,
                    contentColor = androidx.compose.ui.graphics.Color.White
                )
            ) {
                Text("Ingresar")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Crear nueva cuenta", color = androidx.compose.ui.graphics.Color.Blue)
            Spacer(modifier = Modifier.height(4.dp))
            Text("¿Olvidó su contraseña?", color = androidx.compose.ui.graphics.Color.Blue)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TableHubTheme {
        Greeting("Android")
    }
}