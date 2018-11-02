package ru.mail.park.tfshw2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class MyCustomViewGroup extends LinearLayout {

    public MyCustomViewGroup(Context context) {
        super(context);
    }

    public MyCustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public MyCustomViewGroup(Context context, AttributeSet attrs, int defstyle) {
        super(context, attrs, defstyle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        int preMeasuredWidth = MeasureSpec.getSize(widthMeasureSpec)
                - getPaddingLeft() - getPaddingRight();
        int childState = 0;
        int width = 0;
        int height = 0;
        int currentRowWidth = 0;

        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            final LinearLayout.LayoutParams params = getChildLayoutParams(child);
            if (child.getVisibility() == GONE) {
                continue;
            }
            int horizontalMargins = params.leftMargin + params.rightMargin;
            int verticalMargins = params.topMargin + params.bottomMargin ;
            measureChildWithMargins(child,
                    widthMeasureSpec, 0, heightMeasureSpec, 0);
            width += Math.max(width, child.getMeasuredWidth() + horizontalMargins);
            currentRowWidth += child.getMeasuredWidth() + horizontalMargins;
            if (currentRowWidth > preMeasuredWidth) {
                height += child.getMeasuredHeight() + verticalMargins;
                currentRowWidth = child.getMeasuredWidth() + horizontalMargins;
            } else {
                height = Math.max(height, child.getMeasuredHeight() + verticalMargins);
            }
            childState = combineMeasuredStates(childState, child.getMeasuredState());
        }
        height = Math.max(height, getSuggestedMinimumHeight());
        width = Math.max(width, getSuggestedMinimumWidth());
        setMeasuredDimension(resolveSizeAndState(width, widthMeasureSpec, childState),
                resolveSizeAndState(height, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int layoutLeft = getPaddingLeft();
        int layoutRight = getMeasuredWidth() - getPaddingRight();
        int layoutTop = getPaddingTop();
        int layoutBottom = getMeasuredHeight() - getPaddingBottom();
        int layoutWidth = layoutRight - layoutLeft;
        int layoutHeight = layoutTop - layoutBottom;
        int maxHeight = 0;
        int width, height;
        int right = layoutRight;
        int top = layoutTop;
        List<View> inRow = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            LinearLayout.LayoutParams params = getChildLayoutParams(child);
            if (child.getVisibility() == GONE) {
                continue;
            }
            child.measure(MeasureSpec.makeMeasureSpec(layoutWidth, MeasureSpec.AT_MOST),
                    MeasureSpec.makeMeasureSpec(layoutHeight, MeasureSpec.AT_MOST));
            width = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            height = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;
            if (right - width  <= layoutLeft) {
                right = layoutRight;
                top += maxHeight;
                inRow.clear();
                maxHeight = 0;
            }
            for(int j = 0; j < inRow.size(); j++) {
                View prevChild = inRow.get(j);
                prevChild.layout(prevChild.getLeft() - child.getWidth() - params.rightMargin,
                        top + params.topMargin,
                        prevChild.getRight() - child.getWidth() - params.rightMargin,
                        top + child.getMeasuredHeight() + params.topMargin);
            }
            inRow.add(child);
            child.layout(layoutRight - child.getMeasuredWidth() - params.rightMargin,
                    top + params.topMargin,
                    layoutRight - params.rightMargin,
                    top + child.getMeasuredHeight() + params.topMargin);
            if (maxHeight < height)
                maxHeight = height;
            right -= width;
        }
    }

    @NonNull
    private LinearLayout.LayoutParams getChildLayoutParams(@NonNull View child) {
        final ViewGroup.LayoutParams params = child.getLayoutParams();
        return (LinearLayout.LayoutParams) (checkLayoutParams(params)
                ? params : generateDefaultLayoutParams());
    }
}
