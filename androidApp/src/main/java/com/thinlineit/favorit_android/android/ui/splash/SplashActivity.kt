package com.thinlineit.favorit_android.android.ui.splash

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.ActivitySplashBinding
import com.thinlineit.favorit_android.android.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private var isStartedAnim = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.scrollView.setOnTouchListener { v, event -> true }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!isStartedAnim) {
            val left = binding.imageBack.left
            val right = binding.imageBack.right
            val width = binding.scrollView.width
            isStartedAnim = true

            binding.scrollView.scrollTo(left, 0)

            CoroutineScope(Dispatchers.Main).launch {
                launch {
                    delay(2000)
                    moveRight()
                    delay(500)
                    moveCenter()
                    delay(2000)
                    MainActivity.start(this@SplashActivity)
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                    finish()
                }
            }
        }
    }

    fun moveCenter() {
        val left = binding.imageBack.left
        val right = binding.imageBack.right
        val width = binding.scrollView.width
        val ani = ObjectAnimator
            .ofInt(binding.scrollView, "scrollX", (right + left - width) / 2)
            .setDuration(500)
        ani.interpolator = AccelerateDecelerateInterpolator()
        ani.start()
    }

    fun moveRight() {
        val right = binding.imageBack.right
        val ani = ObjectAnimator
            .ofInt(binding.scrollView, "scrollX", (right))
            .setDuration(1000)
        ani.interpolator = AccelerateDecelerateInterpolator()
        ani.start()
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SplashActivity::class.java)
            context.startActivity(intent)
        }
    }
}
