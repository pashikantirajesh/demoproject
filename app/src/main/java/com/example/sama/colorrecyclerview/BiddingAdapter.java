package com.example.sama.colorrecyclerview;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class BiddingAdapter extends RecyclerView.Adapter<BiddingAdapter.Myviewholder> {

    Context context;
    Myviewholder myviewholder;


    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlay, parent, false);
        return new Myviewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, final int position) {

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {getco(),getco()});
        gd.setCornerRadius(0f);
holder.tg.setText("Number "+position);
        holder.layput.setBackgroundDrawable(gd);




    }
public  int getco(){
    Random rnd = new Random();
    int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

return  color;
}

    @Override
    public int getItemCount() {
        return 100;
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
RelativeLayout layput;
TextView tg;
        public Myviewholder(View itemView) {
            super(itemView);

//            editText_branch=itemView.findViewById(R.id.editText_branch);
            layput=itemView.findViewById(R.id.rowlayy);
tg=itemView.findViewById(R.id.text);

//



        }
    }


}
