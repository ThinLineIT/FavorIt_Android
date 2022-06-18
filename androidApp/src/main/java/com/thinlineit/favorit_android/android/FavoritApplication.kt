package com.thinlineit.favorit_android.android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.thinlineit.favorit_android.android.data.local.FavoritSharedPreference
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FavoritApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
        FavoritSharedPreference.init(applicationContext)
    }
}