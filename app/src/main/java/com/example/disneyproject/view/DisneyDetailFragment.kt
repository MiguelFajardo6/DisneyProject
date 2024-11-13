package com.example.disneyproject.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.disneyproject.R
import com.example.disneyproject.data.remote.DisneyApi
import com.example.disneyproject.data.remote.model.DisneyDetail
import com.example.disneyproject.data.remote.model.DisneyDetailResponse
import com.example.disneyproject.databinding.FragmentDisneyDetailBinding
import com.example.disneyproject.util.Constants
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val ARG_ID = "id"


class DisneyDetailFragment : Fragment() {
    private var _binding: FragmentDisneyDetailBinding? = null
    private val binding get() = _binding!!
    private var id: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ARG_ID)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDisneyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val disneyApi = retrofit.create(DisneyApi::class.java)


        id?.let { characterId ->
            val fullUrl = "${Constants.BASE_URL}character/$characterId"


            Log.d(Constants.LOGTAG, "URL completa: $fullUrl")
            Toast.makeText(requireActivity(), "URL: $fullUrl", Toast.LENGTH_LONG).show()

            val call = disneyApi.getCharacterDetail(characterId)

            call.enqueue(object : Callback<DisneyDetailResponse> {
                override fun onResponse(call: Call<DisneyDetailResponse>, response: Response<DisneyDetailResponse>) {
                    binding.apply {
                        pbLoading.visibility = View.INVISIBLE
                        val disneyDetail = response.body()?.data
                        tvTitle.text = disneyDetail?.name

                        val filmsText = if (disneyDetail?.films.isNullOrEmpty()) {
                            "No hay películas disponibles"
                        } else {
                            disneyDetail?.films?.joinToString("\n") ?: "No hay películas disponibles"
                        }
                        tvLongDesc.text = "Películas:\n$filmsText"

                        Picasso.get()
                            .load(disneyDetail?.imageUrl)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .into(binding.ivImage)
                    }
                }

                override fun onFailure(call: Call<DisneyDetailResponse>, t: Throwable) {
                    binding.pbLoading.visibility = View.INVISIBLE
                    Toast.makeText(requireActivity(), "Error al cargar detalles", Toast.LENGTH_SHORT).show()
                }
            })
        } ?: run {

            Toast.makeText(requireActivity(), "ID de personaje no encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(id: String) =
            DisneyDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ID, id)

                }
            }
    }
}