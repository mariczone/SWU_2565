package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentSetLocationBinding

class SetLocationFragment : Fragment() {

    private lateinit var fragmentSetLocationBinding: FragmentSetLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentSetLocationBinding = FragmentSetLocationBinding.inflate(layoutInflater)
        return fragmentSetLocationBinding.root
    }
}