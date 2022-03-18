package com.example.myapplication

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentSetLocationBinding
import com.example.myapplication.databinding.TemplateUploadPhotoBinding
import com.google.android.gms.location.*

private val TAG = SetLocationFragment::class.java.simpleName

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

    private fun setupLocationProvider() {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        val locationRequest =
            LocationRequest().setInterval(6000L).setFastestInterval(1000L).setPriority(
                LocationRequest.PRIORITY_HIGH_ACCURACY
            )

        val locationTask =
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        locationTask.addOnFailureListener {
            Log.e(TAG, "Failure: ${it.message}")
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(currentLocation: LocationResult) {
            super.onLocationResult(currentLocation)
            Log.d(TAG, "onLocationResult: currentLocation $currentLocation")
        }
    }
}