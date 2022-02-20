package com.example.sephorachallenge.presentation.fragment.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.sephorachallenge.SephoraChallengeApplication
import com.example.sephorachallenge.databinding.FragmentProductDetailBinding
import com.example.sephorachallenge.presentation.StateChild
import com.example.sephorachallenge.presentation.di.components.DaggerProductDetailsComponent
import com.example.sephorachallenge.presentation.di.modules.ProductDetailsModule
import com.example.sephorachallenge.presentation.fragment.BaseFragment
import com.example.sephorachallenge.presentation.state
import com.example.sephorachallenge.presentation.viewmodels.productdetail.ProductDetailsDisplayState
import com.example.sephorachallenge.presentation.viewmodels.productdetail.ProductDetailsViewModel
import javax.inject.Inject

class ProductDetailsFragment : BaseFragment() {

    private var binding: FragmentProductDetailBinding? = null

    @Inject
    lateinit var viewModel: ProductDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.errorLayout?.tryAgainButton?.setOnClickListener { viewModel.getProductDetails() }
        observeViewModel()
    }

    private fun injectDependencies() {
        DaggerProductDetailsComponent.builder()
            .applicationComponent((requireActivity().application as SephoraChallengeApplication).applicationComponent)
            .productDetailsModule(ProductDetailsModule(this, (arguments?.get(ID) as? Int) ?: 0))
            .build()
            .inject(this)
    }

    private fun observeViewModel() {
        viewModel.displayState.observe(viewLifecycleOwner) { detailsDisplayState ->
            when (detailsDisplayState) {
                ProductDetailsDisplayState.Error -> binding?.productsDetailViewFlipper.state =
                    StateChild.ERROR
                ProductDetailsDisplayState.Loading -> binding?.productsDetailViewFlipper.state =
                    StateChild.LOADING
                is ProductDetailsDisplayState.Success -> {
                    binding?.productDetailLayout?.brandNameTextView?.text =
                        detailsDisplayState.displayableProductDetails.brandName
                    binding?.productDetailLayout?.productNameTextView?.text =
                        detailsDisplayState.displayableProductDetails.productName
                    binding?.productDetailLayout?.descriptionTextView?.text =
                        detailsDisplayState.displayableProductDetails.description
                    binding?.productDetailLayout?.productPriceTextView?.text =
                        detailsDisplayState.displayableProductDetails.price
                    binding?.productDetailLayout?.imageDetailImageView?.let { imageView ->
                        Glide.with(requireContext())
                            .load(detailsDisplayState.displayableProductDetails.imageUrl)
                            .into(imageView)
                    }
                    binding?.productsDetailViewFlipper.state = StateChild.CONTENT
                }
            }
        }
    }

    companion object {

        private const val ID = "productId"

        @JvmStatic
        fun newInstance(id: Int) =
            ProductDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID, id)
                }
            }
    }
}