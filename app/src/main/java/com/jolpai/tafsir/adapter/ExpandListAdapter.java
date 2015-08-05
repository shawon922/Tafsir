package com.jolpai.tafsir.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.entity.Audio;
import com.jolpai.tafsir.entity.Font;
import com.jolpai.tafsir.entity.Lang;
import com.jolpai.tafsir.entity.Tafsir;
import com.jolpai.tafsir.entity.Translation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tanim Reja on 5/1/2015.
 */
public class ExpandListAdapter extends BaseExpandableListAdapter implements View.OnClickListener{

    private Context context;
    private List <String> parentList;
    private HashMap<String,ArrayList<?>> childMapingWithParent;


    /**
     * Constructor with 3 param
     * @param context activity context.
     * @param parentList  that's group data of ExpandableListView. its a string type List.
     * @param childMapingWithParent  its child data that's mapped with group data of ExpandableListView. its a HashMap.
     */
    public ExpandListAdapter(Context context,List<String> parentList,HashMap<String,ArrayList<?>> childMapingWithParent){

        this.context=context;
        this.parentList = parentList;
        this.childMapingWithParent = childMapingWithParent;
    }


    /**
     * Gets a View that displays the given group. This View is only for the
     * group--the Views for the group's children will be fetched using
     * {@link #getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)}.
     *
     * @param groupPosition the position of the group for which the View is
     *                      returned
     * @param isExpanded    whether the group is expanded or collapsed
     * @param convertView   the old view to reuse, if possible. You should check
     *                      that this view is non-null and of an appropriate type before
     *                      using. If it is not possible to convert this view to display
     *                      the correct data, this method can create a new view. It is not
     *                      guaranteed that the convertView will have been previously
     *                      created by
     *                      {@link #getGroupView(int, boolean, android.view.View, android.view.ViewGroup)}.
     * @param parent        the parent that this view will eventually be attached to
     * @return the View corresponding to the group at the specified position
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

       String groupTitle = (String)getGroup(groupPosition);

        if(groupPosition == 0){
            LayoutInflater inflater =(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.nv_parent_tafsir,parent,false);
            TextView txtViewHeader = (TextView)convertView.findViewById(R.id.txtNV_rowTitle);
            txtViewHeader.setText(groupTitle);
            txtViewHeader.setTypeface(Typeface.SANS_SERIF);
            txtViewHeader.setTextSize(20);


        }else if (groupPosition == 1){
            LayoutInflater inflater =(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.nv_parent_tafsir,parent,false);
            TextView txtViewHeader = (TextView)convertView.findViewById(R.id.txtNV_rowTitle);
            txtViewHeader.setText(groupTitle);
            txtViewHeader.setTypeface(Typeface.SANS_SERIF);
            txtViewHeader.setTextSize(20);

        }else if (groupPosition == 2){

            LayoutInflater inflater =(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.nv_parent_lang,parent,false);
            TextView txtViewHeader = (TextView)convertView.findViewById(R.id.txtNV_rowTitle);
            txtViewHeader.setText(groupTitle);
            txtViewHeader.setTypeface(Typeface.SANS_SERIF);
            txtViewHeader.setTextSize(20);
        }else if (groupPosition == 3){
            LayoutInflater inflater =(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.nv_parent_font,parent,false);
            TextView txtViewHeader = (TextView)convertView.findViewById(R.id.txtNV_rowTitle);
            txtViewHeader.setText(groupTitle);
            txtViewHeader.setTypeface(Typeface.SANS_SERIF);
            txtViewHeader.setTextSize(20);

        }else if (groupPosition == 4){
            LayoutInflater inflater =(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.nv_parent_audio,parent,false);
            TextView txtViewHeader = (TextView)convertView.findViewById(R.id.txtNV_rowTitle);
            txtViewHeader.setText(groupTitle);
            txtViewHeader.setTypeface(Typeface.SANS_SERIF);
            txtViewHeader.setTextSize(20);

        }


        //  convertView.setOnClickListener(this);



        return convertView;

    }

    /**
     * Gets a View that displays the data for the given child within the given
     * group.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child (for which the View is
     *                      returned) within the group
     * @param isLastChild   Whether the child is the last child within the group
     * @param convertView   the old view to reuse, if possible. You should check
     *                      that this view is non-null and of an appropriate type before
     *                      using. If it is not possible to convert this view to display
     *                      the correct data, this method can create a new view. It is not
     *                      guaranteed that the convertView will have been previously
     *                      created by
     *                      {@link #getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)}.
     * @param parent        the parent that this view will eventually be attached to
     * @return the View corresponding to the child at the specified position
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if(groupPosition==0){
            final Tafsir tafsir = (Tafsir)getChild(groupPosition, childPosition);
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.nv_child,null);
            }
            TextView txtViewChild =(TextView)convertView.findViewById(R.id.txtChild);
            txtViewChild.setText(tafsir.getName());
            txtViewChild.setTypeface(Typeface.SANS_SERIF);
            txtViewChild.setTextSize(15);
            convertView.setTag(tafsir);

        }else if(groupPosition==1){
            final Lang tafsir = (Lang)getChild(groupPosition, childPosition);
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.nv_child,null);
            }
            TextView txtViewChild =(TextView)convertView.findViewById(R.id.txtChild);
            txtViewChild.setText(tafsir.getName());
            txtViewChild.setTypeface(Typeface.SANS_SERIF);
            txtViewChild.setTextSize(15);

        }else if (groupPosition==2){
            final Translation tafsir = (Translation)getChild(groupPosition, childPosition);
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.nv_child,null);
            }
            TextView txtViewChild =(TextView)convertView.findViewById(R.id.txtChild);
            txtViewChild.setText(tafsir.getName());
            txtViewChild.setTypeface(Typeface.SANS_SERIF);
            txtViewChild.setTextSize(15);

        }else if(groupPosition==3){
            final Font tafsir = (Font)getChild(groupPosition, childPosition);
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.nv_child,null);
            }
            TextView txtViewChild =(TextView)convertView.findViewById(R.id.txtChild);
            txtViewChild.setText(tafsir.getName());
            txtViewChild.setTypeface(Typeface.SANS_SERIF);
            txtViewChild.setTextSize(15);

        }else if(groupPosition==4){
            final Audio tafsir = (Audio)getChild(groupPosition, childPosition);
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.nv_child,null);
            }
            TextView txtViewChild =(TextView)convertView.findViewById(R.id.txtChild);
            txtViewChild.setText(tafsir.getName());
            txtViewChild.setTypeface(Typeface.SANS_SERIF);
            txtViewChild.setTextSize(15);


        }





        convertView.setOnClickListener( this);
        return convertView;
    }



    /**
     * Gets the data associated with the given child within the given group.
     *
     * @param groupPosition the position of the group that the child resides in
     * @param childPosition the position of the child with respect to other
     *                      children in the group
     * @return the data of the child
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.childMapingWithParent.get(this.parentList.get(groupPosition)).get(childPosition);
    }
    /**
     * Gets the ID for the given child within the given group. This ID must be
     * unique across all children within the group. The combined ID (see
     * {@link #getCombinedChildId(long, long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group for which
     *                      the ID is wanted
     * @return the ID associated with the child
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return  childPosition;
    }

    /**
     * Gets the number of children in a specified group.
     *
     * @param groupPosition the position of the group for which the children
     *                      count should be returned
     * @return the children count in the specified group
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childMapingWithParent.get(this.parentList.get(groupPosition)).size();
    }


    /**
     * Gets the data associated with the given group.
     *
     * @param groupPosition the position of the group
     * @return the data child for the specified group
     */
    @Override
    public Object getGroup(int groupPosition) {
        return this.parentList.get(groupPosition);
    }

    /**
     * Gets the number of groups.
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {

        return this.parentList.size();
    }

    /**
     * Gets the ID for the group at the given position. This group ID must be
     * unique across groups. The combined ID (see
     * {@link #getCombinedGroupId(long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group for which the ID is wanted
     * @return the ID associated with the group
     */
    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    public void notifyDataSetChanged()
    {
        // Refresh List rows
        super.notifyDataSetChanged();
    }

    /**
     * Indicates whether the child and group IDs are stable across changes to the
     * underlying data.
     *
     * @return whether or not the same ID always refers to the same object

     */
    @Override
    public boolean hasStableIds() {
        return true;
    }


    /**
     * Whether the child at the specified position is selectable.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group
     * @return whether the child is selectable.
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



    @Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }

    @Override
    public void onClick(View v ) {

        Toast.makeText(context,"clicked",Toast.LENGTH_SHORT).show();

    }
}
