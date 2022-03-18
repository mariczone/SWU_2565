package com.example.myapplication

import android.app.Activity.RESULT_OK
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentUploadPhotoBinding
import com.example.myapplication.databinding.TemplateUploadPhotoBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class UploadPhotoFragment : Fragment() {
    private lateinit var templateUploadPhotoBinding: TemplateUploadPhotoBinding
    private lateinit var fragmentUploadPhotoBinding: FragmentUploadPhotoBinding
    private val REQUEST_CAMERA = 0
    private lateinit var uri: Uri

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
        templateUploadPhotoBinding.nextBtn.apply {
            setOnClickListener { findNavController().navigate(R.id.action_uploadPhotoFragment_to_uploadPhotoPreviewFragment) }
        }
        fragmentUploadPhotoBinding.cameraCard.setOnClickListener {
            openCamera()
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "IMG_$timeStamp.jpg"
        val contextWrapper = ContextWrapper(activity)
        val imageDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val file = File(imageDirectory, imageFileName)
        uri = FileProvider.getUriForFile(
            requireContext(),
            requireContext().applicationContext.packageName.toString() + ".provider",
            file
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(
            Intent.createChooser(
                intent, "Take a picture with"
            ), REQUEST_CAMERA
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                getBitMapFromCamera()
            }
        }
    }

    private fun getBitMapFromCamera() {
        val cr = requireActivity().contentResolver
        cr.notifyChange(uri, null)
        try {
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(cr, uri)
            UploadPhotoPreviewFragment.bitmap = bitmap
            findNavController().navigate(R.id.action_uploadPhotoFragment_to_uploadPhotoPreviewFragment)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}