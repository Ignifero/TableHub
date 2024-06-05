package com.duoc.tablehub.services

import com.duoc.tablehub.models.Login
import com.duoc.tablehub.models.NuevoUsuario
import com.duoc.tablehub.models.Producto
import com.duoc.tablehub.models.Respuesta
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("route/usuario_duoc_almacenar")
    suspend fun usuarioAlmacenar(@Body nuevoUsuario: NuevoUsuario):Response<List<Respuesta>>

    @POST("route/usuario_duoc_login")
    suspend fun usuarioLogin(@Body login: Login): Response<List<Respuesta>>

    @POST("route/producto_duoc_almacenar")
    suspend fun productoAlmacenar(@Body producto: Producto):Response<List<Respuesta>>

    @GET("route/producto_duoc_obtener_x_mail")
    suspend fun productoObtener(@Query("mail") correo: String): Response<List<Any>>

}