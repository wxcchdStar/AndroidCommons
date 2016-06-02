package wxc.android.commons.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

// When the content scroll down, fab dismiss; when the content scroll up, fab show.
public class FabBehavior extends CoordinatorLayout.Behavior<View> {

    private boolean mFirst = true;

    private int mLastDy;

    private int mTranslation;

    private ValueAnimator mAnimator;

    public FabBehavior() {
    }

    public FabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, final View child, View target,
                                  int dx, int dy, int[] consumed) {
        // 判断是否改变了滑动方向
        boolean directionChanged = false;
        if ((dy > 0 && mLastDy < 0 || (dy < 0 && mLastDy > 0))) {
            directionChanged = true;
            mLastDy = 0;
            if (mAnimator != null && mAnimator.isRunning()) {
                mAnimator.cancel();
            }
        }
        // 展示或隐藏
        if (directionChanged || mFirst) {
            if (mFirst) {
                mFirst = false;
            }
            if (dy > 0) { // 下滑
                if (mTranslation == 0) {
                    mTranslation += child.getHeight();
                    if (child.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                        mTranslation += ((ViewGroup.MarginLayoutParams) child.getLayoutParams()).bottomMargin;
                    }
                }
                mAnimator = ObjectAnimator.ofFloat(child, View.TRANSLATION_Y, child.getTranslationY(), mTranslation);
                mAnimator.setInterpolator(new DecelerateInterpolator());
                mAnimator.start();
            } else { // 上滑
                mAnimator = ObjectAnimator.ofFloat(child, View.TRANSLATION_Y, child.getTranslationY(), 0);
                mAnimator.setInterpolator(new DecelerateInterpolator());
                mAnimator.start();
            }
        }
        // 保存最近一次滑动距离
        if (dy != 0) {
            mLastDy = dy;
        }
    }

}
