package com.example.dipstore.navigationView

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.dipstore.InternalStorageFragment
import com.example.dipstore.R
import com.example.dipstore.databinding.ActivityDrawerLayoutBinding
import com.example.dipstore.forgotpassword.EmailFragment
import com.example.dipstore.forgotpassword.PhoneFragment
import com.example.dipstore.home.HomeFragment

class DrawerLayoutActivity : AppCompatActivity() {
    lateinit var binding: ActivityDrawerLayoutBinding
    private var fragment : Fragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawerLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
    }

    private fun setData() {
        val actionBarToggle = ActionBarDrawerToggle(this, binding.drawerLayout, 0, 0)
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragmentContainer, PhoneFragment()).hide(HomeFragment())
        }.commit()

        binding.drawerLayout.addDrawerListener(actionBarToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarToggle.syncState()

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.myProfile -> {
                    Toast.makeText(this, getString(R.string.txt_my_profile_), Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction().hide(fragment).show(EmailFragment()).commit()
                    fragment = EmailFragment()
                }
                R.id.people -> {
                    supportFragmentManager.beginTransaction().hide(fragment).show(AppBarFragment()).commit()
                    fragment = AppBarFragment()
                    Toast.makeText(this, getString(R.string.txt_people), Toast.LENGTH_SHORT).show()
                }
                R.id.settings -> {
                    supportFragmentManager.beginTransaction().hide(fragment).show(PhoneFragment()).commit()
                    fragment = PhoneFragment()
                    Toast.makeText(this, getString(R.string.txt_settings), Toast.LENGTH_SHORT).show()
                }
                R.id.share -> {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    val body = getString(R.string.txt_share_body)
                    val sub = getString(R.string.txt_share_sub)
                    intent.putExtra(Intent.EXTRA_SUBJECT,sub)
                    intent.putExtra(Intent.EXTRA_TEXT,body)
                    startActivity(Intent.createChooser(intent, getString(R.string.txt_share_title)))
                }
                R.id.send -> {
                    supportFragmentManager.beginTransaction().hide(fragment).show(InternalStorageFragment()).commit()
                    fragment = InternalStorageFragment()
                    Toast.makeText(this, getString(R.string.txt_send), Toast.LENGTH_SHORT).show()
                }
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).addToBackStack(null).commit()
            closeDrawer()
            true
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer()
        } else {
            binding.drawerLayout.openDrawer(binding.navView)
        }
        return true
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer()
        } else {
            super.onBackPressed()
        }
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawer(binding.navView)
    }

}