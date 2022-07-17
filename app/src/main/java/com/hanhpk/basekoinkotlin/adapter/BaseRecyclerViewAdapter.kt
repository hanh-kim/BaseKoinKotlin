package com.hanhpk.basekoinkotlin.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.hanhpk.basekoinkotlin.BR
import com.hanhpk.basekoinkotlin.base.BaseItem
import com.hanhpk.basekoinkotlin.extensions.onAvoidDoubleClick

open class BaseRecyclerViewAdapter<T> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var clickListener: ClickListener? = null
    private var listItems: List<T> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewDataBinding: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return BaseViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model: BaseItem = getItems(position) as BaseItem
        val baseViewHolder: BaseViewHolder = holder as BaseViewHolder
        baseViewHolder.bind(model)
        baseViewHolder.viewDataBinding.root.onAvoidDoubleClick {
            clickListener?.onClickItem(position, baseViewHolder.viewDataBinding.root)
        }
        baseViewHolder.viewDataBinding.root.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    onTouchItem()
                }
            }
            false
        }
    }

    fun setItems(listItems: List<T>) {
        this.listItems = listItems
        notifyDataSetChanged()
    }

    fun updateItems(listItems: List<T>) {
        this.listItems = emptyList()
        this.listItems = listItems
        notifyDataSetChanged()
    }

    override fun getItemCount() = listItems.size

    override fun getItemViewType(position: Int): Int {
        return getLayoutId(position)
    }

    private fun getLayoutId(position: Int): Int {
        val model: BaseItem = listItems[position] as BaseItem
        return model.layoutResourceId
    }

    open fun getItems(position: Int): T {
        return listItems[position]
    }

    fun getListItems() = listItems

    open fun getListItem(): ArrayList<T> {
        return listItems as ArrayList<T>
    }

    class BaseViewHolder(var viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(
        viewDataBinding.root
    ) {
        fun bind(model: BaseItem?) {
            viewDataBinding.setVariable(BR.model, model)
            viewDataBinding.executePendingBindings()
        }
    }

    open fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener!!
    }

    var onTouchItem: () -> Unit = {}

    interface ClickListener {
        fun onClickItem(position: Int, view: View?)
    }
}