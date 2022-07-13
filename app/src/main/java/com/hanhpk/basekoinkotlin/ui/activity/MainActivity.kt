package com.hanhpk.basekoinkotlin.ui.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hanhpk.basekoinkotlin.R
import com.hanhpk.basekoinkotlin.base.BaseActivity
import com.hanhpk.basekoinkotlin.databinding.ActivityMainBinding
import com.hanhpk.basekoinkotlin.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>(){

    override val viewModel: MainActivityViewModel by viewModel()
    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater,container,false)
    }
    override fun initViews(binding: ActivityMainBinding) {

    }

    override fun layoutId(): Int = R.layout.activity_main

}