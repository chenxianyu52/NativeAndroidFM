package com.xianyu.androidfm

import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xianyu.androidfm.view.AppBottomBar


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
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navController?.navigate(item.itemId)
        return !TextUtils.isEmpty(item.title)
    }
}