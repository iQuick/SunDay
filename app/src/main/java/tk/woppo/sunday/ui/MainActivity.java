package tk.woppo.sunday.ui;

import android.app.FragmentManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import tk.woppo.sunday.App;
import tk.woppo.sunday.Const;
import tk.woppo.sunday.R;
import tk.woppo.sunday.model.city.AreaModel;
import tk.woppo.sunday.ui.fragment.BaseFragment;
import tk.woppo.sunday.ui.fragment.DrawerFragment;
import tk.woppo.sunday.ui.fragment.DrawerFragment_;
import tk.woppo.sunday.ui.fragment.HomeFragment;
import tk.woppo.sunday.ui.fragment.HomeFragment_;
import tk.woppo.sunday.util.ActivityUtil;
import tk.woppo.sunday.util.SharedPrefUtil;
import tk.woppo.sunday.widget.FoldingDrawerLayout;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    protected final static String TAG = "MainActivity";

    private Menu mMenu;

    public HomeFragment homeFragment;

    private DrawerFragment drawerFragment;

    /**
     * 侧拉菜单
     */
    @ViewById(R.id.drawer_layout)
    FoldingDrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    /**
     * 天气列表
     */
    @ViewById(R.id.lv_weather)
    ListView mWeatherListView;

    @AfterViews
    void initActivity() {

        initData();
        initView();
    }

    private void initData() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mMenu.findItem(R.id.action_refresh).setVisible(false);
                if (homeFragment.getCurWeatherModel() != null)
                    drawerFragment.updateReminder(homeFragment.getCurWeatherModel());
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mMenu.findItem(R.id.action_refresh).setVisible(true);
            }
        };
    }

    private void initView() {

        actionBar.setIcon(R.drawable.ic_actionbar);

        mDrawerLayout.setScrimColor(Color.argb(100, 0, 0, 0));
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        replaceFragment(R.id.fl_content, homeFragment = new HomeFragment_());
        replaceFragment(R.id.fl_left_drawer, drawerFragment = new DrawerFragment_());
    }

    protected void replaceFragment(int viewId, BaseFragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(viewId, fragment).commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_refresh:
                homeFragment.setOnRefresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 杀死进程
        if (SharedPrefUtil.getBoolean(Const.CONFIG_EXIT_KILL, false))
            ActivityUtil.finishKill();
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }
}