package wxc.android.commons;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.SwipeDismissBehavior;
import android.view.View;
import android.view.ViewGroup;

import wxc.android.commons.utils.V;

public class SwipeDismissActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_swipe_dismiss;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("SwipeDismiss");
        // init swipe dismiss, 2.3上没有滑动效果
        ViewGroup contentFl = V.f(this, R.id.fl_content);
        SwipeDismissBehavior<View> swipeDismissBehavior = new SwipeDismissBehavior<>();
        swipeDismissBehavior.setStartAlphaSwipeDistance(1f);
        swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END);
        swipeDismissBehavior.setListener(new SwipeDismissBehavior.OnDismissListener() {
            @Override
            public void onDismiss(View view) {
                finish();
            }

            @Override
            public void onDragStateChanged(int state) {

            }
        });
        CoordinatorLayout.LayoutParams coordinatorParams = (CoordinatorLayout.LayoutParams) contentFl.getLayoutParams();
        coordinatorParams.setBehavior(swipeDismissBehavior);
    }
}
