package com.xianyu.my.user.login

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xianyu.androidlibrary.AppGlobals
import com.xianyu.net2.model.NetResult

import kotlinx.coroutines.launch


class LoginViewModel(private val loginRepo: LoginRepository) : ViewModel() {


    private val loginLiveData = MutableLiveData<User>()

    fun login(username: String, password: String): MutableLiveData<User> {
        viewModelScope.launch {
            val user = loginRepo.login(username, password)
            if (user is NetResult.Success) {
                loginLiveData.postValue(user.data)
            } else if (user is NetResult.Error) {
                Toast.makeText(
                    AppGlobals.getApplication(),
                    user.exception.msg,
                    Toast.LENGTH_LONG
                ).show()
            }

        }

        return loginLiveData
    }

    fun register(username: String, password: String, surePassword: String): MutableLiveData<User> {
        viewModelScope.launch {
            val user = loginRepo.register(username, password, surePassword)
            if (user is NetResult.Success) {
                loginLiveData.postValue(user.data)
            } else if (user is NetResult.Error) {
                Toast.makeText(
                    AppGlobals.getApplication(),
                    user.exception.msg,
                    Toast.LENGTH_LONG
                ).show()
            }

        }

        return loginLiveData
    }

}