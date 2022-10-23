package com.thinlineit.favorit_android.android.ui.present.list

import android.app.ProgressDialog.show
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.PagePresentListBinding
import com.thinlineit.favorit_android.android.ui.present.detail.PresentDetailDialog

class AlbumPageAdapter(
    private val viewModel: PresentListViewModel
) : RecyclerView.Adapter<AlbumPageAdapter.BookPage>() {
    var presentList: MutableList<Present> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookPage {
        return BookPage.from(parent, viewModel)
    }

    override fun onBindViewHolder(holder: BookPage, position: Int) {
        holder.bind(getItemViewType(position))
    }

    override fun getItemCount(): Int = 2

    override fun getItemViewType(position: Int): Int = position

    class BookPage(
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
                // viewModel.onPresentClicked(it)
            }
            dataBinding.presentRecyclerView.adapter = presentAdapter
            val pageInfo =
                if (viewType == LEFT_PAGE) viewModel.leftPageInfo else viewModel.rightPageInfo
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

        interface BookPageClickListener {
            fun onPrevClick()
            fun onNextClick()
            fun onPageClick(nextPage: Int)
        }

        companion object {
            fun from(parent: ViewGroup, viewModel: PresentListViewModel): BookPage {
                val layoutInflater = LayoutInflater.from(parent.context)
                val dataBinding = PagePresentListBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                ).apply {
                    lifecycleOwner = parent.findViewTreeLifecycleOwner()
                }
                return BookPage(dataBinding, viewModel)
            }

            const val LEFT_PAGE = 0
            const val RIGHT_PAGE = 1
        }
    }
}
