package com.example.sephorachallenge.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sephorachallenge.R

class ProductDetailFragment : BaseFragment() {

    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    companion object {

        private const val ID = "productId"

        @JvmStatic
        fun newInstance(id: Int) =
            ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID, id)
                }
            }
    }
}