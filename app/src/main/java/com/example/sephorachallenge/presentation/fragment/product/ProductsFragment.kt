package com.example.sephorachallenge.presentation.fragment.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sephorachallenge.SephoraChallengeApplication
import com.example.sephorachallenge.databinding.FragmentProductsBinding
import com.example.sephorachallenge.presentation.StateChild
import com.example.sephorachallenge.presentation.adapter.ProductsAdapter
import com.example.sephorachallenge.presentation.di.components.DaggerProductsComponent
import com.example.sephorachallenge.presentation.di.modules.ProductsModule
import com.example.sephorachallenge.presentation.fragment.BaseFragment
import com.example.sephorachallenge.presentation.state
import com.example.sephorachallenge.presentation.viewmodels.product.ProductsDisplayState
import com.example.sephorachallenge.presentation.viewmodels.product.ProductsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        //binding?.swipeContainer?.setOnRefreshListener { viewModel.getProducts() }
        viewModel.getProducts()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun observeViewModel() {
        viewModel.displayState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { displayState ->
                when (displayState) {
                    ProductsDisplayState.Error -> binding?.productsViewFlipper.state =
                        StateChild.ERROR
                    ProductsDisplayState.Loading -> binding?.productsViewFlipper.state =
                        StateChild.LOADING
                    is ProductsDisplayState.Success -> {
                        //binding?.swipeContainer?.isRefreshing = false
                        productsAdapter =
                            ProductsAdapter(displayState.products, ::onProductClickListener)
                        binding?.productsRecyclerView?.adapter = productsAdapter
                        binding?.productsRecyclerView?.layoutManager =
                            LinearLayoutManager(requireContext())
                        binding?.productsViewFlipper.state = StateChild.CONTENT
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun onProductClickListener(id: Int, position: Int) {
        openProductDetail(id)

        /** Test Smooth Scroll **/

        /*val scrollToPosition = 3
        if (position != 0) {
            openProductDetail(id)
        } else {
            Handler(Looper.getMainLooper()).post {
                val smoothScroller: SmoothScroller = object : LinearSmoothScroller(context) {
                    override fun getVerticalSnapPreference(): Int {
                        return SNAP_TO_START
                    }
                }
                smoothScroller.targetPosition = scrollToPosition
                (binding?.productsRecyclerView?.layoutManager as LinearLayoutManager).startSmoothScroll(
                    smoothScroller
                )
            }
        }*/
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