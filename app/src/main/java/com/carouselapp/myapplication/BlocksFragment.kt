package com.carouselapp.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.carouselapp.myapplication.databinding.FragmentBlocksBinding

class BlocksFragment : Fragment() {

    private var _binding: FragmentBlocksBinding? = null
    val adapter = RecyclerAdapter()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBlocksBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter

        binding.blocksRoot.setTransition(R.id.animation_transition)

        swipeRefresh()
        setScroll()
    }

    private fun setScroll() {
        binding.blocksRoot.setTransition(R.id.animation_transition)
        binding.blocksRoot.getConstraintSet(R.id.end).setFloatValue(R.id.lottie_view, "Progress", 0.0f)
        binding.blocksRoot.getConstraintSet(R.id.start).setFloatValue(R.id.lottie_view, "Progress", 0.0f)

        val scrollview = binding.nestedScrollView
        scrollview.viewTreeObserver
            .addOnScrollChangedListener(object : ViewTreeObserver.OnScrollChangedListener {
                var lastScroll = 0
                override fun onScrollChanged() {

                    val scrollViewHeight: Double = (scrollview.getChildAt(0).bottom - scrollview.height).toDouble()
                    val getScrollY: Double = scrollview.scrollY.toDouble()

                    val scrollPosition = getScrollY / scrollViewHeight

                    val scrollY: Int = scrollview.scrollY
                    if (scrollY > lastScroll) {
                        if (binding.lottieView.progress < 0.08f) {
                            binding.lottieView.progress = scrollPosition.toFloat()
                        }

//                        Log.e("@logs", "down $scrollY")
                    }
                    if(scrollY < lastScroll) {
//                        Log.e("@logs", "up $scrollY")
                    }
                    lastScroll = scrollY

                }
            })
    }

    // Extracted from Chrysalis
    private fun swipeRefresh() = with(binding.swipeRefresh3) {
        this.setOnRefreshCustomListener() {
            binding.blocksRoot.getConstraintSet(R.id.pullToRefreshEnd).setFloatValue(R.id.lottie_view, "Progress", 0.5635f)
            binding.blocksRoot.getConstraintSet(R.id.pullToRefreshStart).setFloatValue(R.id.lottie_view, "Progress", 0.64f)
            binding.blocksRoot.rebuildScene()

            isRefreshing = false
        }
        this.setOnProgressListener(object : SwipeRefreshLayout.OnProgressListener {
            override fun onProgress(progress: Float) = with(binding.blocksRoot) {
                if (isRefreshTransition()) {
                    this.progress = progress
                    Log.d("@logs", "onProgress $progress")
                }
            }

            override fun onSwipeStart() = with(binding.blocksRoot) {
                Log.d("@logs", "onSwipeStart $progress")
                setTransition(R.id.refresh_transition)
                this.getConstraintSet(R.id.pullToRefreshEnd).setFloatValue(R.id.lottie_view, "Progress", 0.545f)
                this.getConstraintSet(R.id.pullToRefreshStart).setFloatValue(R.id.lottie_view, "Progress", 0.5f)
                rebuildScene()
            }

            override fun onSwipeCancel() = with(binding.blocksRoot) {
                Log.d("@logs", "onSwipeCancel $progress")
                setTransition(R.id.animation_transition)

                progress = 0f
            }
        })
    }

    private fun MotionLayout.isRefreshTransition(): Boolean {
        val endId = transitionState.getInt("motion.EndState", -1)
        val startId = transitionState.getInt("motion.StartState", -1)
        return endId == R.id.pullToRefreshEnd && startId == R.id.pullToRefreshStart
    }

}





