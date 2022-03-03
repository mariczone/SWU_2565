package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentUploadPhotoBinding

class UploadPhotoFragment : Fragment() {
    private lateinit var fragmentUploadPhotoBinding: FragmentUploadPhotoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentUploadPhotoBinding = FragmentUploadPhotoBinding.inflate(layoutInflater)
        return fragmentUploadPhotoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}