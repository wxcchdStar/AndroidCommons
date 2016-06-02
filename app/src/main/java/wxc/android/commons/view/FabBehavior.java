package wxc.android.commons.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

public class FabBehavior extends FloatingActionButton.Behavior {
    private BottomSlideBehavior mBottomSlideBehavior;

    public FabBehavior() {
        mBottomSlideBehavior = new BottomSlideBehavior();
    }

    public FabBehavior(Context context, AttributeSet attrs) {
        mBottomSlideBehavior = new BottomSlideBehavior();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return mBottomSlideBehavior.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dx, int dy, int[] consumed) {
        mBottomSlideBehavior.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        if (child.getTranslationY() <= 0) {
            return super.onDependentViewChanged(parent, child, dependency);
        }
        return false;
    }
}
