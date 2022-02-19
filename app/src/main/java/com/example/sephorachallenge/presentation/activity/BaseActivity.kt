package com.example.sephorachallenge.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import com.example.sephorachallenge.R
import com.example.sephorachallenge.presentation.fragment.BaseFragment

abstract class BaseActivity : AppCompatActivity() {

    protected var fragmentContainer = 0

    open fun replaceFragment(fragment: BaseFragment, withBackStack: Boolean) {
        addFragment(fragment, withBackStack, fragment.fragmentTag)
    }

    open fun addFragment(fragment: BaseFragment, withBackStack: Boolean, tag: String?) {
        val transaction = supportFragmentManager.beginTransaction()
        if (withBackStack) {
            transaction.setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.fade_out
            )
            transaction.addToBackStack(null)
        }
        transaction.replace(fragmentContainer, fragment, tag)
        transaction.commit()
    }
}