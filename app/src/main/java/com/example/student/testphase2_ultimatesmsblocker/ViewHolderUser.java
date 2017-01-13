package com.example.student.testphase2_ultimatesmsblocker;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class ViewHolderUser extends RecyclerView.ViewHolder {

    private TextView id, name, password, email, contact, usertype, regDate, modfDate;

    ViewHolderUser(View v) {
        super(v);
        id = (TextView) itemView.findViewById(R.id.id);
        name = (TextView) itemView.findViewById(R.id.name);
        password = (TextView) itemView.findViewById(R.id.password);
        email = (TextView) itemView.findViewById(R.id.email);
        contact = (TextView) itemView.findViewById(R.id.contact);
        usertype = (TextView) itemView.findViewById(R.id.usertype);
        regDate = (TextView) itemView.findViewById(R.id.regDate);
        modfDate = (TextView) itemView.findViewById(R.id.modifydate);
    }

    TextView getId() {
        return id;
    }

    TextView getName() {
        return name;
    }

    TextView getPassword() {
        return password;
    }

    TextView getEmail() {
        return email;
    }
    TextView getContact() {
        return contact;
    }

    TextView getUsertype() {
        return usertype;
    }

    TextView getRegDate() {
        return regDate;
    }

    TextView getModfDate() {
        return modfDate;
    }
}
