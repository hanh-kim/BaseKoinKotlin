package com.hanhpk.basekoinkotlin.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hanhpk.basekoinkotlin.base.BaseFragment
import com.hanhpk.basekoinkotlin.databinding.FragmentThirdBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ThirdFragment : BaseFragment<FragmentThirdBinding>() {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentThirdBinding {
        return FragmentThirdBinding.inflate(inflater, container, false)
    }

    override fun initViews(viewBinding: FragmentThirdBinding) {
    }

    override fun initData() {
    }

    override fun initListeners() {

    }
}