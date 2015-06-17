package com.jolpai.tafsir.navigation_drawer;

import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.jolpai.tafsir.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NavigationDrawer extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private FrameLayout contentFrame;
    private ExpandableListView drawerList;
    private List<String> listDataHeader ;
    private HashMap<String,List<String>> listDataChild;
    private  ExpandListAdapter expandListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        contentFrame = (FrameLayout) findViewById(R.id.content_frame);
        drawerList = (ExpandableListView)findViewById(R.id.left_drawer);


    }

    @Override
    protected void onResume() {
        super.onResume();

        prepearingData();
        expandListAdapter = new ExpandListAdapter(NavigationDrawer.this,listDataHeader,listDataChild);
        drawerList.setAdapter(expandListAdapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());


    }

    protected  void setText(){

    }

    protected void prepearingData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Tafsir");
        listDataHeader.add("Language");
        listDataHeader.add("Translation");
        listDataHeader.add("Font");
        listDataHeader.add("Audio");
        listDataHeader.add("Update");
        listDataHeader.add("About");
        listDataHeader.add("Help");

        // Adding child data
        List<String> Tafsir = new ArrayList<String>();
        Tafsir.add("The Shawshank Redemption");
        Tafsir.add("The Godfather");
        Tafsir.add("The Godfather: Part II");


        List<String> Language = new ArrayList<String>();
        Language.add("The Conjuring");
        Language.add("Despicable Me 2");
        Language.add("Turbo");


        List<String> Translation = new ArrayList<String>();
        Translation.add("2 Guns");
        Translation.add("The Smurfs 2");
        Translation.add("The Spectacular Now");



        listDataChild.put(listDataHeader.get(0), Tafsir); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Language);
        listDataChild.put(listDataHeader.get(2), Translation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView. OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem( position);
        }
    }

    private void selectItem(int position) {
    }
}
