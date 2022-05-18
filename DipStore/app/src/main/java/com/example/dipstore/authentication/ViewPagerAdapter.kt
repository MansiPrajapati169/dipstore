package com.example.dipstore.authentication

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dipstore.forgotpassword.EmailFragment
import com.example.dipstore.forgotpassword.ForgotPasswordActivity
import com.example.dipstore.forgotpassword.PhoneFragment

class ViewPagerAdapter(activity: ForgotPasswordActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            1 -> return PhoneFragment()
        }
        return EmailFragment()
    }
}