package com.example.sephorachallenge.presentation.activity

import android.os.Bundle
import com.example.sephorachallenge.R
import com.example.sephorachallenge.presentation.fragment.product.ProductsFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentContainer = R.id.main_framelayout
        replaceFragment(ProductsFragment.newInstance(), false)
    }
}