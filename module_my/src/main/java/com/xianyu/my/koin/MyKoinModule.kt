package com.xianyu.my.koin

import com.xianyu.my.HelloRepositoryImpl
import com.xianyu.my.user.login.LoginRepository
import com.xianyu.my.user.login.LoginViewModel
import com.xianyu.net2.net.RetrofitClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myKoinModule = module {
    single {
        RetrofitClient()
    }

    single {
        LoginRepository(get())
    }

    viewModel {
        LoginViewModel(get())
    }

    single{ HelloRepositoryImpl() }
}