package wxc.android.commons.lib.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class BaseActivityPresenter {

    protected BaseActivity mActivity;

    public void setActivity(BaseActivity activity) {
        mActivity = activity;
    }

    public void onCreate(Bundle savedInstanceState) {

    }

    public void onStart() {

    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onStop() {

    }

    public void onDestroy() {

    }

    public void onSaveInstanceState(Bundle savedInstanceState) {

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}