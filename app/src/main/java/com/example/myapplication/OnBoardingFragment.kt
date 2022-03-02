package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.adapter.OnBoardingAdapter
import com.example.myapplication.adapter.OnBoardingViewItem
import com.example.myapplication.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment() {
    private val TAG = OnBoardingFragment::class.java.simpleName
    private lateinit var fragmentOnBoardingBinding: FragmentOnBoardingBinding
    private lateinit var indicators: List<ImageView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentOnBoardingBinding = FragmentOnBoardingBinding.inflate(layoutInflater)
        return fragmentOnBoardingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        indicators = listOf(
            fragmentOnBoardingBinding.indicator1,
            fragmentOnBoardingBinding.indicator2,
            fragmentOnBoardingBinding.indicator3
        )
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
        fragmentOnBoardingBinding.btn.setOnClickListener {
            if (currentPosition == indicators.size - 1) findNavController().navigate(R.id.action_onBoardingFragment_to_signInFragment)
        }
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
}