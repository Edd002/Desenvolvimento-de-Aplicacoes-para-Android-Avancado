package com.example.app.trabalhofinal_worldwidepublicholiday.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.app.trabalhofinal_worldwidepublicholiday.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Definir a toolbar como support action bar
        setSupportActionBar(toolbar);

        // Centralizar título e subtítulo
        supportActionBar?.apply {
            title = getString(R.string.app_title);
            toolbarTitle.text = getString(R.string.app_title);
            toolbarSubTitle.text = getString(R.string.app_subtitle);
            this.elevation = 15F;
        }
    }
}