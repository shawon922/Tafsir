package com.jolpai.tafsir.adapter.sticky;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Tanim reja on 12/15/2015.
 */
public class DividerDecoration extends RecyclerView.ItemDecoration {
    private int height;
    private int leftPadding;
    private int rightPadding;
    private Paint paint;

    private DividerDecoration(int height,int leftPadding,int rightPadding,int color ){
        this.height=height;
        this.leftPadding=leftPadding;
        this.rightPadding=rightPadding;
        this.paint=new Paint();
        paint.setColor(color);
    }

    @Override
    public void onDrawOver(Canvas canvas,RecyclerView recycler,RecyclerView.State state){
        int count=recycler.getChildCount();
        for(int i=0; i<count; i++){
            final View child = recycler.getChildAt(i);
            final int top = child.getBottom();
            final int bottom=top+height;

            int left = child.getLeft()+leftPadding;
            int right=child.getRight()-rightPadding;

            canvas.save();
            canvas.drawRect(left, top, right, bottom, paint);
            canvas.restore();
        }
    }

    public static class Builder {
        private Resources resources;
        private int height;
        private int leftPadding;
        private int rightPadding;
        private int color;

        public Builder(Context context) {
            resources = context.getResources();
            height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 1f, context.getResources().getDisplayMetrics());
            leftPadding = 0;
            rightPadding = 0;
            color = Color.BLACK;
        }

        /**
         * Set the divider height in pixels
         * @param pixels height in pixels
         * @return the current instance of the Builder
         */
        public Builder setHeight(float pixels) {
            height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pixels, resources.getDisplayMetrics());

            return this;
        }

        /**
         * Set the divider height in dp
         * @param resource height resource id
         * @return the current instance of the Builder
         */
        public Builder setHeight(@DimenRes int resource) {
            height = resources.getDimensionPixelSize(resource);
            return this;
        }

        /**
         * Sets both the left and right padding in pixels
         * @param pixels padding in pixels
         * @return the current instance of the Builder
         */
        public Builder setPadding(float pixels) {
            setLeftPadding(pixels);
            setRightPadding(pixels);

            return this;
        }

        /**
         * Sets the left and right padding in dp
         * @param resource padding resource id
         * @return the current instance of the Builder
         */
        public Builder setPadding(@DimenRes int resource) {
            setLeftPadding(resource);
            setRightPadding(resource);
            return this;
        }

        /**
         * Sets the left padding in pixels
         * @param pixelPadding left padding in pixels
         * @return the current instance of the Builder
         */
        public Builder setLeftPadding(float pixelPadding) {
            leftPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pixelPadding, resources.getDisplayMetrics());

            return this;
        }

        /**
         * Sets the right padding in pixels
         * @param pixelPadding right padding in pixels
         * @return the current instance of the Builder
         */
        public Builder setRightPadding(float pixelPadding) {
            rightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pixelPadding, resources.getDisplayMetrics());

            return this;
        }

        /**
         * Sets the left padding in dp
         * @param resource left padding resource id
         * @return the current instance of the Builder
         */
        public Builder setLeftPadding(@DimenRes int resource) {
            leftPadding = resources.getDimensionPixelSize(resource);

            return this;
        }

        /**
         * Sets the right padding in dp
         * @param resource right padding resource id
         * @return the current instance of the Builder
         */
        public Builder setRightPadding(@DimenRes int resource) {
            rightPadding = resources.getDimensionPixelSize(resource);

            return this;
        }

        /**
         * Sets the divider colour
         * @param resource the colour resource id
         * @return the current instance of the Builder
         */
        public Builder setColorResource(@ColorRes int resource) {
            setColor(resources.getColor(resource));

            return this;
        }

        /**
         * Sets the divider colour
         * @param color the colour
         * @return the current instance of the Builder
         */
        public Builder setColor(@ColorInt int color) {
            this.color = color;

            return this;
        }

        /**
         * Instantiates a DividerDecoration with the specified parameters.
         * @return a properly initialized DividerDecoration instance
         */
        public DividerDecoration build() {
            return new DividerDecoration(height, leftPadding, rightPadding, color);
        }
    }
}
