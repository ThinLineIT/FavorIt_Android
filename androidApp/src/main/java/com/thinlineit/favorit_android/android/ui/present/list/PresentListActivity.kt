package com.thinlineit.favorit_android.android.ui.present.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.lifecycle.ViewModelProvider
import com.thinlineit.favorit_android.android.databinding.ActivityPresentListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PresentListActivity : AppCompatActivity() {

    private val binding: ActivityPresentListBinding by lazy {
        ActivityPresentListBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@PresentListActivity
        }
    }

    private val viewModel: PresentListViewModel by lazy {
        ViewModelProvider(this)[PresentListViewModel::class.java]
    }

    private val albumPageAdapter by lazy {
        AlbumPageAdapter(this, viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.albumViewPager.apply {
            adapter = albumPageAdapter.apply {
                setPageTransformer { view: View, position: Float ->
                    /* if (needDissolve) {
                         view.alpha = 0.3f
                         view.animate()
                             .alpha(1f)
                             .setDuration(500)
                     }*/
                }
                isUserInputEnabled = false
            }
        }
        initObserver()
    }

    private fun initObserver() {
        viewModel.pagingEvent.observe(this) {
            binding.albumViewPager.currentItem = it.toLeftPage
        }
        viewModel.presentStatusText.observe(this){
            binding.presentListTitle.text = Html.fromHtml(it, FROM_HTML_MODE_LEGACY)
        }
    }

    companion object {
        fun start(context: Context, fundingId: Int) {
            val intent = Intent(context, PresentListActivity::class.java).apply {
                putExtra(FUNDING_ID, fundingId)
            }
            context.startActivity(intent)
        }

        const val FUNDING_ID = "FUNDING_ID"
    }
}
