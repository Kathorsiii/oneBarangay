package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.fragments.DashboardFragment
import com.example.myapplication.fragments.NotificationsFragment
import com.example.myapplication.fragments.ProfileFragment
import com.example.myapplication.fragments.ServicesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    private val dashboardFragment = DashboardFragment()
//    private val servicesFragment = ServicesFragment()
//    private val notificationFragment = NotificationsFragment()
//    private val profileFragment = ProfileFragment()

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_dashboard -> {
                moveToFragment(DashboardFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_services -> {
                moveToFragment(ServicesFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_notification -> {
                moveToFragment(NotificationsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_profile -> {
                moveToFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        moveToFragment(DashboardFragment())

        // Announcement Module
//        val arrayList = ArrayList<Model>()
//
//        arrayList.add(Model("Announcement 1", "Mon, 05 July 2021", "09:00 - 11:00 AM", "Announcement 1 Description", R.drawable.dice))
//        arrayList.add(Model("Announcement 2", "Mon, 06 July 2021", "09:00 - 11:00 AM", "Announcement 2 Description", R.drawable.dice))
//        arrayList.add(Model("Announcement 3", "Mon, 07 July 2021", "09:00 - 11:00 AM", "Announcement 3 Description", R.drawable.dice))
//        arrayList.add(Model("Announcement 4", "Mon, 08 July 2021", "09:00 - 11:00 AM", "Announcement 4 Description", R.drawable.dice))
//        arrayList.add(Model("Announcement 5", "Mon, 09 July 2021", "09:00 - 11:00 AM", "Announcement 5 Description", R.drawable.dice))
//
//        val announcementAdapter = AnnouncementAdapter(arrayList, this)
//
//        rv_announcement.layoutManager = LinearLayoutManager(this)
//        rv_announcement.adapter = announcementAdapter

    }

    private fun moveToFragment(fragment: Fragment) {
        val fragmentTrans = supportFragmentManager.beginTransaction()

        fragmentTrans.replace(R.id.fragment_container, fragment)
        fragmentTrans.commit()
    }

//    private fun replaceFragment(fragment: Fragment) {
//        if (fragment != null) {
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.fragment_container, fragment)
//            transaction.commit()
//        }
//    }
}