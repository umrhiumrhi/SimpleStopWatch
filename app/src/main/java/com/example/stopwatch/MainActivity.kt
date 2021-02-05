package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var time = 0
    private var flag = 0
    private var timerTask : Timer? = null
    private var isRunning = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // 액티비티에서 구현하면 안 된다고 하니 나중에 알아봅시다.

        val start_btn = findViewById<FloatingActionButton>(R.id.start_btn)
        val reset_btn = findViewById<FloatingActionButton>(R.id.reset_btn)
        val minute = findViewById<TextView>(R.id.minute)
        val hour = findViewById<TextView>(R.id.hour)
        val second = findViewById<TextView>(R.id.second)
        val layout = findViewById<LinearLayout>(R.id.lap_layout)

        start_btn.setOnClickListener {
            start_btn.setImageResource(android.R.drawable.ic_media_pause)

            if (flag == 0) {
                reset_btn.visibility = View.VISIBLE
                reset_btn.setImageResource(android.R.drawable.ic_menu_edit)
                flag = 1
                timerTask = timer(period = 10) {
                    time++
                    val min = time / 6000
                    val sec = (time / 100) % 60
                    val milli = time % 100

                    runOnUiThread {
                        fun change(time : Int) : String {
                            return if (time/10 == 0) {
                                "0$time"
                            } else {
                                time.toString()
                            }
                        }
                        minute.text = change(sec)
                        hour.text = change(min)
                        second.text = change(milli)
                    }
                }
            } else {
                start_btn.setImageResource(android.R.drawable.ic_media_play)
                reset_btn.setImageResource(android.R.drawable.ic_delete)
                timerTask?.cancel()
                flag = 0
            }
        }

        reset_btn.setOnClickListener {
            if (flag == 1) {
                // Toast.makeText(this, "${hour.text} : ${minute.text} : ${second.text} recorded", Toast.LENGTH_SHORT).show()
                val textView : TextView = TextView(this).apply {
                    setTextSize(30f)
                    text = "${hour.text} : ${minute.text} : ${second.text}"
                }
                layout.addView(textView, 0)

            } else {
                time = 0
                layout.removeAllViews()
                reset_btn.visibility = View.GONE
                minute.text = "00"
                hour.text = "00"
                second.text = "00"
            }
        }

    }
}