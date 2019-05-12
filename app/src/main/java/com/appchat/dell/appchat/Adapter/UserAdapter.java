package com.appchat.dell.appchat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.appchat.dell.appchat.Model.User;
import com.appchat.dell.appchat.R;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends BaseAdapter {

    List<User> userList = new ArrayList<>();
    LayoutInflater layoutInflater;
    Context context;

    public UserAdapter(Context context, List<User> userList){
        this.context = context;
        this.userList = userList;
        String name;
        layoutInflater = layoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.user_item, parent, false);
            holder.tvUsername = (TextView) convertView.findViewById(R.id.tvUser);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        User user = userList.get(position);
        String usename = user.getUsername();
        holder.tvUsername.setText(usename);

        return convertView;
    }
    class ViewHolder{
        TextView tvUsername;
    }
}
