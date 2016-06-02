package wxc.android.commons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;

import wxc.android.commons.utils.V;

public abstract class BaseActivity extends AppCompatActivity {
    private static final int ON_CREATE = 1;
    private static final int ON_START = 2;
    private static final int ON_RESUME = 3;
    private static final int ON_PAUSE = 4;
    private static final int ON_STOP = 5;
    private static final int ON_DESTROY = 6;
    private static final int ON_SAVE_STATE = 7;

    protected ActionBar mActionBar;

    private boolean mDestroyed;

    private ArrayList<ActivityCallbacks> mActivityCallbacks = new ArrayList<>();

    public void startActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        initToolbar();
        ActivityCallbacks[] callbacksArr = newActivityLifecycleCallbacks();
        if (callbacksArr != null && callbacksArr.length > 0) {
            mActivityCallbacks.addAll(Arrays.asList(callbacksArr));
            onLifecycleCallbacks(ON_CREATE, savedInstanceState);
        }
    }

    protected abstract int getLayoutResId();

    protected ActivityCallbacks[] newActivityLifecycleCallbacks() {
        return new ActivityCallbacks[0];
    }

    private void initToolbar() {
        Toolbar toolbar = V.f(this, R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            mActionBar = getSupportActionBar();
        }
    }

    public boolean isDestroyed() {
        return mDestroyed;
    }

    @Override
    protected void onStart() {
        super.onStart();
        onLifecycleCallbacks(ON_START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onLifecycleCallbacks(ON_RESUME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        onLifecycleCallbacks(ON_PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        onLifecycleCallbacks(ON_STOP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDestroyed = true;
        onLifecycleCallbacks(ON_DESTROY);
        if (mActivityCallbacks != null) {
            mActivityCallbacks.clear();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        onLifecycleCallbacks(ON_SAVE_STATE, outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else {
            if (mActivityCallbacks != null) {
                for (ActivityCallbacks callbacks : mActivityCallbacks) {
                    if (callbacks != null) {
                        callbacks.onOptionsItemSelected(item);
                    }
                }
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mActivityCallbacks != null) {
            for (ActivityCallbacks callbacks : mActivityCallbacks) {
                if (callbacks != null) {
                    callbacks.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    private void onLifecycleCallbacks(int level) {
        onLifecycleCallbacks(level, null);
    }

    private void onLifecycleCallbacks(int level, Bundle savedInstanceState) {
        if (mActivityCallbacks != null) {
            for (ActivityCallbacks callbacks : mActivityCallbacks) {
                if (callbacks != null) {
                    switch (level) {
                        case ON_CREATE:
                            callbacks.onCreate(savedInstanceState);
                            break;
                        case ON_START:
                            callbacks.onStart();
                            break;
                        case ON_RESUME:
                            callbacks.onResume();
                            break;
                        case ON_PAUSE:
                            callbacks.onPause();
                            break;
                        case ON_STOP:
                            callbacks.onStop();
                            break;
                        case ON_DESTROY:
                            callbacks.onDestroy();
                            break;
                        case ON_SAVE_STATE:
                            callbacks.onSaveSate(savedInstanceState);
                            break;
                    }
                }
            }
        }
    }

    public static class ActivityCallbacks {

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

        public void onSaveSate(Bundle savedInstanceState) {

        }

        public void onOptionsItemSelected(MenuItem item) {

        }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {

        }
    }
}
