package com.thinlineit.favorit_android.android.ui.fundingList

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.skydoves.landscapist.glide.GlideImage
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.FundingInfo
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingActivity
import com.thinlineit.favorit_android.android.ui.detail.FundingDetailActivity
import com.thinlineit.favorit_android.android.ui.fundingList.ui.theme.FundingListBackgroundColor
import com.thinlineit.favorit_android.android.util.shortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FundingListActivity : ComponentActivity() {
    private lateinit var viewModel: FundingListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[FundingListViewModel::class.java].also {
            it.getFundingList()
        }
        initObserver()
        setContent {
            Main(viewModel)
        }
    }

    private fun initObserver() {
        viewModel.loadFundingListsResult.observe(this) {
            when (it) {
                is Result.Loading -> {
                    // nothing to do
                }
                is Result.Fail -> {
                    shortToast("Fail to load funding")
                    finish()
                }
                is Result.Success -> {
                    // nothing to do
                }
            }
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, FundingListActivity::class.java)
            context.startActivity(intent)
        }
    }
}

@Composable
fun Main(viewModel: FundingListViewModel) {
    val myFundingList by viewModel.myFundingList.observeAsState(emptyList())
    val friendFundingList by viewModel.friendFundingList.observeAsState(emptyList())
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = FundingListBackgroundColor
    ) {
        Column {
            ExitButton()
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.default_top_margin)))
            MyFundingList(fundingList = myFundingList)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.default_top_margin)))
            FriendFundingList(fundingList = friendFundingList)
        }
    }
}

@Composable
fun ExitButton() {
    val activity = (LocalContext.current as? Activity)
    Column() {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.default_top_margin)))
        Row() {
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.my_funding_list_left_margin)))
            Image(painter = painterResource(id = R.drawable.icon_exit),
                contentDescription = "",
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.exit_size))
                    .clickable {
                        activity?.finish()
                    })
        }
    }

}

@Composable
fun MyFundingList(fundingList: List<FundingInfo>?) {
    Column() {
        Box() {
            Image(
                painter = painterResource(id = R.drawable.icon_my_funding),
                contentDescription = "",
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.funding_list_name_width))
                    .height(dimensionResource(id = R.dimen.funding_list_name_height))
            )
            Row(modifier = Modifier.align(Alignment.CenterStart)) {
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.exit_left_margin)))
                Text(
                    text = stringResource(id = R.string.label_my_funding_list),
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.rotate(3f)
                )
            }
        }
        if (fundingList == null) {
            Row() {
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.funding_item_left_margin)))
                CreateFunding()
            }
        } else {
            LazyRow {
                itemsIndexed(fundingList) { index, item ->
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.funding_item_left_margin)))
                    if (index % 2 == 0)
                        FundingItem(funding = item, modifier = Modifier.rotate(5.66f), index)
                    else
                        FundingItem(funding = item, modifier = Modifier.rotate(-5.66f), index)
                    if (index == fundingList.lastIndex) {
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.funding_item_left_margin)))
                        CreateFunding()
                    }
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.funding_item_right_margin)))
                }
            }
        }
    }
}

@Composable
fun FriendFundingList(fundingList: List<FundingInfo>?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .align(Alignment.End)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_friend_funding),
                contentDescription = "",
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.funding_list_name_width))
                    .height(dimensionResource(id = R.dimen.funding_list_name_height))
            )
            Row(modifier = Modifier.align(Alignment.CenterStart)) {
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.friend_funding_list_left_margin)))
                Text(
                    text = stringResource(id = R.string.label_friend_funding_list),
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.rotate(-3f)
                )
            }
        }
        if (fundingList == null) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.friend_funding_list_empty_top_margin)))
            Text(text = stringResource(id = R.string.label_empty_funding_list), fontSize = 17.sp)
        } else {
            LazyRow {
                itemsIndexed(fundingList) { index, item ->
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.funding_item_left_margin)))
                    if (index % 2 == 0)
                        FundingItem(funding = item, modifier = Modifier.rotate(5.66f), index + 1)
                    else
                        FundingItem(funding = item, modifier = Modifier.rotate(-5.66f), index + 1)
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.funding_item_right_margin)))
                }
            }
        }
    }
}

@Composable
fun FundingItem(funding: FundingInfo, modifier: Modifier, itemNum: Int) {
    val mContext = LocalContext.current
    Box(
        modifier = modifier
            .width(dimensionResource(id = R.dimen.funding_item_width))
            .height(dimensionResource(id = R.dimen.funding_item_height))
            .clickable {
                FundingDetailActivity.start(mContext, funding.fundingID)
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_funding_item),
            modifier = Modifier.fillMaxSize(),
            contentDescription = ""
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.default_top_margin)))
            FundingItemImage(fundingImage = funding.image, itemNum = itemNum)
            Text(
                text = funding.name,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.funding_item_text_margin)))
            Text(
                text = funding.dueDate.replace('-', '.'),
                textAlign = TextAlign.Center,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun FundingItemImage(fundingImage: String, itemNum: Int) {
    val tapePainter = when (itemNum % 3) {
        0 -> painterResource(id = R.drawable.icon_green_tape)
        1 -> painterResource(id = R.drawable.icon_pink_tape)
        else -> painterResource(id = R.drawable.icon_yellow_tape)
    }

    Box(modifier = Modifier.size(dimensionResource(id = R.dimen.funding_item_box_size))) {
        GlideImage(
            imageModel = fundingImage,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.funding_item_image_size))
                .align(Alignment.Center),
            placeHolder = ImageBitmap.imageResource(R.drawable.icon_celebrate),
            error = ImageBitmap.imageResource(R.drawable.icon_celebrate)
        )
        Image(
            painter = tapePainter,
            contentDescription = "",
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.green_tape_width))
                .align(Alignment.TopEnd)
        )
        Image(
            painter = tapePainter,
            contentDescription = "",
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.green_tape_width))
                .align(Alignment.BottomStart)
        )
    }
}

@Composable
fun CreateFunding() {
    val mContext = LocalContext.current
    Image(
        painter = painterResource(id = R.drawable.icon_create_funding),
        contentDescription = "",
        modifier = Modifier
            .width(dimensionResource(id = R.dimen.funding_item_width))
            .height(dimensionResource(id = R.dimen.funding_item_height))
            .clickable {
                CreateFundingActivity.start(mContext)
            })
}
