package com.thinlineit.favorit_android.android.ui.createfunding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.thinlineit.favorit_android.android.util.longToast

open class CreateFundingBaseFragment<T : ViewDataBinding>(
    @LayoutRes private val contentLayoutId: Int
) : Fragment(contentLayoutId) {
    lateinit var navController: NavController
    lateinit var binding: T
    val viewModel by lazy {
        ViewModelProvider(requireActivity())[CreateFundingViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<T>(inflater, contentLayoutId, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        initToast()
        return binding.root
    }

    private fun initToast() {
        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            if (message.isEmpty()) return@observe
            requireContext().longToast(message)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }
}
