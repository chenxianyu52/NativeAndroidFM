package com.xianyu.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.xianyu.androidlibrary.AppGlobals
import com.xianyu.androidlibrary.log.LoggerUtil
import com.xianyu.androidlibrary.utils.ToastUtil
import com.xianyu.annotation.FragmentDestination
import com.xianyu.my.user.login.LoginRepository
import com.xianyu.my.user.login.LoginViewModel
import com.xianyu.net2.model.NetResult
import com.xianyu.net2.net.RetrofitClient
import com.xianyu.route.koin.injectOrNull
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


@FragmentDestination(pageUrl = "main/tabs/my", asStarter = false)
class MyFragment : Fragment() {

    private lateinit var myViewModel: MyViewModel

//    private val client by inject<RetrofitClient>()
//
//    private val repo by inject<LoginRepository>{ parametersOf(client)}
//
//    private val repo = LoginRepository(RetrofitClient())

    private val requestModel by viewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myViewModel =
            ViewModelProvider(this).get(MyViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_my, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        val helloRepository: HelloRepositoryImpl? by injectOrNull()
        myViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = helloRepository?.giveHello()
        })

        val button: Button = root.findViewById(R.id.request_button)
        button.setOnClickListener {
//            requestModel.login("chenxianyu","llll").observe(viewLifecycleOwner, Observer {
//                ToastUtil.showToast("ggggggg")
//            })

            requestModel.login("username","password").observe(viewLifecycleOwner, Observer {
                LoggerUtil.dObject("cxy",it);
            })
        }
        return root
    }
}