package com.example.myapplication

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private var isSplashFinished = true


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_on_boarding)
        setupSplashScreenState()
        //SetUp splash screen for android 12
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setupAboveAndroid12SplashScreen()
        }
    }

    private fun setupSplashScreenState() {
        // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    Log.d(TAG, "onPreDraw: isSplashFinished : $isSplashFinished")
                    // Check if the initial data is ready.
                    return if (isSplashFinished) {
                        // The content is ready; start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content is not ready; suspend.
                        false
                    }
                }
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setupAboveAndroid12SplashScreen() {
//        val splashScreen = installSplashScreen()
//        splashScreen.setOnExitAnimationListener {
//
//        }
    }

}