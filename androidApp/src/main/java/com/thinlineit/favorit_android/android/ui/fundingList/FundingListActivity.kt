package com.thinlineit.favorit_android.android.ui.fundingList

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.skydoves.landscapist.glide.GlideImage
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.FundingInfo
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingActivity
import com.thinlineit.favorit_android.android.ui.detail.FundingDetailActivity
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
        color = com.thinlineit.favorit_android.android.ui.fundingList.ui.theme.FundingListBackgroundColor
    ) {
        Column {
            ExitButton()
            Spacer(modifier = Modifier.height(40.dp))
            MyFundingList(fundingList = myFundingList)
            Spacer(modifier = Modifier.height(20.dp))
            FriendFundingList(fundingList = friendFundingList)
        }
    }
}

@Composable
fun ExitButton() {
    val activity = (LocalContext.current as? Activity)
    Column() {
        Spacer(modifier = Modifier.height(27.dp))
        Row() {
            Spacer(modifier = Modifier.width(16.dp))
            Image(painter = painterResource(id = R.drawable.icon_exit),
                contentDescription = "",
                modifier = Modifier.clickable {
                    activity?.finish()
                })
        }
    }

}


@Composable
fun MyFundingList(fundingList: List<FundingInfo>?) {
    Column() {
        Box(modifier = Modifier.rotate(3f)) {
            Image(
                painter = painterResource(id = R.drawable.icon_my_funding),
                contentDescription = ""
            )
            Row(modifier = Modifier.align(Alignment.CenterStart)) {
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "내가 만든 펀딩(*/ω＼*)",
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.rotate(3f)
                )
            }

        }
        if (fundingList == null) {
            Spacer(modifier = Modifier.width(30.dp))
            CreateFunding()
        } else {
            LazyRow {
                itemsIndexed(fundingList) { index, item ->
                    Spacer(modifier = Modifier.width(30.dp))
                    if (index % 2 == 0)
                        FundingItem(funding = item, modifier = Modifier.rotate(10f))
                    else
                        FundingItem(funding = item, modifier = Modifier.rotate(350f))
                    if (index == fundingList.lastIndex) {
                        Spacer(modifier = Modifier.width(30.dp))
                        CreateFunding()
                    }
                    Spacer(modifier = Modifier.width(20.dp))
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
                .rotate(358f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_friend_funding),
                contentDescription = ""
            )
            Row(modifier = Modifier.align(Alignment.CenterStart)) {
                Spacer(modifier = Modifier.width(40.dp))
                Text(
                    text = "친구들의 펀딩 (●'e'●)",
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.rotate(357f)
                )
            }

        }
        if (fundingList == null) {
            Text("아직 공유받은 펀딩이 없어요")
        } else {
            LazyRow {
                itemsIndexed(fundingList) { index, item ->
                    Spacer(modifier = Modifier.width(30.dp))
                    if (index % 2 == 0)
                        FundingItem(funding = item, modifier = Modifier.rotate(10f))
                    else
                        FundingItem(funding = item, modifier = Modifier.rotate(350f))
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
        }
    }
}

@Composable
fun FundingItem(funding: FundingInfo, modifier: Modifier) {
    val mContext = LocalContext.current
    Box(
        modifier = modifier
            .width(168.dp)
            .height(242.dp)
            .clickable {
                FundingDetailActivity.start(mContext, funding.fundingID)
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_funding_item),
            contentDescription = ""
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Box(modifier = Modifier.size(168.dp)) {
                GlideImage(
                    imageModel = funding.image,
                    modifier = Modifier
                        .size(130.dp)
                        .align(Alignment.Center),
                    placeHolder = ImageBitmap.imageResource(R.drawable.icon_celebrate),
                    error = ImageBitmap.imageResource(R.drawable.icon_celebrate)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_green_tape),
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.TopEnd)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_green_tape),
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.BottomStart)
                )
                Text(text = funding.name, modifier = Modifier.align(Alignment.BottomCenter))
            }
            Text(text = funding.dueDate, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun CreateFunding() {
    val mContext = LocalContext.current
    Image(
        painter = painterResource(id = R.drawable.icon_create_funding),
        contentDescription = "",
        modifier = Modifier.clickable {
            CreateFundingActivity.start(mContext)
        })
}
