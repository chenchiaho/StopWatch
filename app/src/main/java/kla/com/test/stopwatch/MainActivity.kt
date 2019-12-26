package kla.com.test.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.os.postDelayed
import java.util.*



class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private var seconds = 0
    private var running:Boolean = false
    private var wasRunning:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate starts")

        if(savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
            wasRunning = savedInstanceState.getBoolean("wasRunning")
            Log.d(TAG, "saveInstanceState called")
        }

        runTimer()
        Log.d(TAG, "runTimer starts")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("seconds", seconds)
        outState.putBoolean("running", running)
        outState.putBoolean("wasRunning", wasRunning)
        Log.d(TAG, "data saves to Bundle")
    }

    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
        Log.d(TAG, "onPause called")
    }

    override fun onResume() {
        super.onResume()
        if(wasRunning) {
            running = true
        }
        Log.d(TAG, "onResume called")
    }

    fun onClickStart(view: View) {
        running = true
        Log.d(TAG, "onClickStart called")
    }

    fun onClickStop(view: View) {
        running = false
        Log.d(TAG, "onStop called")
    }

    fun onClickReset(view: View) {
        running = false
        seconds = 0
        Log.d(TAG, "onClickReset called")
    }

    private fun runTimer() {
        val timeView = findViewById(R.id.time_view) as TextView
        val handler: Handler = Handler()
        handler.post(object:Runnable {
            public override fun run() {
                var hours = seconds / 3600
                var minutes = (seconds % 3600) / 60
                var secs = seconds % 60
                var time: String =
                    String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs)
                timeView.setText(time)
                if (running) {
                    seconds++
                }
                handler.postDelayed(this, 1000)
            }

        }
        )
        Log.d(TAG, "runTimer complete")
    }

}
