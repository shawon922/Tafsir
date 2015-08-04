package com.jolpai.tafsir.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.jolpai.tafsir.R;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    Context context;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* if(Utility.isConnectedToInternet(this))
        {
            Request request =new Request("GET_USER");

            CommiunicationTask commiunicationTask=new CommiunicationTask(this, request,"retrieving data","retrieving data");
            commiunicationTask.setOnCompleteListener(this);
            commiunicationTask.execute();
        }
        else
        {
            Utility.openInternetSettingsActivity(this);
        }*/


    }


    @Override
    protected void onResume() {
        super.onResume();

        setText();
    }

    /*@Override
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
    }*/

    private void setText() {

    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}


