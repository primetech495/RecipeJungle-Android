package com.prime.recipejungle.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.prime.recipejungle.R;
import com.prime.redef.utils.ViewUtils;

public class PaddedDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    private int mOrientation;
    private final Rect mBounds = new Rect();
    private int mLeftMargin, mRightMargin;

    public PaddedDividerItemDecoration(@NonNull Context context, int orientation) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.sh_divider);
        setOrientation(orientation);
    }

    private void setOrientation(int orientation) {
        mOrientation = orientation;
    }

    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getLayoutManager() == null || mDivider == null)
            return;

        if (mOrientation == LinearLayout.VERTICAL)
            drawVertical(canvas, parent);
        else
            drawHorizontal(canvas, parent);
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();

        final int left;
        final int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            final int top = bottom - mDivider.getIntrinsicHeight();
            mDivider.setBounds(left + mLeftMargin, top, right - mRightMargin, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int top;
        final int bottom;
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top, parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager != null)
                layoutManager.getDecoratedBoundsWithMargins(child, mBounds);
            final int right = mBounds.right + Math.round(child.getTranslationX());
            final int left = right - mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (mDivider == null) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        if (mOrientation == LinearLayout.VERTICAL) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }

    public void setLeftMargin(int mLeftMargin) {
        this.mLeftMargin = mLeftMargin;
    }

    public void setRightMargin(int mRightMargin) {
        this.mRightMargin = mRightMargin;
    }

    public void setLeftMarginDp(int leftMargin) {
        this.mLeftMargin = (int) ViewUtils.dpToPx(leftMargin);
    }

    public void setRightMarginDp(int rightMargin) {
        this.mRightMargin = (int) ViewUtils.dpToPx(rightMargin);
    }
}
