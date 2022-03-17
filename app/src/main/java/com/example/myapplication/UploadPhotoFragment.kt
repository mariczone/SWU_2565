package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentUploadPhotoBinding
import com.example.myapplication.databinding.TemplateUploadPhotoBinding

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
        fragmentUploadPhotoBinding.cameraCard.setOnClickListener {
            openBackCamera()
        }
        templateUploadPhotoBinding.nextBtn.apply {
            setOnClickListener { findNavController().navigate(R.id.action_uploadPhotoFragment_to_uploadPhotoPreviewFragment) }
        }
    }

    private fun openBackCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            try {
                val photo = data!!.extras!!.get("data")
                UploadPhotoPreviewFragment.previewImage = photo as Bitmap
                Log.d("TAG", "onActivityResult: photo $photo")
                findNavController().navigate(R.id.action_uploadPhotoFragment_to_uploadPhotoPreviewFragment)
            } catch (e: Exception) {
                Log.e("TAG", "onActivityResult: e -> $e")
            }
        }
    }
}