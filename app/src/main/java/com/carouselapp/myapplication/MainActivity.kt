package com.carouselapp.myapplication

import android.os.Bundle
import android.view.ViewTreeObserver.OnScrollChangedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.widget.NestedScrollView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.blocks.*

class MainActivity : AppCompatActivity() {

    val adapter = RecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.blocks3)
        recycler_view.adapter = adapter
        val animationView = findViewById<LottieAnimationView>(R.id.lottie_view)
        val blocks_root = findViewById<MotionLayout>(R.id.blocks_root)
        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)
        val scrollview = findViewById<NestedScrollView>(R.id.nestedScrollView)
        
        blocks_root.setTransition(R.id.animation_transition)
        swipeRefresh.setOnRefreshListener {

            animationView.setMinAndMaxProgress(0.49f,0.56f)
            animationView.playAnimation()

            blocks_root.setTransition(R.id.animation_transition_refresh)
            
            swipeRefresh.isRefreshing = false
        }


        scrollview.viewTreeObserver
            .addOnScrollChangedListener(object : OnScrollChangedListener {
                var lastScroll = 0
                override fun onScrollChanged() {
                    blocks_root.setTransition(R.id.animation_transition)
                    val scrollViewHeight: Double =
                        (scrollview.getChildAt(0).bottom - scrollview.height).toDouble()
                    val getScrollY: Double = scrollview.scrollY.toDouble()
                    val scrollPosition2 = getScrollY / scrollViewHeight * 100.0
                    val scrollPosition = getScrollY / scrollViewHeight
//                    Log.e("@logs", "scroll $scrollPosition2")

                    val scrollY: Int = scrollview.scrollY
                    if (scrollY > lastScroll) {
                        animationView.setMinAndMaxProgress(0.0f,0.21f)
                        animationView.playAnimation()
//                        Log.e("@logs", "down $scrollY")
                    } else if (scrollY < lastScroll) {
                        animationView.setMinAndMaxProgress(0.22f,0.26f)
                        animationView.playAnimation()
//                        Log.e("@logs", "up $scrollY")
                    }
                    lastScroll = scrollY
                }
            })
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return super.onCreateOptionsMenu(menu)
//
//    }
}
