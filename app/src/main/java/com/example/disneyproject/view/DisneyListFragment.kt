package com.example.disneyproject.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.disneyproject.R
import com.example.disneyproject.data.remote.DisneyApi
import com.example.disneyproject.data.remote.model.Disney
import com.example.disneyproject.data.remote.model.DisneyResponse
import com.example.disneyproject.databinding.FragmentDisneyListBinding
import com.example.disneyproject.util.Constants
import com.example.disneyproject.view.adapters.DisneyAdapter
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
        // Inflate the layout for this fragment
        _binding = FragmentDisneyListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val disneyApi = retrofit.create(DisneyApi::class.java)

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
                            // Aquí puedes definir la acción al hacer clic en un personaje
                        }
                    }
                } else {
                    Log.e(Constants.LOGTAG, "Error en la respuesta: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<DisneyResponse>, t: Throwable) {
                binding.pbLoading.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), "No hay conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}