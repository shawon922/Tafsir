package com.jolpai.tafsir.custom.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jolpai.tafsir.R;

import static android.view.Gravity.CENTER_HORIZONTAL;
import static android.view.Gravity.CENTER_VERTICAL;

public class MyToast {

   /* public static void show(Context activity,Object text, Integer imgId ,Integer bacground ,int duration){
        LayoutInflater inflater=((Activity)activity).getLayoutInflater();
        View customToastroot =inflater.inflate(R.layout.my_toast, null);

        LinearLayout linearLayout =(LinearLayout)customToastroot.findViewById(R.id.toast_background);
        if(bacground==null){
            linearLayout.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.my_toast_yellow));
        }else{
            linearLayout.setBackgroundDrawable(activity.getResources().getDrawable(bacground));
        }


        ImageView img =(ImageView)customToastroot.findViewById(R.id.toast_img);
        if(imgId==null){
            img.setImageResource(R.drawable.yellow_warning);
        }else{
            img.setImageResource(imgId);
        }



        TextView txtView =(TextView)customToastroot.findViewById(R.id.toast_txt);
        if(text instanceof String)
            txtView.setText((String)text);
        else if(text instanceof Integer)
            txtView.setText((Integer)text);

        Toast customtoast=new Toast(activity);
        customtoast.setView(customToastroot);
        customtoast.setGravity(CENTER_HORIZONTAL | CENTER_VERTICAL,0, 250);
        customtoast.setDuration(duration);
        customtoast.show();
    }
    */

   /* public static void show(Context activity,Object text,int duration){
        show(activity, text, null, null, duration);
    }
    public static void show(Context activity,Object text){
        show(activity, text, null, null, Toast.LENGTH_SHORT);
    }*/

}
