package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var fragmentSignInBinding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentSignInBinding = FragmentSignInBinding.inflate(layoutInflater)
        return fragmentSignInBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentSignInBinding.signInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_uploadPhotoFragment)
        }
    }
}