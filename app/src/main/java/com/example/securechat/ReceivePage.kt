package com.example.securechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlin.math.abs

class ReceivePage : AppCompatActivity(), GestureDetector.OnGestureListener {
    lateinit var gestureDetector: GestureDetector

    var x2:Float = 0.0f
    var x1:Float = 0.0f

    companion object{
        const val MIN_DISTANCE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_page)

        gestureDetector = GestureDetector(this, this)
        val historyBtn=findViewById<Button>(R.id.show)
        historyBtn.setOnClickListener {
            val intent = Intent(this, HistoryPage::class.java)
            startActivity(intent)
        }



    }




    override fun onTouchEvent(event: MotionEvent?): Boolean {

        gestureDetector.onTouchEvent(event)

        when(event?.action){


            //At the starting of swipe
            0->
            {
                x1 = event.x
            }

            //At the end of swipe
            1->
            {
                x2 = event.x

                val valueX:Float = x2-x1

                if(abs(valueX) > MIN_DISTANCE){
                    //Condition for left swipe
                    if(x2 > x1){
                        startActivity(Intent(this, MessageSendActivity::class.java))
                        finish()
                        this.overridePendingTransition(R.anim.left_right,R.anim.right_left)

                    }
                }
            }
        }

        return super.onTouchEvent(event)
    }




    fun toSendPage(view: View){
        onBackPressed()
    }



    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(p0: MotionEvent?) {

    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {

    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }
}



