package com.app.smartshamba.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.app.smartshamba.ItemDataModels
import com.app.smartshamba.R
import com.app.smartshamba.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)


        return binding.root
    }

}