package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentUploadPhotoBinding
import com.example.myapplication.databinding.TemplateUploadPhotoBinding

class UploadPhotoFragment : Fragment() {
    private lateinit var templateUploadPhotoBinding: TemplateUploadPhotoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        templateUploadPhotoBinding = TemplateUploadPhotoBinding.inflate(layoutInflater)

        return templateUploadPhotoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        templateUploadPhotoBinding.centerLayout.apply {
            addView(FragmentUploadPhotoBinding.inflate(layoutInflater).root)
        }
        templateUploadPhotoBinding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}