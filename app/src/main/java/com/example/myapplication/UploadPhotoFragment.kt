package com.example.myapplication

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentUploadPhotoBinding
import com.example.myapplication.databinding.TemplateUploadPhotoBinding
import java.io.FileNotFoundException
import java.io.InputStream


class UploadPhotoFragment : Fragment() {
    private lateinit var templateUploadPhotoBinding: TemplateUploadPhotoBinding
    private lateinit var fragmentUploadPhotoBinding: FragmentUploadPhotoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        templateUploadPhotoBinding = TemplateUploadPhotoBinding.inflate(layoutInflater)
        fragmentUploadPhotoBinding = FragmentUploadPhotoBinding.inflate(layoutInflater)
        return templateUploadPhotoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        templateUploadPhotoBinding.centerLayout.apply {
            addView(fragmentUploadPhotoBinding.root)
        }
        templateUploadPhotoBinding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        fragmentUploadPhotoBinding.galleryCard.setOnClickListener {
            openGallery()
        }
        templateUploadPhotoBinding.nextBtn.apply {
            setOnClickListener { findNavController().navigate(R.id.action_uploadPhotoFragment_to_uploadPhotoPreviewFragment) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2) {
            try {
                val imageUri = data?.data
                val imageStream: InputStream? =
                    activity?.contentResolver?.openInputStream(imageUri!!)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                UploadPhotoPreviewFragment.previewImage = selectedImage
                findNavController().navigate(R.id.action_uploadPhotoFragment_to_uploadPhotoPreviewFragment)
                Log.d("TAG", "onActivityResult: imageUri: $imageUri")
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    private fun openGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, 2)
    }
}