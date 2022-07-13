package com.hanhpk.basekoinkotlin.base

import androidx.databinding.BaseObservable

abstract class BaseItem : BaseObservable() {
    private var objectTitle: String?
    get() = objectTitle
    set(value) {
        objectTitle = value
    }

    abstract val layoutResourceId: Int

    open var itemKey: Any? = null
}