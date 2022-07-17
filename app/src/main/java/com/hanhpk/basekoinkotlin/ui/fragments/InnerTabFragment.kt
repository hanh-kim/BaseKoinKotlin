package com.hanhpk.basekoinkotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hanhpk.basekoinkotlin.base.BaseFragment
import com.hanhpk.basekoinkotlin.databinding.FragmentInnerTabBinding

class InnerTabFragment : BaseFragment<FragmentInnerTabBinding>() {
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentInnerTabBinding {
        return FragmentInnerTabBinding.inflate(
            inflater, container, false
        )
    }

    override fun initViews(viewBinding: FragmentInnerTabBinding) {
        binding.message.text = arguments?.getString(ARGS_MESSAGE) ?: "InnerTabFragment"
    }

    override fun initData() {
    }

    override fun initListeners() {
    }

    companion object {
        fun newInstance(message: String): InnerTabFragment {
            return InnerTabFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGS_MESSAGE, message)
                }
            }
        }

        private const val ARGS_MESSAGE = "ARGS_MESSAGE"
    }
}