package com.example.myapplication

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.adapter.OnBoardingAdapter
import com.example.myapplication.adapter.OnBoardingViewItem
import com.example.myapplication.databinding.FragmentOnBoardingBinding

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private var isSplashFinished = true

    //    private val fragmentOnBoardingBinding by lazy {
//        FragmentOnBoardingBinding.inflate(layoutInflater)
//    }
    private lateinit var fragmentOnBoardingBinding: FragmentOnBoardingBinding
    private lateinit var indicators: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentOnBoardingBinding = FragmentOnBoardingBinding.inflate(layoutInflater)
        indicators = listOf(
            fragmentOnBoardingBinding.indicator1,
            fragmentOnBoardingBinding.indicator2,
            fragmentOnBoardingBinding.indicator3
        )
        setContentView(fragmentOnBoardingBinding.root)
        fragmentOnBoardingBinding.viewPager.apply {
            adapter = OnBoardingAdapter(
                listOf(
                    OnBoardingViewItem(
                        title = "Find your \nComfort Food here",
                        description = "Here You Can find a chef or dish for every taste and color. Enjoy!",
                        imageId = R.drawable.on_boarding_first_bg
                    ),
                    OnBoardingViewItem(
                        title = "Food Ninja is Where \nYour Comfort Food Lives",
                        description = "Enjoy a fast and smooth food delivery at your doorstep",
                        imageId = R.drawable.on_boarding_second_bg
                    ),
                    OnBoardingViewItem(
                        title = "Food Ninja is Where \nYour Comfort Food Lives",
                        description = "Enjoy a fast and smooth food delivery at your doorstep",
                        imageId = R.drawable.on_boarding_first_bg
                    )
                )
            )
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setCurrentIndicator(position)
                    setBottomBtnState(position)
                }
            })
        }
    }

    private fun setBottomBtnState(currentPosition: Int) {
        fragmentOnBoardingBinding.btn.visibility =
            if (currentPosition == indicators.size - 1) View.VISIBLE else View.INVISIBLE
    }

    private fun setCurrentIndicator(currentPosition: Int) {
        indicators.forEachIndexed { index, iv ->
            if (currentPosition == index) {
                iv.setImageResource(R.drawable.indicator_active)
            } else {
                iv.setImageResource(R.drawable.indicator_inactive)
            }
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