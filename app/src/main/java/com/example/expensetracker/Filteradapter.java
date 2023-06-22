package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Filteradapter extends RecyclerView.Adapter<Filteradapter.MyViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<Incomealltransactionmodel> incomealltransactionmodelArrayList;
    private Context c;
    String url = config.baseurl + "delete.php";
    String u_id,status,message;

    public Filteradapter(Context ctx,ArrayList<Incomealltransactionmodel> incomealltransactionmodelArrayList){
        c=ctx;
        inflater=LayoutInflater.from(ctx);
        this.incomealltransactionmodelArrayList=incomealltransactionmodelArrayList;
    }


    @NonNull
    @Override
    public Filteradapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.incomealltranslistimage,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;

    }
    @Override
    public void onBindViewHolder(@NonNull Filteradapter.MyViewHolder holder, int position) {
        holder.tt1.setText(incomealltransactionmodelArrayList.get(position).getPrice());
        holder.tt2.setText(incomealltransactionmodelArrayList.get(position).getCategory());
        holder.tt3.setText(incomealltransactionmodelArrayList.get(position).getDate());
        holder.tt4.setText(incomealltransactionmodelArrayList.get(position).getDiscription());
        holder.tt5.setText(incomealltransactionmodelArrayList.get(position).getPaymentmode());
        // Picasso.get().load(config.imgurl+incomealltransactionmodelArrayList.get(position).getImage()).into(holder.img11);


//        holder.tt6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deleting(incomealltransactionmodelArrayList.get(position).getId());
//            }
//        });

    }

//    private void deleting(String u_id) {
////        HashMap<String,String> map=new HashMap<>(new SessionManager(c).getUserDetails());
////        u_id=map.get("id");
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try {
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    status = jsonObject.getString("status");
//                    message = jsonObject.getString("message");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                if("1".equals(status)){
//                    Toast.makeText(c, "success", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(c, "failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(c, String.valueOf(error), Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("id", u_id);
//                return map;
//
//            }
//        };
//        RequestQueue requestQueue= Volley.newRequestQueue(c);
//        requestQueue.add(stringRequest);
//    }


    @Override
    public int getItemCount() {
        return incomealltransactionmodelArrayList.size();
    }

//    public void filterList(ArrayList<Incomealltransactionmodel> filteredSongs) {
//        this.incomealltransactionmodelArrayList=filteredSongs;
//        notifyDataSetChanged();
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tt1,tt2,tt3,tt4,tt5,tt6;
        ImageView img11;
        CardView view;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tt1=itemView.findViewById(R.id.text1);
            tt2=itemView.findViewById(R.id.text2);
            tt3=itemView.findViewById(R.id.text3);
            tt4=itemView.findViewById(R.id.text4);
            tt5=itemView.findViewById(R.id.text5);
            tt6=itemView.findViewById(R.id.text6);
//            img11=itemView.findViewById(R.id.image1);
            view=itemView.findViewById(R.id.view1);

        }
    }
}
