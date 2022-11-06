package com.thinlineit.favorit_android.android.ui.present.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.data.entity.Present
import com.thinlineit.favorit_android.android.databinding.PagePresentListBinding
import com.thinlineit.favorit_android.android.ui.present.detail.PresentDetailDialog

class AlbumPageAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: PresentListViewModel
) : RecyclerView.Adapter<AlbumPageAdapter.BookPage>() {
    var presentList: MutableList<Present> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookPage {
        return BookPage.from(lifecycleOwner, parent, viewModel)
    }

    override fun onBindViewHolder(holder: BookPage, position: Int) {
        holder.bind(getItemViewType(position))
    }

    override fun getItemCount(): Int = 2

    override fun getItemViewType(position: Int): Int = position

    class BookPage(
        private val lifecycleOwner: LifecycleOwner,
        private val dataBinding: PagePresentListBinding,
        private val viewModel: PresentListViewModel
    ) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(
            viewType: Int
        ) {
            val presentAdapter = PresentAdapter {
                PresentDetailDialog(dataBinding.root.context, it).apply {
                    show()
                }
            }
            dataBinding.presentRecyclerView.adapter = presentAdapter
            val pageInfo =
                if (viewType == LEFT_PAGE) viewModel.leftPageInfo else viewModel.rightPageInfo
            initObserver(pageInfo)
            dataBinding.pageInfo = pageInfo
            if (viewType == LEFT_PAGE) {
                dataBinding.root.background = AppCompatResources.getDrawable(
                    dataBinding.root.context,
                    R.drawable.background_left_album
                )
            } else {
                dataBinding.root.background = AppCompatResources.getDrawable(
                    dataBinding.root.context,
                    R.drawable.background_right_album
                )
            }
            dataBinding.prevButton.setOnClickListener {
                viewModel.onPrevClick()
            }
            dataBinding.nextButton.setOnClickListener {
                viewModel.onNextClick()
            }
        }

        private fun initObserver(pageInfo: LiveData<PresentListViewModel.PageInfo?>) {
            pageInfo.observe(lifecycleOwner) { pageInfo ->
                if (pageInfo == null) return@observe
                dataBinding.currentPageNumberTextView.text = (pageInfo.page+1).toString()
                if (pageInfo.isPrevAvailable) {
                    dataBinding.prevButton.visibility = View.VISIBLE
                    dataBinding.prevPageNumberTextView.visibility = View.VISIBLE
                    dataBinding.prevPageNumberTextView.text = pageInfo.page.toString()
                } else {
                    dataBinding.prevButton.visibility = View.GONE
                    dataBinding.prevPageNumberTextView.visibility = View.GONE
                }
                if (pageInfo.isNextAvailable) {
                    dataBinding.nextButton.visibility = View.VISIBLE
                    dataBinding.nextPageNumberTextView.visibility = View.VISIBLE
                    dataBinding.prevPageNumberTextView.text = (pageInfo.page + 2).toString()
                } else {
                    dataBinding.nextButton.visibility = View.GONE
                    dataBinding.nextPageNumberTextView.visibility = View.GONE
                }
            }
        }

        interface BookPageClickListener {
            fun onPrevClick()
            fun onNextClick()
            fun onPageClick(nextPage: Int)
        }

        companion object {
            fun from(lifecycleOwner: LifecycleOwner, parent: ViewGroup, viewModel: PresentListViewModel): BookPage {
                val layoutInflater = LayoutInflater.from(parent.context)
                val dataBinding = PagePresentListBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                dataBinding.lifecycleOwner = lifecycleOwner
                return BookPage(lifecycleOwner, dataBinding, viewModel)
            }

            const val LEFT_PAGE = 0
            const val RIGHT_PAGE = 1
        }
    }
}
