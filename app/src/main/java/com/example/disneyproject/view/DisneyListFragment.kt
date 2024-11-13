package com.example.disneyproject.view

import java.util.concurrent.TimeUnit
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.disneyproject.R
import com.example.disneyproject.data.remote.DisneyApi
import com.example.disneyproject.data.remote.model.Disney
import com.example.disneyproject.data.remote.model.DisneyResponse
import com.example.disneyproject.databinding.FragmentDisneyListBinding
import com.example.disneyproject.util.Constants
import com.example.disneyproject.view.adapters.DisneyAdapter
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DisneyListFragment : Fragment() {

    private var _binding: FragmentDisneyListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDisneyListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //intento de hacer que funcione mejor el reintentar
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val disneyApi = retrofit.create(DisneyApi::class.java)

        fun fetchData() {
            val call = disneyApi.getCharacters()
            call.enqueue(object: Callback<DisneyResponse> {
                override fun onResponse(call: Call<DisneyResponse>, response: Response<DisneyResponse>) {
                    binding.pbLoading.visibility = View.INVISIBLE
                    if (response.isSuccessful) {
                        val disneyResponse = response.body()
                        val disneyList = disneyResponse?.data ?: mutableListOf()  // Lista de personajes

                        binding.apply {
                            rvDisney.layoutManager = LinearLayoutManager(requireActivity())
                            rvDisney.adapter = DisneyAdapter(disneyList) { disney ->
                                disney.id?.let { id ->
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container, DisneyDetailFragment.newInstance(id))
                                        .addToBackStack(null)
                                        .commit()
                                }
                            }
                        }
                    } else {
                        Log.e(Constants.LOGTAG, "Error en la respuesta: ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<DisneyResponse>, t: Throwable) {
                    binding.pbLoading.visibility = View.INVISIBLE

                    AlertDialog.Builder(requireActivity())
                        .setTitle("Error de Conexión")
                        .setMessage("No se pudo conectar al servidor. ¿Desea intentar de nuevo?")
                        .setPositiveButton("Reintentar") { _, _ ->

                            binding.pbLoading.visibility = View.VISIBLE
                            fetchData()
                        }
                        .setNegativeButton("Cancelar") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            })
        }


        binding.pbLoading.visibility = View.VISIBLE
        fetchData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}