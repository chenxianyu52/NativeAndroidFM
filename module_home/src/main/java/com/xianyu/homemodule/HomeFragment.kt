package com.xianyu.homemodule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.fastjson.JSONObject
import com.xianyu.annotation.FragmentDestination
import com.xianyu.net.ApiResponse
import com.xianyu.net.ApiService
import com.xianyu.net.JsonCallback
import kotlinx.coroutines.MainScope

@FragmentDestination(pageUrl = "main/tabs/home", asStarter = true)
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    val scope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ApiService.init("http://120.26.106.207:7001", null)
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        val btn: Button = root.findViewById(R.id.web_btn)
        btn.setOnClickListener {
            ApiService.get<Any>("").execute(object : JsonCallback<JSONObject>() {
                override fun onSuccess(response: ApiResponse<JSONObject>?) {
                    if (response!!.body != null) {
                    }
                }
            })
        }
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

//        scope.launch {
//            try {
//                val result = NetApi.createGithubApi().searchRepos()
//                if(result != null && !result.result.isNullOrEmpty()){
//                    textView.text = result.result!![0].title
//                }
//            }catch (e: Exception){
//                e.printStackTrace()
//            }
//        }
        return root
    }
}