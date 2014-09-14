package tk.woppo.sunday.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;

import tk.woppo.sunday.R;
import tk.woppo.sunday.data.RequestManager;
import tk.woppo.sunday.util.ActivityUtil;
import tk.woppo.sunday.util.DialogUtil;
import tk.woppo.sunday.util.ToastUtil;

/**
 * Created by Ho on 2014/6/25.
 */

public class BaseActivity extends FragmentActivity {

    /**
     * 自定义加载对话框
     */
    protected Dialog mLoadingDialog;

    /**
     * ActionBar 动作菜单栏
     */
    protected ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.add(this);
        init();
    }

    private void init() {

        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 显示加载对话框
     */
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = DialogUtil.Instance().createLoadingDialog(this);
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    /**
     * 取消加载对话框
     */
    public void dismissLoading() {
        if (mLoadingDialog.isShowing() && mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 打开Activity，并跳转
     * 不带参数
     * @param clazz
     */
    protected void openActivity(Class clazz) {
        openActivity(clazz, null);
    }
    /**
     * 打开Activity，并跳转
     * 带参数
     * @param clazz
     * @param bundle
     */
    protected void openActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.cancelAll(this);
        ActivityUtil.remove(this);
    }

    /**
     * 添加到网络请求队列
     * @param request
     */
    protected void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
    }

    /**
     * 网络请求错误回调
     * @return
     */
    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.showShort(volleyError.getMessage());
            }
        };
    }
}
