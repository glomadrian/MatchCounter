package com.github.glomadrian.actionviewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ActionViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeFragment()
    }

    private fun initializeFragment() {
        val fragmentContainerId = android.R.id.content
        val fragment = ActionViewerFragment.newInstance()
        val fragmentTag = fragment.javaClass.name
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainerId, fragment, fragmentTag)
            .commitAllowingStateLoss()
    }
}