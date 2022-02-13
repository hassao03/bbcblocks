package com.carouselapp.myapplication

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.carouselapp.myapplication.SwipeRefreshLayout as OurSwipeRefreshLayout

interface SwipeRefreshView {
    fun setOnRefreshCustomListener(listener: SwipeRefreshLayout.OnRefreshListener)
    fun setOnProgressListener(listener: OurSwipeRefreshLayout.OnProgressListener)
    fun setRefreshing(refreshing: Boolean)
}