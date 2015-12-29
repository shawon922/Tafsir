package com.jolpai.tafsir.adapter.sticky;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Tanim reja on 12/15/2015.
 */
public class StickyHeaderDecoration extends RecyclerView.ItemDecoration{
    private Map<Long,RecyclerView.ViewHolder> headerCache;
    private StickyHeaderAdapter adapter;
    private boolean renderInline;
    private HeaderProvider mHeaderProvider;


    /**
     *
     */
    public StickyHeaderDecoration(StickyHeaderAdapter adapter){
        this(adapter,false);
    }
    public StickyHeaderDecoration(StickyHeaderAdapter adapter,boolean renderInline){
     this(adapter,renderInline,new HeaderViewCache(adapter));

    }

    public StickyHeaderDecoration(StickyHeaderAdapter adapter,
                                  boolean renderInline,
                                  HeaderProvider headerProvider){
        this.adapter=adapter;
        this.headerCache=new Hashtable<>();
        this.renderInline=renderInline;
        this.mHeaderProvider=headerProvider;
    }


    @Override
    public void getItemOffsets(Rect outRect,View view,RecyclerView recycler,RecyclerView.State state){
        int position = recycler.getChildAdapterPosition(view);

        int headerHeight=0;
        if (position != RecyclerView.NO_POSITION && hasHeader(position)){
            View header = getHeader(recycler,position).itemView;
            headerHeight=getHeaderHeightForLayout(header);
        }
        outRect.set(0,headerHeight,0,0);

    }

    private boolean hasHeader(int position) {
        if(position ==0){
            return true;
        }
        int previous = position-1;
        return adapter.getHeaderId(position) != adapter.getHeaderId(previous);
    }

    private RecyclerView.ViewHolder getHeader(RecyclerView recycler, int position) {

       // return mHeaderProvider.getHeader(recycler,position);



        final long key = adapter.getHeaderId(position);
        if(headerCache.containsKey(key)){
            return headerCache.get(key);
        }
        else{
            final  RecyclerView.ViewHolder holder = adapter.onCreateHeaderViewHolder(recycler);
            final View header =holder.itemView;

            adapter.onBindHeaderViewHolder(holder,position,header);

            int widthSpec = View.MeasureSpec.makeMeasureSpec(recycler.getWidth(),View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(recycler.getHeight(),View.MeasureSpec.UNSPECIFIED);

            int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                            recycler.getPaddingLeft() + recycler.getPaddingRight(),
                            header.getLayoutParams().width);
            int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                            recycler.getPaddingTop()+recycler.getPaddingBottom(),
                            header.getLayoutParams().height);

            header.measure(childWidth, childHeight);
            header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
            headerCache.put(key,holder);
            return holder;
        }
    }


    @Override
    public void onDrawOver(Canvas canvas,RecyclerView recycler,RecyclerView.State state){
        final  int count = recycler.getChildCount();

        for(int layoutPosition=0;layoutPosition<count;layoutPosition++){
            final  View child = recycler.getChildAt(layoutPosition);
            final int adapterPosition =recycler.getChildAdapterPosition(child);

            if(adapterPosition != RecyclerView.NO_POSITION && (layoutPosition==0 || hasHeader(adapterPosition))){
                View header = getHeader(recycler,adapterPosition).itemView;
                canvas.save();
                final int left = child.getLeft();
                final int top=getHeaderTop(recycler,child,header,adapterPosition,layoutPosition);
                canvas.translate(left,top);
                header.draw(canvas);
                canvas.restore();
            }

        }
    }

    private int getHeaderTop(RecyclerView recycler, View child, View header, int adapterPosition, int layoutPosition) {

        int headerHeight=getHeaderHeightForLayout(header);
        int top=((int) child.getY())-headerHeight;
        if(layoutPosition==0){
            final int count = recycler.getChildCount();
            final long currentId=adapter.getHeaderId(adapterPosition);
            for(int i=1; i<count; i++){
                int adapterPositionHere =recycler.getChildAdapterPosition(recycler.getChildAt(i));
                if(adapterPositionHere != RecyclerView.NO_POSITION) {
                    long nextId = adapter.getHeaderId(adapterPositionHere);
                    if (nextId != currentId) {
                        final View next = recycler.getChildAt(i);
                        final int offset = ((int) next.getY()) - (headerHeight + getHeader(recycler, adapterPosition).itemView.getHeight());
                        if (offset < 0) {
                            return offset;
                        } else {
                            break;
                        }
                    }
                }
            }
            top =Math.max(0,top);
        }
        return top;
    }

    private int getHeaderHeightForLayout(View header) {
        return renderInline ?0 :header.getHeight();
    }

    public void invalidateHeaders() {
        //mHeaderProvider.invalidate(headerCache);
        headerCache.clear();
    }


}
