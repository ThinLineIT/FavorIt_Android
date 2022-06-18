package com.thinlineit.favorit_android.android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.thinlineit.favorit_android.android.util.FavoritSharedPreference

class FavoritApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FavoritSharedPreference.init(applicationContext)
    }
}