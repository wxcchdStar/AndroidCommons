package wxc.android.commons;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class BaseActivity extends AppCompatActivity {
    private static final int ON_CREATE      = 1;
    private static final int ON_START       = 2;
    private static final int ON_RESUME      = 3;
    private static final int ON_PAUSE       = 4;
    private static final int ON_STOP        = 5;
    private static final int ON_DESTROY     = 6;
    private static final int ON_SAVE_STATE  = 7;

    private ArrayList<ActivityLifecycleCallbacks> mActivityLifecycleCallbacks = new ArrayList<>();

    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callbacks) {
        if (callbacks != null) {
            mActivityLifecycleCallbacks.add(callbacks);
        }
    }

    public void unRegisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callbacks) {
        if (callbacks != null) {
            mActivityLifecycleCallbacks.remove(callbacks);
        }
    }

    /**
     * 注册Activity监听回调接口
     * @return
     */
    protected abstract ActivityLifecycleCallbacks[] newActivityLifecycleCallbacks();

    /**
     * 调用{@link {@link Activity#setContentView(int)}及其重载方法
     */
    protected abstract void setContentView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        ActivityLifecycleCallbacks[] callbacksArr = newActivityLifecycleCallbacks();
        if (callbacksArr != null && callbacksArr.length > 0) {
            mActivityLifecycleCallbacks.addAll(Arrays.asList(callbacksArr));
            onLifecycleCallbacks(ON_CREATE, savedInstanceState);
        }
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
        onLifecycleCallbacks(ON_DESTROY);
        if (mActivityLifecycleCallbacks != null) {
            mActivityLifecycleCallbacks.clear();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        onLifecycleCallbacks(ON_SAVE_STATE, outState);
    }

    private void onLifecycleCallbacks(int level) {
        onLifecycleCallbacks(level, null);
    }

    private void onLifecycleCallbacks(int level, Bundle savedInstanceState) {
        if (mActivityLifecycleCallbacks != null) {
            for (ActivityLifecycleCallbacks callbacks : mActivityLifecycleCallbacks) {
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

    public interface ActivityLifecycleCallbacks {
        void onCreate(Bundle savedInstanceState);

        void onStart();

        void onResume();

        void onPause();

        void onStop();

        void onDestroy();

        void onSaveSate(Bundle outState);
    }

    public static class SimpleActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {

        @Override
        public void onCreate(Bundle savedInstanceState) {

        }

        @Override
        public void onStart() {

        }

        @Override
        public void onResume() {

        }

        @Override
        public void onPause() {

        }

        @Override
        public void onStop() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public void onSaveSate(Bundle savedInstanceState) {

        }
    }
}
