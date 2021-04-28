package com.example.netsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.netsample.ui.main.ObservableFragment

class ObservableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.observable_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ObservableFragment.newInstance())
                    .commitNow()
        }
    }
}