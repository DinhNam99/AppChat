package com.appchat.dell.appchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appchat.dell.appchat.Adapter.MessageAdapter;
import com.appchat.dell.appchat.Model.Chats;
import com.appchat.dell.appchat.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    TextView tvUsername;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    EditText edMess;
    ImageView igSend;

    Intent intent;
    RecyclerView recyclerView;
    List<Chats> chatsList;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //axa
        tvUsername = findViewById(R.id.tvUsename);
        edMess = findViewById(R.id.edMess);
        igSend = findViewById(R.id.igSend);

        //recycleview
        recyclerView = findViewById(R.id.rvMess);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        intent = getIntent();
        final String uid = intent.getStringExtra("Uid");
        //firebase
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        igSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = edMess.getText().toString();
                if(!msg.equals("")){
                    sendMessage(firebaseUser.getUid(),uid,msg);
                }else{
                    Toast.makeText(MessageActivity.this,"You can't send empty message!!!",Toast.LENGTH_LONG).show();
                }
                edMess.setText("");
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                tvUsername.setText(user.getUsername());
                readMessage(firebaseUser.getUid(),uid);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);

        reference.child("Chat").push().setValue(hashMap);
    }

    private void readMessage(final String idSender, final String idReceiver){
        chatsList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chat");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatsList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chats chats = snapshot.getValue(Chats.class);
                    if(chats.getSender().equals(idSender) && chats.getReceiver().equals(idReceiver) || chats.getSender().equals(idReceiver) && chats.getReceiver().equals(idSender)){
                        chatsList.add(chats);
                    }
                }
                messageAdapter = new MessageAdapter(MessageActivity.this,chatsList);
                recyclerView.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
