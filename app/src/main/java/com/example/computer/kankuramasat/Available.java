package com.example.computer.kankuramasat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Available extends AppCompatActivity {
    private RecyclerView FindFriendsRecyclerList;
    private DatabaseReference UserRef;
    private FirebaseAuth mAuth;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available);
        mAuth = FirebaseAuth.getInstance();
        userid = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Info");
        // retriveid = UserRef.push().getKey();
        FindFriendsRecyclerList = (RecyclerView)findViewById(R.id.recycle);
        FindFriendsRecyclerList.setLayoutManager(new LinearLayoutManager(this));
    }
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Info> options = new FirebaseRecyclerOptions.Builder<Info>().setQuery(UserRef,Info.class).build();
        FirebaseRecyclerAdapter<Info,InfoViewHolder> adapter = new FirebaseRecyclerAdapter<Info, InfoViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull InfoViewHolder holder, final int position, @NonNull Info model) {
                holder.userName.setText(model.getName());
                holder.usermessage.setText(model.getMessage());
                holder.useemail.setText(model.getEmail());
            }
            @NonNull
            @Override
            public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_info_layout,viewGroup,false);
                InfoViewHolder viewHolder = new InfoViewHolder(view);
                return viewHolder;
            }
        };
        FindFriendsRecyclerList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class InfoViewHolder extends RecyclerView.ViewHolder
    {
        TextView userName,usermessage,useemail;
        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_profile_name);
            useemail = itemView.findViewById(R.id.email);
            usermessage = itemView.findViewById(R.id.message);
        }

    }
}
