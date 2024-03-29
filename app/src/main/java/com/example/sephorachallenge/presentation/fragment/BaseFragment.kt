package com.example.sephorachallenge.presentation.fragment

import androidx.fragment.app.Fragment
import com.example.sephorachallenge.presentation.activity.MainActivity
import com.example.sephorachallenge.presentation.fragment.productdetail.ProductDetailsFragment

abstract class BaseFragment : Fragment() {
    open val fragmentTag: String?
        get() = tag

    protected fun mainActivity() = requireActivity() as MainActivity

    fun openProductDetail(productId: Int) {
        mainActivity().replaceFragment(ProductDetailsFragment.newInstance(productId), true)
    }
}