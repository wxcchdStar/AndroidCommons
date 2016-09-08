package wxc.android.commons.lib.base;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public abstract class BaseFragmentPresenter {

    protected BaseFragment mFragment;

    public void setFragment(BaseFragment fragment) {
        mFragment = fragment;
    }

    public void onAttach(Context context) {
    }

    public void onCreate(Bundle savedInstanceState) {
    }

    public void initContentView(View view) {
    }

    public void onActivityCreated(Bundle savedInstanceState) {
    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onDestroyView() {
    }

    public void onDestroy() {
    }

    public void onDetach() {
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public void onSaveInstanceState(Bundle outState) {
    }
}
