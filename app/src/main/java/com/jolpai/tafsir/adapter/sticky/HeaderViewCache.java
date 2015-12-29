package com.jolpai.tafsir.adapter.sticky;

import android.support.v7.widget.RecyclerView;
import android.util.LongSparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

/**
 * Created by Tanim reja on 12/23/2015.
 */
public class HeaderViewCache implements HeaderProvider {
    private final StickyHeaderAdapter mAdapter;
    private final LongSparseArray<View> mHeaderView=new LongSparseArray<>();

    public HeaderViewCache(StickyHeaderAdapter mAdapter){
        this.mAdapter=mAdapter;
    }




    @Override
    public View getHeader(RecyclerView recyclerView, int position) {
        long headerId=mAdapter.getHeaderId(position);
        View header = mHeaderView.get(headerId);
        if(header == null){
            RecyclerView.ViewHolder viewHolder = mAdapter.onCreateHeaderViewHolder(recyclerView);
            mAdapter.onBindHeaderViewHolder(viewHolder,position,recyclerView);
            header=viewHolder.itemView;
            if(header.getLayoutParams()== null){
                header.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            int widthSpec;
            int heightSpec;
            widthSpec = View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY);
            heightSpec = View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), View.MeasureSpec.UNSPECIFIED);

            int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                    recyclerView.getPaddingLeft() + recyclerView.getPaddingRight(), header.getLayoutParams().width);
            int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                    recyclerView.getPaddingTop() + recyclerView.getPaddingBottom(), header.getLayoutParams().height);


            header.measure(childWidth,childHeight);

            header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
            mHeaderView.put(headerId, header);
        }
        return header;
    }

    @Override
    public void invalidate(Map<Long,RecyclerView.ViewHolder> headerCache) {
        headerCache.clear();
    }
}
