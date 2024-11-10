package com.example.disneyproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disneyproject.R
import com.example.disneyproject.databinding.FragmentDisneyDetailBinding


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