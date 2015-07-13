package com.jolpai.tafsir.navigation_drawer;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.jolpai.tafsir.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Tanim Reja on 5/1/2015.
 */
public class ExpandListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List <String> listHeaderData;
    private HashMap<String,List<String>> listChildData;


    /**
     * Constructor with 3 param
     * @param context activity context.
     * @param listHeaderData  that's group data of ExpandableListView. its a string type List.
     * @param listChildData  its child data that's mapped with group data of ExpandableListView. its a HashMap.
     */
    public ExpandListAdapter(Context context,List<String> listHeaderData,HashMap<String,List<String>> listChildData){

        this.context=context;
        this.listHeaderData=listHeaderData;
        this.listChildData=listChildData;
    }


    /**
     * Gets the number of groups.
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {

        return this.listHeaderData.size();
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
        return this.listChildData.get(this.listHeaderData.get(groupPosition)).size();
    }

    /**
     * Gets the data associated with the given group.
     *
     * @param groupPosition the position of the group
     * @return the data child for the specified group
     */
    @Override
    public Object getGroup(int groupPosition) {
        return this.listHeaderData.get(groupPosition);
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
        return this.listChildData.get(this.listHeaderData.get(groupPosition)).get(childPosition);
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
     * Indicates whether the child and group IDs are stable across changes to the
     * underlying data.
     *
     * @return whether or not the same ID always refers to the same object
     * @see Adapter#hasStableIds()
     */
    @Override
    public boolean hasStableIds() {
        return false;
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

        if(groupPosition == 0){
            String groupTitle = (String)getGroup(groupPosition);
            if(convertView == null){
                LayoutInflater inflater =(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.nv_row,null);
            }
            TextView txtViewHeader = (TextView)convertView.findViewById(R.id.txtGroup);
            txtViewHeader.setText(groupTitle);
            txtViewHeader.setTypeface(Typeface.SANS_SERIF);
            txtViewHeader.setTextSize(20);


        }else if(groupPosition==1){
            String groupTitle = (String)getGroup(groupPosition);
            if(convertView == null){
                LayoutInflater inflater =(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.nv_row,null);
            }
            TextView txtViewHeader = (TextView)convertView.findViewById(R.id.txtGroup);
            txtViewHeader.setText(groupTitle);
            txtViewHeader.setTypeface(Typeface.SANS_SERIF);
            txtViewHeader.setTextSize(20);

        }


        String groupTitle = (String)getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater =(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.nv_row,null);
        }
        TextView txtViewHeader = (TextView)convertView.findViewById(R.id.txtGroup);
        txtViewHeader.setText(groupTitle);
        txtViewHeader.setTypeface(Typeface.SANS_SERIF);
        txtViewHeader.setTextSize(20);
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

    final String childTxt = (String)getChild(groupPosition,childPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.nv_child,null);
        }
        TextView txtViewChild =(TextView)convertView.findViewById(R.id.txtChild);
        txtViewChild.setText(childTxt);
        txtViewChild.setTypeface(Typeface.SANS_SERIF);
        txtViewChild.setTextSize(15);

        return convertView;
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
}
