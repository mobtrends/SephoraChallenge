package com.example.sephorachallenge.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.sephorachallenge.app.SephoraChallengeApplication
import com.example.sephorachallenge.databinding.FragmentProductsBinding
import com.example.sephorachallenge.presentation.ProductsDisplayState
import com.example.sephorachallenge.presentation.ProductsViewModel
import com.example.sephorachallenge.presentation.StateChild
import com.example.sephorachallenge.presentation.di.components.DaggerProductsComponent
import com.example.sephorachallenge.presentation.di.modules.ProductsModule
import com.example.sephorachallenge.presentation.state
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
        binding?.errorLayout?.buttonTryAgain?.setOnClickListener { viewModel.getProducts() }
        observeViewModel()
    }

    override fun onNetworkConnected() {
        if (this::viewModel.isInitialized) {
            viewModel.getProducts()
        }
        super.onNetworkConnected()
    }

    private fun observeViewModel() {
        viewModel.displayState.observe(viewLifecycleOwner) { displayState ->
            when (displayState) {
                ProductsDisplayState.Error -> binding?.viewFlipperProducts.state = StateChild.ERROR
                ProductsDisplayState.Loading -> binding?.viewFlipperProducts.state =
                    StateChild.LOADING
                is ProductsDisplayState.Success -> {
                    binding?.viewFlipperProducts.state = StateChild.CONTENT
                    Toast.makeText(
                        requireContext(),
                        displayState.products[0].price,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun injectDependencies() {
        DaggerProductsComponent.builder()
            .applicationComponent((requireActivity().application as SephoraChallengeApplication).applicationComponent)
            .productsModule(ProductsModule(this))
            .build()
            .inject(this)
    }

    companion object {
        fun newInstance(): ProductsFragment {
            return ProductsFragment()
        }
    }
}