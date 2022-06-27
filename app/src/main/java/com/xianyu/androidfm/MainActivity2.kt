package com.xianyu.androidfm

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xianyu.androidlibrary.log.LoggerUtil
import com.xianyu.common.ui.bottom.AppBottomBar
import com.xianyu.common.nav.NavGraphBuilder
import java.lang.ref.SoftReference
import java.util.*
import kotlin.collections.ArrayList


class MainActivity2 : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var navController: NavController? = null
    private var navView: AppBottomBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        /*val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/
        navView = findViewById(R.id.nav_view)

        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        navController = fragment?.let { NavHostFragment.findNavController(it) }
        fragment?.id?.let { NavGraphBuilder.build(this, navController, it) }

        navView?.setOnNavigationItemSelectedListener(this)
        intent.action = "hahah"
        intent.data = Uri.parse("hahn")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

        LoggerUtil.d("MainActivity2","test message")
        LoggerUtil.dObject("MainActivity2",true)
        LoggerUtil.dBundle("MainActivity2",Bundle())
        LoggerUtil.dIntent("MainActivity2",intent)
        LoggerUtil.dObject("MainActivity2", SoftReference (0));
        LoggerUtil.e("MainActivity2",NullPointerException ("this object is null!"));
        val list: MutableList<String> = ArrayList()
        for (i in 0..4) {
            list.add("test$i")
        }
        LoggerUtil.dObject("MainActivity2",list)

        // 强制让应用crash
//        val list2:MutableList<String>? = null
//        LoggerUtil.dObject("MainActivity2", list2!![0])



        val map: MutableMap<String, String> = HashMap()
        for (i in 0..4) {
            map.put("xyy$i", "test$i")
        }
        LoggerUtil.dObject("MainActivity2",map)

        val json =
            "{'xyy1':[{'test1':'test1'},{'test2':'test2'}],'xyy2':{'test3':'test3','test4':'test4'}}"
        LoggerUtil.dJson("MainActivity2",json)

        val xml =
            "<xyy><test1><test2>key</test2></test1><test3>name</test3><test4>value</test4></xyy>"
        LoggerUtil.dXml("MainActivity2",xml)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navController?.navigate(item.itemId)
        return !TextUtils.isEmpty(item.title)
    }
}