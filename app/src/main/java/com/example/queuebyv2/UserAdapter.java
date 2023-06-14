package com.example.queuebyv2;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    Context context;
    ArrayList<UserRequest> list;

    public UserAdapter(Context context, ArrayList<UserRequest> list) {
        this.context = context;
        this.list = list;
    }
    public void setData(ArrayList<UserRequest> newDataList) {
        if (newDataList == null) {
            return;
        } else {
            list.clear();
            list.addAll(newDataList);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserRequest user = list.get(position);
        holder.forms.setText(user.getCertificates());
        holder.status.setText(user.getStatus());
        holder.date.setText(user.getDate());
        holder.bind(user);

        holder.seedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(user);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView forms, status, date, seedetails, message;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            seedetails = itemView.findViewById(R.id.btnreqseedetails);
            forms = itemView.findViewById(R.id.forms);
            status = itemView.findViewById(R.id.status);
            date = itemView.findViewById(R.id.date);
            message = itemView.findViewById(R.id.message);

            seedetails.setPaintFlags(seedetails.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }

        public void bind(UserRequest user) {
            itemView.setTag(user);

            if (user.getStatus().equalsIgnoreCase("pending")) {
                message.setText("This request is currently in review. " +
                        "\nWe will notify you for an update.\n" +
                        "Thank you for your patience.");
            } else if (user.getStatus().equalsIgnoreCase("to receive")) {
                // Do something when the status is "to receive"
                message.setText("The requested document is now \navailable for pick-up. See details \nfor your scheduled date and time ");
            } else if (user.getStatus().equalsIgnoreCase("declined")) {
                // Do something when the status is "declined"
                message.setText("This request has been declined. \nPlease come at the barangay hall \nabout this matter. Thank you.");
            } else if (user.getStatus().equalsIgnoreCase("completed")) {
                // Do something when the status is "completed"
                message.setText("The requested document has \nbeen picked up. Thank you!");
            }
        }
    }
    private void showDialog(UserRequest user) {
        final Dialog dialog = new Dialog(context);
        String userStatus = user.getStatus();

        if (userStatus.equalsIgnoreCase("pending")) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.bottomsheetlayout);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            dialog.show();
        } else if (userStatus.equalsIgnoreCase("to receive")) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.receivebottomsheetlayout);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            dialog.show();
        } else if (userStatus.equalsIgnoreCase("completed")) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.completedbottomsheetlayout);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            dialog.show();
        }
        else if (userStatus.equalsIgnoreCase("declined")) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.declinedbottomsheetlayout);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            dialog.show();
        }


        TextView forms = dialog.findViewById(R.id.modalforms);
        TextView status = dialog.findViewById(R.id.modalstatus);
        TextView date = dialog.findViewById(R.id.modaldate);
        TextView modalfirstname = dialog.findViewById(R.id.modalfirstname);
        TextView modalmiddleinitial = dialog.findViewById(R.id.modalmiddleinitial);
        TextView modallastname = dialog.findViewById(R.id.modallastname);
        TextView modalpurpose = dialog.findViewById(R.id.modalpurpose);

        forms.setText(user.getCertificates());
        status.setText(userStatus);
        date.setText(user.getDate());
        modalfirstname.setText(user.getFirstname());
        modalmiddleinitial.setText(user.getMiddleinitial());
        modallastname.setText(user.getLastname());
        modalpurpose.setText(user.getPurpose());
    }
}


