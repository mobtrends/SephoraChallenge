package com.example.sephorachallenge.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sephorachallenge.app.SephoraChallengeApplication
import com.example.sephorachallenge.databinding.FragmentProductsBinding
import com.example.sephorachallenge.presentation.di.modules.ProductsModule
import javax.inject.Inject

class ProductsFragment : BaseFragment() {

    //private lateinit var productsAdapter: ProductsAdapter
    private var binding: FragmentProductsBinding? = null

    @Inject
    lateinit var viewModel: ProductsViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(layoutInflater, container, false)

        /*
        productAdapter = ProductAdapter()
        productAdapter.setRequestManager(GlideApp.with(this))
         */

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    override fun onNetworkConnected() {
        if (this::viewModel.isInitialized) {
            viewModel.getProducts
        }
        super.onNetworkConnected()
    }

    private fun observeViewModel() {


    }

    private fun injectDependencies() {
        DaggerProductsComponent.builder()
            .categoriesTabModule(ProductsModule(this))
            .build()
            .inject(this)
    }

    companion object {
        fun newInstance(): ProductsFragment {
            return ProductsFragment()
        }
    }
}