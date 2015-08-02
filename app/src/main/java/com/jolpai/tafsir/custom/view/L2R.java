package com.jolpai.tafsir.custom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class L2R extends ViewGroup {

	private int mHeight;
	private final static int PAD_H = 2, PAD_V = 2;

	public L2R(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("NewApi") @Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		final int count = getChildCount();
		int curWidth, curHeight, curLeft, curTop, maxHeight;
		
		int childLeft = this.getPaddingLeft();
		int childTop =this.getPaddingTop();
		int childRight = this.getMeasuredWidth() - this.getPaddingRight();
		int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();
		int childWidth = childRight - childLeft;
		  int childHeight = childBottom - childTop;
		  
		  maxHeight = 0;
		  curLeft = childLeft;
		  curTop = childTop;
		  
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);

			if (child.getVisibility() != GONE) {
			      //Get the maximum size of the child
			      child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST), 
			                    MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));
			      curWidth = child.getMeasuredWidth();
			      curHeight = child.getMeasuredHeight();
			      
			      //wrap is reach to the end
				if (curLeft + curWidth >= childRight) {
					curLeft = childLeft;
					curTop += maxHeight;
					maxHeight = 0;
				}
				// do the layout

				child.layout(curLeft, curTop, curLeft + curWidth, curTop
						+ curHeight);

				// store the max height
				if (maxHeight < curHeight)
					maxHeight = curHeight;
				curLeft += curWidth;
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
		height += 5; // Fudge to avoid clipping bottom of last row.
		setMeasuredDimension(width, height);
	}

}
