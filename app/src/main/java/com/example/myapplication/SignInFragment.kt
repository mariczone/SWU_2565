package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentSignInBinding
import java.util.concurrent.Executor

class SignInFragment : Fragment() {
    private lateinit var fragmentSignInBinding: FragmentSignInBinding
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        executor = ContextCompat.getMainExecutor(requireContext())
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()
        biometricPrompt = BiometricPrompt(
            this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        requireContext(),
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        requireContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT
                    )
                        .show()
                    findNavController().navigate(R.id.action_signInFragment_to_mainFragment)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        requireContext(), "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentSignInBinding = FragmentSignInBinding.inflate(layoutInflater)
        return fragmentSignInBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isUserStoreInSharedPref()) {
            displayStoredUserData()
        }
        fragmentSignInBinding.signInBtn.setOnClickListener {
            if (isUserStoreInSharedPref()) {
                biometricPrompt.authenticate(promptInfo)
            } else {
                if (verifyUser()) {
                    saveUserDataToPref()
                    findNavController().navigate(R.id.action_signInFragment_to_mainFragment)
                } else {
                    Toast.makeText(requireContext(), "Wrong password or email", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun isUserStoreInSharedPref(): Boolean {
        val preference = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val storedEmail = preference.getString(User.emailKey, null)
        val storedPassword = preference.getString(User.passwordKey, null)
        return !storedEmail.isNullOrBlank() && !storedPassword.isNullOrBlank()
    }

    private fun verifyUser(): Boolean {
        return fragmentSignInBinding.email.text.toString() == User.email && fragmentSignInBinding.password.text.toString() == User.password
    }

    private fun saveUserDataToPref() {
        val preference = requireActivity().getPreferences(Context.MODE_PRIVATE)
        preference.edit {
            putString(User.emailKey, fragmentSignInBinding.email.text.toString())
            putString(User.passwordKey, fragmentSignInBinding.password.text.toString())
        }
    }

    private fun displayStoredUserData() {
        val preference = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val storedEmail = preference.getString(User.emailKey, null)
        val storedPassword = preference.getString(User.passwordKey, null)
        fragmentSignInBinding.apply {
            email.setText(storedEmail)
            password.setText(storedPassword)
        }
    }

    private object User {
        val email = "name@swu.ac.th"
        val password = "1234"
        val emailKey = "email"
        val passwordKey = "password"
    }
}