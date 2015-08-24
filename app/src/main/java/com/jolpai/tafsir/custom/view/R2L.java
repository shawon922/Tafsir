package com.jolpai.tafsir.custom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Tanim Reja on 5/10/2015.
 */
public class R2L extends ViewGroup {
    private int mHeight;
    private final static int PAD_H = 2, PAD_V = 2;

    public R2L(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setScrollContainer(true);

    }

    @SuppressLint("NewApi")
    @Override
    protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {

        final int count = getChildCount();
        int curWidth, curLeft, curTop, maxHeight, curRight, curBottom, curHeight;


        int childLeft = this.getPaddingLeft();
        int childTop = this.getPaddingTop();
        int childRight = this.getMeasuredWidth() - this.getPaddingRight();
        int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();
        int childWidth = childRight - childLeft;
        int childHeight = childBottom - childTop;

        maxHeight = 0;
        curRight = childWidth;
        curTop = childTop;

        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);

            if (childView.getVisibility() != GONE) {
                // Get the maximum size of the child
                childView.measure(MeasureSpec.makeMeasureSpec(childWidth,
                        MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(
                        childHeight, MeasureSpec.AT_MOST));

                curWidth = childView.getMeasuredWidth();
                curHeight = childView.getMeasuredHeight();
                // wrap is reach to the end create new line if need
                int endPoint = curRight - curWidth;
                if (endPoint <= childLeft) {
                    curRight = childWidth;
                    curTop += maxHeight;
                    maxHeight = 0;

                }

                // do the layout
                curLeft = curRight - curWidth;
                curRight = curLeft + curWidth;
                curBottom = curTop + curHeight;

                childView.layout(curLeft, curTop, curRight, curBottom);
                // store the max height
                if (maxHeight < curHeight) {
                    maxHeight = curHeight;
                }

                curRight -= curWidth;

            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        assert (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED);
        final int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        final int count = getChildCount();
        int xpos = getPaddingLeft();
        int ypos = getPaddingTop();
        int childHeightMeasureSpec;
        if(MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST)
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);
        else
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        mHeight = 0;
        for(int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if(child.getVisibility() != GONE) {
                child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), childHeightMeasureSpec);
                final int childw = child.getMeasuredWidth();
                mHeight = Math.max(mHeight, child.getMeasuredHeight() + PAD_V);
                if(xpos + childw > width) {
                    xpos = getPaddingLeft();
                    ypos += mHeight;
                }
                xpos += childw + PAD_H;
            }
        }
        if(MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            height = ypos + mHeight;
        } else if(MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            if(ypos + mHeight < height) {
                height = ypos + mHeight;
            }
        }
        height += 2; // Fudge to avoid clipping bottom of last row.
        setMeasuredDimension(width, height);
    }

}
