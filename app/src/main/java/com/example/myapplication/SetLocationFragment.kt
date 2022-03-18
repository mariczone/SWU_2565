package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentSetLocationBinding
import com.example.myapplication.databinding.TemplateUploadPhotoBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

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
        fragmentSetLocationBinding.setLocationBtn.setOnClickListener {
            setupLocationProvider()
        }
    }

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                setupLocationProvider()
                Toast.makeText(requireContext(), "Granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Deny", Toast.LENGTH_LONG).show()
            }
        }

    private fun setupLocationProvider() {
        val locationTask =
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                return
            } else {
                val fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(requireContext())
                val locationRequest =
                    LocationRequest().setInterval(6000L).setFastestInterval(1000L).setPriority(
                        LocationRequest.PRIORITY_HIGH_ACCURACY
                    )
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }

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