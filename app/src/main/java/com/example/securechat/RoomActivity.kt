package com.example.securechat

import android.content.Intent
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.example.securechat.util.SharedPref
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase




class RoomActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    lateinit var linearLayout:RelativeLayout
    lateinit var linearLayout1:LinearLayout
    lateinit var linearLayout2:LinearLayout
    lateinit var searchinggroup1:LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        val auth = FirebaseAuth.getInstance()
        val logout = findViewById<ImageView>(R.id.logoutImg)

        // Code Start Here
        show()
        logout.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)


        }

        findViewById<TextView>(R.id.user_textView).text = SharedPref(this).getUserName()


        val roomButton = findViewById<ImageButton>(R.id.to_send_message_button)
        val roomNo = findViewById<EditText>(R.id.group_key_edit_text)


        roomButton.setOnClickListener {
            roomButton.visibility = View.GONE
            hide()

            database = FirebaseDatabase.getInstance().getReference("Chats")
            database.get().addOnSuccessListener {
                val roomName = roomNo.text.toString()
                if (it.child(roomName).exists()) {

                    Handler().postDelayed({

                        Toast.makeText(this, "Room Found Successfully !", Toast.LENGTH_SHORT).show()
                        SharedPref(this).setGroupname(roomName)
                        launchChatWindow(roomName)
                        roomButton.visibility = View.VISIBLE
                        show()

                    }, 1000)

            }
                else {

                    Handler().postDelayed({
                        show()
                        roomButton.visibility = View.VISIBLE
                        Toast.makeText(this, "Room Doesn't Exist", Toast.LENGTH_SHORT).show()
                    }, 1000)



                }


                }
        }


    }




    fun hide(){
        linearLayout=findViewById(R.id.linear_layout1)
        linearLayout1=findViewById(R.id.relative_layout1)
        linearLayout2=findViewById(R.id.Linearlayout3)
        searchinggroup1=findViewById(R.id.searchinggroup)
        linearLayout.visibility = View.GONE
        linearLayout1.visibility = View.GONE
        linearLayout2.visibility = View.GONE
        searchinggroup1.visibility= View.VISIBLE
    }
    fun show(){
        linearLayout=findViewById(R.id.linear_layout1)
        linearLayout1=findViewById(R.id.relative_layout1)
        linearLayout2=findViewById(R.id.Linearlayout3)
        searchinggroup1=findViewById(R.id.searchinggroup)
        linearLayout.visibility = View.VISIBLE
        linearLayout1.visibility = View.VISIBLE
        linearLayout2.visibility = View.VISIBLE
        searchinggroup1.visibility= View.GONE
    }

    private fun launchChatWindow(roomNo: String) {
        val intent = Intent(this, MessageSendActivity::class.java)
        startActivity(intent)
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

    }

    fun toMessageSend(view: View){
        startActivity(Intent(this, MessageSendActivity::class.java))
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
    fun toCreateKey(view: View){
       startActivity(Intent(this,  CreateKey::class.java))
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

}