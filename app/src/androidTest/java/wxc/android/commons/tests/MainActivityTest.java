package wxc.android.commons.tests;


import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;

import wxc.android.commons.MainActivity;
import wxc.android.commons.R;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mActivity;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }

    public void test() {
        assertNotNull(mActivity);

        View decorView = mActivity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(decorView, mActivity.findViewById(R.id.cl_content));
    }
}
