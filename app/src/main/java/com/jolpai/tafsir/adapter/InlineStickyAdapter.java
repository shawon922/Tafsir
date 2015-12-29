package com.jolpai.tafsir.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.adapter.holder.VerseItemHeaderViewHolder;
import com.jolpai.tafsir.adapter.holder.VerseItemViewHolder;
import com.jolpai.tafsir.adapter.sticky.StickyHeaderAdapter;
import com.jolpai.tafsir.adapter.holder.SurahHeaderHolder;
import com.jolpai.tafsir.model.Global;
import com.jolpai.tafsir.model.Verse;
import com.jolpai.tafsir.model.VerseArabic;
import com.jolpai.tafsir.model.VerseTrans;


import java.util.List;

/**
 * Created by Tanim reja on 12/15/2015.//InlineStickyTestAdapter.ItemHolder
 */
public class InlineStickyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
                                     implements StickyHeaderAdapter<VerseItemHeaderViewHolder> {


    private LayoutInflater mInflater;
    private Context context;
    private List<Verse> verseArabicList;
    private List<Verse> verseTransList;
    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    private Toolbar mToolbar;


    public InlineStickyAdapter(Context context,
                               List<Verse> verseArabicList,
                               List<Verse> verseTransList,
                               Toolbar mToolbar){
        this.context=context;
        this.verseArabicList=verseArabicList;
        this.verseTransList=verseTransList;
        mInflater = LayoutInflater.from(context);
        this.mToolbar = mToolbar;

    }
    private void showViews() {
        mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final Context context = parent.getContext();
        if (viewType == TYPE_ITEM) {
            final View view = LayoutInflater.from(context).inflate(R.layout.verse_row_exmp, parent, false);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //openPopup(view);

                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    VerseArabic verseArabic = (VerseArabic) v.getTag();

                    setBookMark(verseArabic,Global.bookmarkedStore,verseArabic.getSurahNo()+":"+verseArabic.getVerseId());
                    //setBookMark(verseArabic,Global.bookMarkedStoreSurah,verseArabic.getSurahNo());
                    notifyDataSetChanged();

                    showViews();

                    return false;
                }
            });

            return VerseItemViewHolder.newInstance(view, context);
        } else if (viewType == TYPE_HEADER) {
            final View view = LayoutInflater.from(context).inflate(R.layout.verse_header, parent, false);
            return SurahHeaderHolder.newInstance(view);
        }
        throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types    correctly");

    }

    public void setBookMark(VerseArabic verseArabic,SharedPreferences sharedPreferences,String key){

        if (sharedPreferences != null) {
            String id = sharedPreferences.getString(key, "");
            SharedPreferences.Editor bookmarkEditor = sharedPreferences.edit();
            if (id.equalsIgnoreCase(verseArabic.getVerseId())) {
                bookmarkEditor.remove(key);
                bookmarkEditor.commit();
                Toast.makeText(context, "removed" + id, Toast.LENGTH_SHORT).show();
            } else {
                bookmarkEditor.putString(key, verseArabic.getVerseId());
                bookmarkEditor.commit();
                Toast.makeText(context, "bookmarked" + id, Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void openPopup(View view){

                    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.popup, null);
                    final PopupWindow popupWindow = new PopupWindow(
                            popupView,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);


                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        int orgX, orgY;
                        int offsetX, offsetY;

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    orgX = (int) event.getX();
                                    orgY = (int) event.getY();
                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    offsetX = (int) event.getRawX() - orgX;
                                    offsetY = (int) event.getRawY() - orgY;
                                    popupWindow.update(offsetX, offsetY, -1, -1, true);
                                    break;
                            }
                            return true;
                        }
                    });

                    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                    int width = displayMetrics.widthPixels / 2;
                    int height = (displayMetrics.heightPixels);

                    popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            //Toast.makeText(context,"dismissed",Toast.LENGTH_SHORT).show();
                            Global.isPopupOpen = false;
                        }
                    });

                    if (Global.isPopupOpen) {

                        popupWindow.dismiss();
                        Global.isPopupOpen = false;
                    } else {
                        popupWindow.showAsDropDown(view, width, -100);

                        Global.isPopupOpen = true;
                    }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (!isPositionHeader(position)) {
            VerseItemViewHolder itemHolder=(VerseItemViewHolder)holder;

            VerseArabic verseArabic = (VerseArabic) verseArabicList.get(position-1);
            VerseTrans verseTrans;
            if (verseArabic.getVerseId().equalsIgnoreCase("0")) {
                verseTrans = new VerseTrans();
            } else if (verseArabic.getSurahNo().equalsIgnoreCase("1") || verseArabic.getSurahNo().equalsIgnoreCase("9")) {
                verseTrans = (VerseTrans) verseTransList.get(position-1);
            } else {
                verseTrans = (VerseTrans) verseTransList.get(position - 2);
            }
            itemHolder.setItemText(verseArabic,verseTrans);
        }
    }


    //added a method to check if given position is a header
    private boolean isPositionHeader(int position) {
        return position == 0;
    }
    //our old getItemCount()
    public int getBasicItemCount() {
        return verseArabicList == null ? 0 : verseArabicList.size();
    }

    @Override
    public int getItemCount() {
        return getBasicItemCount()+1; // header
    }
    //added a method that returns viewType for a given position
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public long getHeaderId(int position) {
        //return position/1;
        return position;
    }

    @Override
    public VerseItemHeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.item_header, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VerseArabic verseArabic = (VerseArabic) v.getTag();
                Toast.makeText(context, "" + verseArabic.getVerseId(), Toast.LENGTH_SHORT).show();
            }
        });

        return VerseItemHeaderViewHolder.newInstance(view, context);
    }

    @Override
    public void onBindHeaderViewHolder(VerseItemHeaderViewHolder headerHolder, int position, View view) {
        if (!isPositionHeader(position)) {
            VerseArabic verseArabic = (VerseArabic) verseArabicList.get(position - 1);

            if (verseArabic.getVerseId().equalsIgnoreCase("0")) {
                headerHolder.header.setVisibility(View.GONE);
            } else if (verseArabic.getSurahNo().equalsIgnoreCase("1") || verseArabic.getSurahNo().equalsIgnoreCase("9")) {
                headerHolder.header.setVisibility(View.VISIBLE);
            } else {
                verseArabic = (VerseArabic) verseArabicList.get(position - 1);
                headerHolder.header.setVisibility(View.VISIBLE);
            }
            headerHolder.setHeaderText(verseArabic);
        }
    }

}
