package com.example.sephorachallenge.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sephorachallenge.SephoraChallengeApplication
import com.example.sephorachallenge.databinding.FragmentProductsBinding
import com.example.sephorachallenge.presentation.ProductsDisplayState
import com.example.sephorachallenge.presentation.ProductsViewModel
import com.example.sephorachallenge.presentation.StateChild
import com.example.sephorachallenge.presentation.adapter.ProductsAdapter
import com.example.sephorachallenge.presentation.di.components.DaggerProductsComponent
import com.example.sephorachallenge.presentation.di.modules.ProductsModule
import com.example.sephorachallenge.presentation.state
import javax.inject.Inject

class ProductsFragment : BaseFragment() {

    private lateinit var productsAdapter: ProductsAdapter
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
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.errorLayout?.tryAgainButton?.setOnClickListener { viewModel.getProducts() }
        binding?.swipeContainer?.setOnRefreshListener { viewModel.getProducts() }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.displayState.observe(viewLifecycleOwner) { displayState ->
            when (displayState) {
                ProductsDisplayState.Error -> binding?.productsViewFlipper.state = StateChild.ERROR
                ProductsDisplayState.Loading -> binding?.productsViewFlipper.state =
                    StateChild.LOADING
                is ProductsDisplayState.Success -> {
                    binding?.productsViewFlipper.state = StateChild.CONTENT
                    binding?.swipeContainer?.isRefreshing = false
                    productsAdapter =
                        ProductsAdapter(displayState.products, ::onProductClickListener)
                    binding?.productsRecyclerView?.adapter = productsAdapter
                    binding?.productsRecyclerView?.layoutManager =
                        LinearLayoutManager(requireContext())
                }
            }
        }
    }

    private fun onProductClickListener(id: Int) {
        openProductDetail(id)
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