package com.jolpai.tafsir.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import net.ieclbd.finance.R;
import net.ieclbd.finance.communication.CommiunicationTask;
import net.ieclbd.finance.communication.Request;
import net.ieclbd.finance.communication.Response;
import net.ieclbd.finance.communication.Utility;
import net.ieclbd.finance.custom.listener.OnCompleteListener;
import net.ieclbd.finance.custom.view.MyDialog;
import net.ieclbd.finance.custom.view.MyToast;

import java.util.HashMap;

public class DashBord extends Activity implements View.OnClickListener ,OnCompleteListener{

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashbord);
        context=DashBord.this;

        View v = findViewById(R.id.llSignIn);
        v.setOnClickListener(this);

        v = findViewById(R.id.btnCardLon);
        v.setOnClickListener(this);

        v = findViewById(R.id.llReg);
        v.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
           case R.id.btnCardLon:
                intent = new Intent(this,FormCardLoanBOAccount.class);
               this.startActivity(intent);
               break;
           case R.id.llSignIn:
                intent = new Intent(this,Home.class);
               this.startActivity(intent);
               break;

            case R.id.llReg:

                if(Utility.isConnectedToInternet(this))
                {
                    Request request =new Request("GET_USER");

                    CommiunicationTask commiunicationTask=new CommiunicationTask(this, request,"retrieving data","retrieving data");
                    commiunicationTask.setOnCompleteListener(this);
                    commiunicationTask.execute();
                }
                else
                {
                    Utility.openInternetSettingsActivity(this);
                }

                break;
       }



    }

    @Override
    public void onComplete(Message msg) {
        if (msg.getData().containsKey("ERROR")) {
            String errorMsg = (String) msg.getData().getSerializable("ERROR");

            HashMap<Integer, Object> buttonMap = new HashMap<Integer, Object>();
            buttonMap.put(2, R.string.btn_cancel);
            MyDialog dialog = new MyDialog(this,"ERROR TITLE", errorMsg, Color.RED,R.drawable.information, buttonMap);
            dialog.show();

        } else {
            Response response = (Response) msg.getData().getSerializable("DATA");
            if(response.getStatus().equals("00")){
                MyToast.show(this,response.getErrorMsg(),null,R.drawable.my_toast_red, Toast.LENGTH_SHORT);
            }else{
                MyToast.show(this,response.getData().toString(),null,R.drawable.my_toast_blue, Toast.LENGTH_SHORT);
            }
        }
    }
}
