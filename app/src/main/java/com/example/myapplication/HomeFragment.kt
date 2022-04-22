package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.adapter.CategoryAdapter
import com.example.myapplication.adapter.CategoryOnlineViewItem
import com.example.myapplication.adapter.CategoryViewItem
import com.example.myapplication.adapter.PromotionAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.repository.FirestoreRepository
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var promotionPager: ViewPager2
    private lateinit var categoryRecycleView: RecyclerView
    private lateinit var categoryImageList: MutableList<CategoryViewItem>
    private var categoryImageOnlineList: MutableList<CategoryOnlineViewItem> = mutableListOf<CategoryOnlineViewItem>()
    private var firebaseRepository = FirestoreRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)
        return FragmentHomeBinding.inflate(layoutInflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        // get user login email
        mAuth.currentUser?.email
        // get user login uid
        mAuth.currentUser?.uid

        val promoImageList = mutableListOf<Int>()
        promoImageList.add(R.drawable.promotion1)
        promoImageList.add(R.drawable.promotion2)
        promoImageList.add(R.drawable.promotion3)
        promoImageList.add(R.drawable.promotion4)
        promoImageList.add(R.drawable.promotion5)
        promoImageList.add(R.drawable.promotion6)
        promoImageList.add(R.drawable.promotion7)
        promotionPager = view.findViewById<ViewPager2>(R.id.promotionViewPager);
        promotionPager.adapter = PromotionAdapter(promoImageList);

        categoryRecycleView = view.findViewById<RecyclerView>(R.id.recycleView);
        categoryImageList = mutableListOf<CategoryViewItem>()
//        categoryImageList.add(CategoryViewItem("Dinner", R.drawable.ic_baseline_free_breakfast_24))
//        categoryImageList.add(CategoryViewItem("Lunch", R.drawable.ic_baseline_dinner_dining_24))
//        categoryImageList.add(CategoryViewItem("Breakfast", R.drawable.ic_baseline_fastfood_24))
//        categoryImageList.add(CategoryViewItem("Cafe", R.drawable.ic_baseline_free_breakfast_24))
//        categoryImageList.add(CategoryViewItem("HealtyFood", R.drawable.ic_baseline_dinner_dining_24))
        fetchCategoriesDataFromDatabase()
//        categoryRecycleView.adapter = CategoryAdapter(categoryImageList)

        var viewAllCategoryText = view.findViewById<TextView>(R.id.tv_view_cate);
        viewAllCategoryText.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_cv, CategoryViewFragment())
                    .addToBackStack("HomeFragment")
                    .commit()
            }
        }

        //search event
        var etSearchInput = view.findViewById<EditText>(R.id.etSearchInput);
        etSearchInput.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (etSearchInput.text.length > 0) {
                    var filteredItem = categoryImageOnlineList.filter {
                        it.Type.toLowerCase().contains(etSearchInput.text.toString().toLowerCase())
                    }
                    categoryRecycleView.adapter = CategoryAdapter(filteredItem)
                } else {
                    categoryRecycleView.adapter = CategoryAdapter(categoryImageOnlineList)
                }
            }
        })
    }

    private fun fetchCategoriesDataFromDatabase() {
        firebaseRepository.getSavedCategories().get().addOnSuccessListener { documents ->
            categoryImageOnlineList.clear()
            for (document in documents) {
                categoryImageOnlineList.add(document.toObject(CategoryOnlineViewItem::class.java))
            }
            categoryRecycleView.adapter = CategoryAdapter(categoryImageOnlineList)
        }
    }

}