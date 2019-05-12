package com.appchat.dell.appchat.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appchat.dell.appchat.MessageActivity;
import com.appchat.dell.appchat.Model.Chats;
import com.appchat.dell.appchat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    public static final int TYPE_SENDER = 0;
    public static final int TYPE_RECEIVER = 1;

    List<Chats> chatsList;
    Context context;

    FirebaseUser firebaseUser;
    public MessageAdapter (Context context, List<Chats> chatsList){
        this.context = context;
        this.chatsList = chatsList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_SENDER){
            View view = LayoutInflater.from(context).inflate(R.layout.chat_sender_item, parent, false);
            return new MessageAdapter.MessageViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_reciever_item, parent, false);
            return new MessageAdapter.MessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Chats chats = chatsList.get(position);
        holder.tvMess.setText(chats.getMessage());
    }

    @Override
    public int getItemCount() {
        return chatsList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMess;
        public MessageViewHolder(View itemView) {
            super(itemView);
            tvMess = (TextView) itemView.findViewById(R.id.tvMess);
        }
    }
    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chatsList.get(position).getSender().equals(firebaseUser.getUid())){
            return TYPE_SENDER;
        }else{
            return TYPE_RECEIVER;
        }
    }
}
