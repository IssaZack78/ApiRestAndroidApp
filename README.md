# ApiRestAndroid
Construir aplicacion en android que consuma el API Rest

<img width="1920" height="986" alt="App con AppiRest" src="https://github.com/user-attachments/assets/fd0b4e08-e5f3-41b5-8d12-9afa0b565a23" />
<img width="1920" height="983" alt="App con AppiRest1" src="https://github.com/user-attachments/assets/3ad9083b-072f-42ee-bb33-e3e2a1e27281" />


```package com.example.apirestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)

        RetrofitClient.instance.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body() ?: emptyList()
                    val builder = StringBuilder()
                    for (user in users) {
                        builder.append("${user.id} - ${user.name}\n")
                    }
                    tvResult.text = builder.toString()
                } else {
                    tvResult.text = "Error en respuesta: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                tvResult.text = "Error de conexión: ${t.message}"
                Log.e("API", "Fallo en conexión: ${t.message}")
            }
```
``` 
ApiService
package com.example.apirestapp

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<User>>

    @GET("colors")
    suspend fun getColors(): List<Color>

}
        })
    }
}
```
```
AndroidManifest
<?xml version="1.0" encoding="utf-8"?>

<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <!-- Permission para acceder a Internet -->
    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.ApiRestApp">
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:label="@string/app_name"
            android:theme="@style/Theme.ApiRestApp"
            tools:ignore="RedundantLabel">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```
```
Type
package com.example.apirestapp

/**
 * Modelo Type para consumir del API
 */
data class Type(
    val id: Int,
    val name: String,
    val description: String
)
```
```
package com.example.apirestapp

import android.graphics.Color as AndroidColor

/**
 * Theme de la app: define colores globales y utilidades para UI.
 */
object Theme {

    // Colores predefinidos de la app (puedes agregar más)
    val primaryColor = AndroidColor.parseColor("#6200EE")
    val primaryDarkColor = AndroidColor.parseColor("#3700B3")
    val secondaryColor = AndroidColor.parseColor("#03DAC5")
    val backgroundColor = AndroidColor.parseColor("#F6F6F6")
    val textColor = AndroidColor.parseColor("#000000")
    val textOnPrimary = AndroidColor.parseColor("#FFFFFF")

    /**
     * Convierte un color hexadecimal (de tu API) a int para usar en Views
     * @param hexColor Ejemplo: "#FF0000"
     */
    fun fromHex(hexColor: String): Int {
        return try {
            AndroidColor.parseColor(hexColor)
        } catch (e: IllegalArgumentException) {
            // Retorna negro si el hex es inválido
            AndroidColor.BLACK
        }
    }
}
```
```
Color
package com.example.apirestapp

data class Color(
    val id: Int,
    val name: String,
    val hex: String
)
```
```
RetrofitClient
package com.example.apirestapp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}
```
```
User
package com.example.apirestapp

data class User(
    val id: Int,
    val name: String,
    val email: String
)
```
