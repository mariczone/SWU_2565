package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentSetLocationBinding
import com.example.myapplication.databinding.TemplateUploadPhotoBinding

class SetLocationFragment : Fragment() {

    private lateinit var fragmentSetLocationBinding: FragmentSetLocationBinding
    private lateinit var templateUploadPhotoBinding: TemplateUploadPhotoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentSetLocationBinding = FragmentSetLocationBinding.inflate(layoutInflater)
        templateUploadPhotoBinding = TemplateUploadPhotoBinding.inflate(layoutInflater)
        return templateUploadPhotoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        templateUploadPhotoBinding.centerLayout.apply {
            addView(fragmentSetLocationBinding.root)
        }
        templateUploadPhotoBinding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}