package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    private val dashboardFragment = DashboardFragment()
//    private val servicesFragment = ServicesFragment()
//    private val notificationFragment = NotificationsFragment()
//    private val profileFragment = ProfileFragment()

//    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        when (item.itemId) {
//            R.id.nav_dashboard -> {
//                moveToFragment(DashboardFragment())
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.nav_services -> {
//                moveToFragment(ServicesFragment())
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.nav_notification -> {
//                moveToFragment(NotificationsFragment())
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.nav_profile -> {
//                moveToFragment(ProfileFragment())
//                return@OnNavigationItemSelectedListener true
//            }
//        }
//        false
//    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        announcementBtn.setOnClickListener {
            val intent = Intent(this, AnnouncementActivity::class.java)
            startActivity(intent)
        }

        dataVizBtn.setOnClickListener {
            val intent = Intent(this, DataVisualizationActivity::class.java)
            startActivity(intent)
        }

        servicesBtn.setOnClickListener {
            val intent = Intent(this, ServicesActivity::class.java)
            startActivity(intent)
        }

        notificationBtn.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        profileBtn.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

//        replaceFragment(dashboardFragment)
//
//        bottom_navigation.setOnNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.dashboard -> replaceFragment(dashboardFragment)
//                R.id.services -> replaceFragment(servicesFragment)
//                R.id.notification -> replaceFragment(notificationFragment)
//                R.id.profile -> replaceFragment(profileFragment)
//            }
//            true
//        }

//        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

//        moveToFragment(DashboardFragment())
    }

//    private fun moveToFragment(fragment: Fragment) {
//        val fragmentTrans = supportFragmentManager.beginTransaction()
//
//        fragmentTrans.replace(R.id.fragment_container, fragment)
//        fragmentTrans.commit()
//    }

//    private fun replaceFragment(fragment: Fragment) {
//        if (fragment != null) {
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.fragment_container, fragment)
//            transaction.commit()
//        }
//    }
}