package com.duoc.tablehub

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duoc.tablehub.models.Login
import com.duoc.tablehub.models.NuevoUsuario
import com.duoc.tablehub.utils.RetrofitInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val isLoading = mutableStateOf(false)
    private val _userCreationResult = MutableStateFlow<UserCreationResult?>(null)
    val userCreationResult =_userCreationResult.asStateFlow()

    fun Ingresar(mail: String, pass: String) {
        val usuario : Login
        usuario = Login(mail, pass)

        viewModelScope.launch {
            isLoading.value = true
            val response = RetrofitInstance.api.usuarioLogin(usuario)

            if (response.isSuccessful) {
                val respuesta = response.body()?.get(0)?.RESPUESTA
                _userCreationResult.value = UserCreationResult.Success(respuesta)
                println("${UserCreationResult.Success(respuesta)}")
            } else {
                println("RESPUESTA ERROR")
                _userCreationResult.value = UserCreationResult.Error("Error al ingresar")
            }

            isLoading.value = false
            resetUserCreationResult()
        }
    }

    fun CrearUsuario(mail: String, pass: String, nombre: String, apellido: String) {
        val usuario : NuevoUsuario
        usuario = NuevoUsuario(mail, pass, nombre, apellido)

        viewModelScope.launch {
            isLoading.value = true
            val response = RetrofitInstance.api.usuarioAlmacenar(usuario)

            if (response.isSuccessful) {
                val respuesta = response.body()?.get(0)?.RESPUESTA
                _userCreationResult.value = UserCreationResult.Success(respuesta)
                println("${_userCreationResult.value}")
            } else {
                println("RESPUESTA ERROR")
                _userCreationResult.value = UserCreationResult.Error("Error al crear usuario")
            }

            isLoading.value = false
            resetUserCreationResult()
        }
    }

    fun resetUserCreationResult() {
        viewModelScope.launch {
            delay(1000)
            _userCreationResult.value = null
        }
    }

    sealed class UserCreationResult {
        data class Success(val data: Any?): UserCreationResult()
        data class Error(val message: String): UserCreationResult()
    }
}
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
        composable("signup"){ SignUp(navController = navController)}
    }
}

@Composable
fun InicioLogin(navController: NavController){
    var mail by remember { mutableStateOf("") }
    var pswd by remember { mutableStateOf("") }
    val contexto = LocalContext.current
    val viewModel: MainViewModel = viewModel()
    val userCreationResult by viewModel.userCreationResult.collectAsState()

    LaunchedEffect(userCreationResult) {
        when (val result = userCreationResult) {
            is MainViewModel.UserCreationResult.Success -> {
                val respuesta = result.data as String

                if (respuesta == "LOGIN OK"){
                    println("WAR: LOGIN OK")
                    Toast.makeText(
                        contexto, "Bienvenido $mail", Toast.LENGTH_LONG
                    ).show()
                    navController.navigate("home")
                } else {
                    Toast.makeText(
                        contexto, "Credenciales Inválidas", Toast.LENGTH_LONG
                    ).show()
                }
            }
            is MainViewModel.UserCreationResult.Error -> {
                Toast.makeText(
                    contexto, result.message, Toast.LENGTH_LONG
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
                    listOf(
                        Color.White,
                        Color.Black
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
                painter = painterResource(id = R.drawable.newlogo),
                contentDescription = "logo",
                modifier = Modifier.size(300.dp)
            )
            Spacer(modifier = Modifier.height(72.dp))
            OutlinedTextField(
                value = mail,
                onValueChange = {mail = it},
                label = {Text("Correo Electrónico", color = Color.White)},
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = pswd,
                onValueChange = {pswd = it},
                label = {Text("Contraseña", color = Color.White)},
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            )
            Spacer(modifier = Modifier.height(18.dp))
            Button(onClick = {
                println("WAR: MAIL: $mail")
                println("WAR: PASS: $pswd")

                viewModel.Ingresar(mail, pswd)
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
                Text("Ingresar")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Crear nueva cuenta",
                color = Color.Blue,
                modifier = Modifier.clickable { navController.navigate("signup") }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("¿Olvidó su contraseña?", color = Color.Blue)
        }
    }
}

