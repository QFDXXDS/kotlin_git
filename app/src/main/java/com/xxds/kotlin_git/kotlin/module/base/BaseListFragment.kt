package com.xxds.kotlin_git.kotlin.module.base

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import com.shuyu.commonrecycler.BindSuperAdapter
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.commonrecycler.listener.OnItemClickListener
import com.shuyu.commonrecycler.listener.OnLoadingListener
import com.xxds.kotlin_git.kotlin.model.ui.EmptyUIModel
import com.xxds.kotlin_git.kotlin.ui.holder.EmptyHolder
import com.xxds.kotlin_git.kotlin.ui.holder.base.BindCustomRefreshHeader
import com.xxds.kotlin_git.kotlin.ui.holder.base.BindCustomloadMoreFooter
import com.xxds.kotlin_git.kotlin.ui.holder.base.BindingDataRecyclerManager
import javax.inject.Inject

abstract class BaseListFragment<T : ViewDataBinding, R : BaseViewModel>: BaseFragment<T>(), OnItemClickListener, OnLoadingListener {

    protected  var normalAdapterManager by autoCleared<BindingDataRecyclerManager>()

    private lateinit var baseViewModel: R

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var adapter by autoCleared<BindSuperAdapter>()

    override fun onCreateView(mainView: View?) {

        normalAdapterManager = BindingDataRecyclerManager()
        baseViewModel = ViewModelProviders.of(this,viewModelFactory)
            .get(getViewModelClass())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         initList()

        getViewModel().loading.observe(this,  Observer {

            when(it) {

                LoadState.RefreshDone -> {
                    refreshComplete()
                }
                LoadState.LoadMoreDone -> {
                    loadMoreComplete()
                }
                LoadState.Refresh -> {
                    ///刷新时清空旧数据
                }
            }
        })

        getViewModel().dataList.observe(this, Observer { items ->

            items?.apply {
                if (items.size > 0) {

                    if (getViewModel().isFirstData()) {
                        adapter?.dataList?.clear()
                    }
                    val currentSize: Int = adapter?.dataList?.size ?: 0
                    adapter?.dataList?.addAll(items)
                    if (currentSize == 0) {

                        notifyChanged()
                    } else {
                        notifyInsert(currentSize,items.size)
                    }
                } else {

                    if (getViewModel().isFirstData()) {
                        adapter?.dataList?.clear()
                        notifyChanged()
                    }
                }
            }
        })

        getViewModel().needMore.observe(this, Observer {

            it?.apply {
                normalAdapterManager?.setNoMore(!it)
            }
        })

        showRefresh()

    }

    open fun refreshComplete() {
        normalAdapterManager?.refreshComplete()
    }

    open fun loadMoreComplete() {
        normalAdapterManager?.loadMoreComplete()
    }





    override fun onRefresh() {

        getViewModel().refresh()
    }

    override fun onLoadMore() {

        getViewModel().loadMore()
    }






    open fun showRefresh() {

        normalAdapterManager?.setRefreshing(true)

    }

    open fun isLoading(): Boolean = getViewModel().isLoading()

    open fun notifyInsert(position: Int, count: Int) {
        adapter?.notifyItemRangeInserted(position + adapter!!.absFirstPosition(), count)
    }

    open fun notifyDelete(position: Int, count: Int) {
        adapter?.dataList?.removeAt(position)
        adapter?.notifyItemRangeRemoved(position + adapter!!.absFirstPosition(), count)
    }

    open fun notifyChanged() {
        adapter?.notifyDataSetChanged()
    }


    override fun onItemClick(context: Context, position: Int) {

    }





    /**
     * 当前 recyclerView，为空即不走 @link[initList] 的初始化
     */
    abstract fun getRecyclerView(): RecyclerView?

    /**
     * 绑定Item
     */
    abstract fun bindHolder(manager: BindSuperAdapterManager)

    /**
     * ViewModel Class
     */
    abstract fun getViewModelClass(): Class<R>

    /**
     * ViewModel
     */
    open fun getViewModel(): R = baseViewModel
    /**
     * 是否需要下拉刷新
     */
    open fun enableRefresh(): Boolean = false

    /**
     * 是否需要下拉刷新
     */
    open fun enableLoadMore(): Boolean = false





    fun initList() {

        if (activity != null && getRecyclerView() != null) {

            normalAdapterManager?.setPullRefreshEnabled(enableRefresh())
                ?.setLoadingMoreEnabled(enableLoadMore())
                ?.setOnItemClickListener(this)
                ?.setLoadingListener(this)
                ?.setRefreshHeader(BindCustomRefreshHeader(activity!!))
                ?.setFootView(BindCustomloadMoreFooter(activity!!))
                ?.setLoadingMoreEmptyEnabled(false)
                ?.bindEmpty(EmptyUIModel(), EmptyHolder.ID, EmptyHolder::class.java)

            normalAdapterManager?.apply {

                bindHolder(this)
                adapter = BindSuperAdapter(activity as Context, this, arrayListOf())
                getRecyclerView()?.layoutManager = LinearLayoutManager(activity!!)
                getRecyclerView()?.adapter = adapter
            }

        }

    }


}