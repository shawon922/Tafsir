package com.jolpai.tafsir.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jolpai.tafsir.R;
import com.jolpai.tafsir.adapter.VerseAdapter;
import com.jolpai.tafsir.custom.view.R2L;
import com.jolpai.tafsir.db.App;
import com.jolpai.tafsir.entity.Global;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Verse.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Verse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Verse extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listVerseArabic;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Verse.
     */
    // TODO: Rename and change types and number of parameters
    public static Verse newInstance(String param1, String param2) {
        Verse fragment = new Verse();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Verse() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_verse, container, false);



        listVerseArabic = (ListView)v.findViewById(R.id.listviewVerse);
        VerseAdapter adapter = new VerseAdapter( getActivity(), Global.getVerseList());
        listVerseArabic.setAdapter(adapter);


       /*Typeface  tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/TRADO.TTF");
        ArrayList<String> list=Global.getVerseList();

        for(int j=0;j<list.size();j++){
            LinearLayout ll = (LinearLayout)v.findViewById(R.id.llVerseView);
            String arabic = list.get(j);
            String [] arrayWhitespace = arabic.split("\\s+");
            View rowView = inflater.inflate(R.layout.row_verse_arabic, null, false);
            R2L customLayout = (R2L) rowView.findViewById(R.id.cl_R2L);
            for (int i=0; i<arrayWhitespace.length; i++) {
                View normalView =View.inflate(getActivity(), R.layout.normal_view, null);
                View emptyView =View.inflate(getActivity(), R.layout.normal_empty_view, null);

                TextView tv = (TextView)normalView.findViewById(R.id.textView);
                TextView tv2 = (TextView)emptyView.findViewById(R.id.textView2);

                tv.setTypeface(tf, Typeface.NORMAL);
                tv.setTextSize(30);
                tv.setTextColor(Color.GRAY);
                tv.setTypeface(tf, Typeface.NORMAL);
                tv.setText(arrayWhitespace[i].toString());
                tv2.setText(" ");
                tv2.setTextSize(30);

                customLayout.addView(normalView);
                customLayout.addView(emptyView);
            }

            ll.addView(rowView);

        }
*/



        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
