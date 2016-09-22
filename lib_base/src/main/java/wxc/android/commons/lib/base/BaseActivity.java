package wxc.android.commons.lib.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import wxc.android.commons.lib.base.linker.ActivityLinker;

public abstract class BaseActivity extends AppCompatActivity {

    private boolean mDestroyed;

    private ActivityLinker mLinker = new ActivityLinker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        mLinker.register(this);
        mLinker.onCreate(savedInstanceState);
    }

    protected abstract int getLayoutId();

    @Override
    protected void onStart() {
        super.onStart();
        mLinker.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLinker.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLinker.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLinker.onStop();
    }

    @Override
    protected void onDestroy() {
        mDestroyed = true;
        super.onDestroy();
        mLinker.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mLinker.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            if (mLinker.onOptionsItemSelected(item)) {
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLinker.onActivityResult(requestCode, resultCode, data);
    }

    public boolean isDestroyed() {
        return mDestroyed || isFinishing();
    }

    public void startActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void addPresenter(BaseActivityPresenter presenter) {
        mLinker.addActivityCallbacks(presenter);
    }

    /**
     * 判断Activity是否可用，常用于耗时操作结束后对UI进行更新时
     *
     * @param activity BaseActivity
     * @return true-可用, false-不可用
     */
    public static boolean isAvailable(BaseActivity activity) {
        return activity != null && !activity.isDestroyed() && !activity.isFinishing();
    }

}