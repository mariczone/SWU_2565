package com.example.myapplication

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentUploadPhotoBinding
import com.example.myapplication.databinding.FragmentUploadPhotoPreviewBinding
import com.example.myapplication.databinding.TemplateUploadPhotoBinding

class UploadPhotoPreviewFragment : Fragment() {
    private lateinit var templateUploadPhotoBinding: TemplateUploadPhotoBinding
    private lateinit var fragmentUploadPhotoPreviewBinding: FragmentUploadPhotoPreviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        templateUploadPhotoBinding = TemplateUploadPhotoBinding.inflate(layoutInflater)
        fragmentUploadPhotoPreviewBinding = FragmentUploadPhotoPreviewBinding.inflate(layoutInflater)
        return templateUploadPhotoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        templateUploadPhotoBinding.centerLayout.apply {
            addView(fragmentUploadPhotoPreviewBinding.root)
        }
        fragmentUploadPhotoPreviewBinding.previewImage.setImageBitmap(previewImage)
        templateUploadPhotoBinding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        var previewImage: Bitmap? = null
    }
}