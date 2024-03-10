package com.example.products.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import android.widget.AbsListView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.products.databinding.FragmentProductLentaBinding
import com.example.products.presentation.epoxy.ProductLentaEpoxyController

class ProductFragment: Fragment() {
    private var _binding: FragmentProductLentaBinding? = null
    private val binding: FragmentProductLentaBinding by viewBinding()
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var controller: ProductLentaEpoxyController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductLentaBinding.inflate(inflater, container, false)
        controller = ProductLentaEpoxyController(requireActivity())
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Hmm", "I'm in")
        initEpoxyRV()
        observeViewModel()
    }

    private fun initEpoxyRV(){
        binding.lentaEpoxyRv.setController(controller)
        controller.setData(mutableListOf())
        binding.lentaEpoxyRv.addOnScrollListener(
            object : RecyclerView.OnScrollListener () {
                var isScrolling: Boolean = false
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                        isScrolling = true
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount

                    val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount - 4
                    if(isAtLastItem && isScrolling && viewModel.shouldPaginate()){
                        viewModel.download()
                        isScrolling = false
                    }
                }

            }
        )
    }

    private fun observeViewModel(){
        viewModel.products.observe(viewLifecycleOwner){ productList ->
            controller.setData(productList)
        }
        viewModel.isFounded.observe(viewLifecycleOwner){
            binding.lentaEpoxyRv.isVisible = it
            binding.textNotFounded.isVisible = !it
        }
        setupSwipeRefreshLayout()
    }

    private fun setupSwipeRefreshLayout(){
        viewModel.isRefreshing.observe(viewLifecycleOwner){
            binding.lentaEpoxyRv.isClickable = !it
            binding.refreshLayout.isRefreshing = it
        }
        binding.refreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }
}