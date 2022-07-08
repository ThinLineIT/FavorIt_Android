package com.thinlineit.favorit_android.android.ui.gift

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.thinlineit.favorit_android.android.databinding.ActivityGiftBinding

class GiftActivity : AppCompatActivity() {
    private val binding: ActivityGiftBinding by lazy {
        ActivityGiftBinding.inflate(layoutInflater)
    }
    lateinit var viewModel: GiftViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[GiftViewModel::class.java]
        binding.apply {
            lifecycleOwner = this@GiftActivity
            this.giftViewModel = viewModel
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, GiftActivity::class.java)
            context.startActivity(intent)
        }
    }
}
