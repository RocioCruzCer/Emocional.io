package mx.edu.dsi_code.notasmvvm.api

import mx.edu.dsi_code.notasmvvm.model.Emocion
import mx.edu.dsi_code.notasmvvm.model.EmocionesResponse
import mx.edu.dsi_code.notasmvvm.model.EmotionFrequencyResponse
import mx.edu.dsi_code.notasmvvm.model.Login
import mx.edu.dsi_code.notasmvvm.model.Nota
import mx.edu.dsi_code.notasmvvm.model.NotasResponse
import mx.edu.dsi_code.notasmvvm.model.Note
import mx.edu.dsi_code.notasmvvm.model.User
import mx.edu.dsi_code.notasmvvm.model.UserResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("Api/Emociones/getAll")
    suspend fun getEmociones(): Response<List<Emocion>>

    @POST("Api/Usuarios/add")
    suspend fun registerUser(@Body user: User): Response<UserResponse> // Cambié Call por Response para ser consistente

    @POST("Api/Usuarios/ingresar")
    suspend fun loginUser(@Body login: Login): Response<UserResponse>

    @GET("Api/Emociones/getAll")
    suspend fun getAllEmotions(): Response<EmocionesResponse>

    @GET("Api/Notas/notasPorFecha")
    suspend fun obtenerNotasPorFecha(
        @Query("datos.idUsuario") idUsuario: Int,
        @Query("datos.fechaBuscada") fecha: String
    ): Response<NotasResponse>

    @GET("Api/Notas/notasPorUsuario/{userId}")
    suspend fun getNotasPorUsuario(@Path("userId") userId: Int): Response<NotasResponse>

    // Función para eliminar una nota por su ID
    @DELETE("Api/Notas/eliminarNotaPorID/{id}")
    suspend fun eliminarNotaPorId(
        @Path("id") id: Int
    ): Response<NotasResponse>

    // Llamada para obtener las frecuencias semanales de emociones
    @GET("/Api/Emociones/FrecuenciasSemanales/{idUsuario}")
    suspend fun getFrecuenciasEmocionales(@Path("idUsuario") userId: Int): Response<EmotionFrequencyResponse>

    @POST("Api/Notas/agregarNota")
    suspend fun addNote(@Body note: Nota): Response<Void>

    object RetrofitClient {
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://emocional.bsite.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService: ApiService = retrofit.create(ApiService::class.java)
    }
}

