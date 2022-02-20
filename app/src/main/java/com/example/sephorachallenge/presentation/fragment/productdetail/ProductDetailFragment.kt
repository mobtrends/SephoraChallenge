package com.example.sephorachallenge.presentation.fragment.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.sephorachallenge.SephoraChallengeApplication
import com.example.sephorachallenge.databinding.FragmentProductDetailBinding
import com.example.sephorachallenge.presentation.StateChild
import com.example.sephorachallenge.presentation.di.components.DaggerProductDetailComponent
import com.example.sephorachallenge.presentation.di.modules.ProductDetailModule
import com.example.sephorachallenge.presentation.fragment.BaseFragment
import com.example.sephorachallenge.presentation.state
import com.example.sephorachallenge.presentation.viewmodels.productdetail.ProductDetailDisplayState
import com.example.sephorachallenge.presentation.viewmodels.productdetail.ProductDetailViewModel
import javax.inject.Inject

class ProductDetailFragment : BaseFragment() {

    private var id: Int? = null
    private var binding: FragmentProductDetailBinding? = null

    @Inject
    lateinit var viewModel: ProductDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        /*arguments?.let { bundle ->
            id = bundle.getInt(ID)
        }*/
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
        binding?.errorLayout?.tryAgainButton?.setOnClickListener { viewModel.getProductDetail() }
        observeViewModel()
    }

    private fun injectDependencies() {
        DaggerProductDetailComponent.builder()
            .applicationComponent((requireActivity().application as SephoraChallengeApplication).applicationComponent)
            .productDetailModule(ProductDetailModule(this, (arguments?.get(ID) as? Int) ?: 0))
            .build()
            .inject(this)
    }

    private fun observeViewModel() {
        viewModel.displayState.observe(viewLifecycleOwner) { detailDisplayState ->
            when (detailDisplayState) {
                ProductDetailDisplayState.Error -> binding?.productsDetailViewFlipper.state =
                    StateChild.ERROR
                ProductDetailDisplayState.Loading -> binding?.productsDetailViewFlipper.state =
                    StateChild.LOADING
                is ProductDetailDisplayState.Success -> {
                    binding?.productDetailLayout?.brandNameTextView?.text =
                        detailDisplayState.products.brandName
                    binding?.productDetailLayout?.productNameTextView?.text =
                        detailDisplayState.products.productName
                    binding?.productDetailLayout?.descriptionTextView?.text =
                        detailDisplayState.products.description
                    binding?.productDetailLayout?.productPriceTextView?.text =
                        detailDisplayState.products.price
                    binding?.productDetailLayout?.imageDetailImageView?.let { imageView ->
                        Glide.with(requireContext())
                            .load(detailDisplayState.products.imageUrl)
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
            ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID, id)
                }
            }
    }
}