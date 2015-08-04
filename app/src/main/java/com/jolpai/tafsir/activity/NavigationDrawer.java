package com.jolpai.tafsir.activity;


import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.adapter.ExpandListAdapter;
import com.jolpai.tafsir.adapter.NavigationAdapter;
import com.jolpai.tafsir.db.App;
import com.jolpai.tafsir.entity.About;
import com.jolpai.tafsir.entity.Audio;
import com.jolpai.tafsir.entity.Child;
import com.jolpai.tafsir.entity.Font;
import com.jolpai.tafsir.entity.Help;
import com.jolpai.tafsir.entity.Lang;
import com.jolpai.tafsir.entity.Parent;
import com.jolpai.tafsir.entity.Tafsir;
import com.jolpai.tafsir.entity.Translation;
import com.jolpai.tafsir.entity.Update;
import com.jolpai.tafsir.fragment.Verse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NavigationDrawer extends FragmentActivity implements Verse.OnFragmentInteractionListener {

    private DrawerLayout drawerLayout;
    private FrameLayout contentFrame;
    private ExpandableListView drawerList;
    private List<String> parentList ;
    private HashMap<String,List<String>> childMapingWithParent;
    private ExpandListAdapter adapter;

    private static final String STR_CHECKED = " has Checked!";
    private static final String STR_UNCHECKED = " has unChecked!";
    private int ParentClickStatus=-1;
    private int ChildClickStatus=-1;
    private ArrayList<Parent> parents;
    private Context context;
    private Fragment verse = new Verse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        contentFrame = (FrameLayout) findViewById(R.id.content_frame);
        drawerList = (ExpandableListView)findViewById(R.id.left_drawer);
        prepearingData();

        adapter = new ExpandListAdapter(NavigationDrawer.this,parentList, childMapingWithParent);
        View v=getLayoutInflater().inflate(R.layout.nv_header,null);


        drawerList.addHeaderView(v, null, false);
        drawerList.setAdapter(adapter);

        drawerList.setOnItemClickListener(new DrawerItemClickListener());

    }

    @Override
    protected void onResume() {
        super.onResume();



        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction ft =fragmentManager.beginTransaction();

          ft.replace(R.id.content_frame, verse);

        ft.commit();

    }


    private void loadHosts(final ArrayList<Parent> newParents)
    {
        if (newParents == null)
            return;

        parents = newParents;

        final NavigationAdapter adapter = new NavigationAdapter(NavigationDrawer.this,parents);

        final MyExpandableListAdapter mAdapter = new MyExpandableListAdapter();
        drawerList.setAdapter(adapter);

    }

    private ArrayList<Parent> buildDummyData()
    {
        // Creating ArrayList of type parent class to store parent class objects
        final ArrayList<Parent> list = new ArrayList<Parent>();
        for (int i = 1; i < 4; i++)
        {
            //Create parent class object
            final Parent parent = new Parent();

            // Set values in parent class object
            if(i==1){
                parent.setName("" + i);
                parent.setText1("Parent 0");
                parent.setText2("Disable App On \nBattery Low");
                parent.setChildren(new ArrayList<Child>());

                // Create Child class object
                final Child child = new Child();
                child.setName("" + i);
                child.setText1("Child 0");

                //Add Child class object to parent class object
                parent.getChildren().add(child);
            }
            else if(i==2){
                parent.setName("" + i);
                parent.setText1("Parent 1");
                parent.setText2("Auto disable/enable App \n at specified time");
                parent.setChildren(new ArrayList<Child>());

                final Child child = new Child();
                child.setName("" + i);
                child.setText1("Child 0");
                parent.getChildren().add(child);
                final Child child1 = new Child();
                child1.setName("" + i);
                child1.setText1("Child 1");
                parent.getChildren().add(child1);
            }
            else if(i==3){
                parent.setName("" + i);
                parent.setText1("Parent 1");
                parent.setText2("Show App Icon on \nnotification bar");
                parent.setChildren(new ArrayList<Child>());

                final Child child = new Child();
                child.setName("" + i);
                child.setText1("Child 0");
                parent.getChildren().add(child);
                final Child child1 = new Child();
                child1.setName("" + i);
                child1.setText1("Child 1");
                parent.getChildren().add(child1);
                final Child child2 = new Child();
                child2.setName("" + i);
                child2.setText1("Child 2");
                parent.getChildren().add(child2);
                final Child child3 = new Child();
                child3.setName("" + i);
                child3.setText1("Child 3");
                parent.getChildren().add(child3);
            }

            //Adding Parent class object to ArrayList
            list.add(parent);
        }
        return list;
    }


    protected  void setText(){

    }

    protected void prepearingData() {
        parentList = new ArrayList<String>();
        childMapingWithParent = new HashMap<String, List<String>>();

        // Adding child data
        parentList.add(new Tafsir().toString());
        parentList.add(new Lang().toString());
        parentList.add(new Translation().toString());
        parentList.add(new Font().toString());
        parentList.add(new Audio().toString());
        parentList.add(new Update().toString());
        parentList.add(new About().toString());
        parentList.add(new Help().toString());

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



        childMapingWithParent.put(parentList.get(0), Tafsir); // Header, Child data
        childMapingWithParent.put(parentList.get(1), Language);
        childMapingWithParent.put(parentList.get(2), Translation);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private class DrawerItemClickListener implements ListView. OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem( position);
        }
    }

    private void selectItem(int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction ft =fragmentManager.beginTransaction();

        //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch(position){
            case 0 :
                // ft.replace(R.id.order_navigation_frameLayout, orderGenerate);
                ft.replace(R.id.content_frame, verse);
                break;
            case 1 :
                ft.replace(R.id.content_frame, verse);
                //getSupportActionBar().setTitle("Order");
                break;

        }

        ft.commit();
        //drawerLayout.closeDrawer(drawerListView);
    }

    private class MyExpandableListAdapter extends BaseExpandableListAdapter
    {


        private LayoutInflater inflater;

        public MyExpandableListAdapter()
        {
            // Create Layout Inflator
            inflater = LayoutInflater.from(NavigationDrawer.this);
        }


        // This Function used to inflate parent rows view

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parentView)
        {
            final Parent parent = parents.get(groupPosition);

            // Inflate grouprow.xml file for parent rows
            convertView = inflater.inflate(R.layout.grouprow, parentView, false);

            // Get grouprow.xml file elements and set values
            ((TextView) convertView.findViewById(R.id.text1)).setText(parent.getText1());
            ((TextView) convertView.findViewById(R.id.text)).setText(parent.getText2());
            ImageView image=(ImageView)convertView.findViewById(R.id.image);

            image.setImageResource(
                    getResources().getIdentifier(
                            "com.androidexample.customexpandablelist:drawable/setting"+parent.getName(),null,null));

            ImageView rightcheck=(ImageView)convertView.findViewById(R.id.rightcheck);

            //Log.i("onCheckedChanged", "isChecked: "+parent.isChecked());

            // Change right check image on parent at runtime
            if(parent.isChecked()==true){
                rightcheck.setImageResource(
                        getResources().getIdentifier(
                                "com.androidexample.customexpandablelist:drawable/rightcheck",null,null));
            }
            else{
                rightcheck.setImageResource(
                        getResources().getIdentifier(
                                "com.androidexample.customexpandablelist:drawable/button_check",null,null));
            }

            // Get grouprow.xml file checkbox elements
            CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            checkbox.setChecked(parent.isChecked());

            // Set CheckUpdateListener for CheckBox (see below CheckUpdateListener class)
            checkbox.setOnCheckedChangeListener(new CheckUpdateListener(parent));

            return convertView;
        }


        // This Function used to inflate child rows view
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parentView)
        {
            final Parent parent = parents.get(groupPosition);
            final Child child = parent.getChildren().get(childPosition);

            // Inflate childrow.xml file for child rows
            convertView = inflater.inflate(R.layout.childrow, parentView, false);

            // Get childrow.xml file elements and set values
            ((TextView) convertView.findViewById(R.id.text1)).setText(child.getText1());
            ImageView image=(ImageView)convertView.findViewById(R.id.image);
            image.setImageResource(
                    getResources().getIdentifier(
                            "com.androidexample.customexpandablelist:drawable/setting"+parent.getName(),null,null));

            return convertView;
        }


        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            //Log.i("Childs", groupPosition+"=  getChild =="+childPosition);
            return parents.get(groupPosition).getChildren().get(childPosition);
        }

        //Call when child row clicked
        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            /****** When Child row clicked then this function call *******/

            //Log.i("Noise", "parent == "+groupPosition+"=  child : =="+childPosition);
            if( ChildClickStatus!=childPosition)
            {
                ChildClickStatus = childPosition;

                Toast.makeText(getApplicationContext(), "Parent :" + groupPosition + " Child :" + childPosition,
                        Toast.LENGTH_LONG).show();
            }

            return childPosition;
        }

        @Override
        public int getChildrenCount(int groupPosition)
        {
            int size=0;
            if(parents.get(groupPosition).getChildren()!=null)
                size = parents.get(groupPosition).getChildren().size();
            return size;
        }


        @Override
        public Object getGroup(int groupPosition)
        {
            Log.i("Parent", groupPosition + "=  getGroup ");

            return parents.get(groupPosition);
        }

        @Override
        public int getGroupCount()
        {
            return parents.size();
        }

        //Call when parent row clicked
        @Override
        public long getGroupId(int groupPosition)
        {
            Log.i("Parent", groupPosition+"=  getGroupId "+ParentClickStatus);

            if(groupPosition==2 && ParentClickStatus!=groupPosition){

                //Alert to user
                Toast.makeText(getApplicationContext(), "Parent :"+groupPosition ,
                        Toast.LENGTH_LONG).show();
            }

            ParentClickStatus=groupPosition;
            if(ParentClickStatus==0)
                ParentClickStatus=-1;

            return groupPosition;
        }

        @Override
        public void notifyDataSetChanged()
        {
            // Refresh List rows
            super.notifyDataSetChanged();
        }

        @Override
        public boolean isEmpty()
        {
            return ((parents == null) || parents.isEmpty());
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition)
        {
            return true;
        }

        @Override
        public boolean hasStableIds()
        {
            return true;
        }

        @Override
        public boolean areAllItemsEnabled()
        {
            return true;
        }



        /******************* Checkbox Checked Change Listener ********************/

        private final class CheckUpdateListener implements CompoundButton.OnCheckedChangeListener
        {
            private final Parent parent;

            private CheckUpdateListener(Parent parent)
            {
                this.parent = parent;
            }
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                Log.i("onCheckedChanged", "isChecked: " + isChecked);
                parent.setChecked(isChecked);

                //  ((MyExpandableListAdapter)getExpandableListAdapter()).notifyDataSetChanged();

                final Boolean checked = parent.isChecked();
                Toast.makeText(getApplicationContext(),
                        "Parent : "+parent.getName() + " " + (checked ? STR_CHECKED : STR_UNCHECKED),
                        Toast.LENGTH_LONG).show();
            }
        }
        /***********************************************************************/

    }

}
