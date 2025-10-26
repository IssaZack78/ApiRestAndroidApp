package com.example.apirestapp

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
        })
    }
}