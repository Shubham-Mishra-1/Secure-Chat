package com.example.securechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.securechat.util.SharedPref
import com.google.firebase.database.*

class HistoryPage : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var chatArrayList: ArrayList<Chat>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_page)
        chatRecyclerView=findViewById(R.id.chat_list)
        chatRecyclerView.layoutManager=LinearLayoutManager(this)
        chatRecyclerView.setHasFixedSize(true)



        chatArrayList= arrayListOf<Chat>()
        getChatData()
    }

    private fun getChatData() {
        val result= SharedPref(this).getGroupname()
        val roomId=intent.getStringExtra("roomId")
        dbref=FirebaseDatabase.getInstance().getReference("Chats/$result")
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val chat=userSnapshot.getValue(Chat::class.java)
                        chatArrayList.add(chat!!)

                    }
                    chatRecyclerView.adapter=MyAdap(chatArrayList)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}