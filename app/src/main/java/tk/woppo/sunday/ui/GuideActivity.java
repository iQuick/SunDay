package tk.woppo.sunday.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import tk.woppo.sunday.R;
import tk.woppo.sunday.ui.adapter.GuideAdapter;

/**
 * Created by Ho on 2014/7/10.
 */
@Fullscreen
@EActivity(R.layout.activity_guide)
public class GuideActivity extends Activity {

    @ViewById(R.id.guide)
    ViewPager mViewPager;

    @ViewById(R.id.guide_start)
    View start;

    @AfterViews
    void initActivity() {
        initView();
    }

    private void initView() {

        LayoutInflater llf = LayoutInflater.from(this);
        View view1 = llf.inflate(R.layout.layout_img1, null);
        View view2 = llf.inflate(R.layout.layout_img2, null);
        View view3 = llf.inflate(R.layout.layout_img3, null);
        View view4 = llf.inflate(R.layout.layout_img4, null);
        List<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);

        mViewPager.setAdapter(new GuideAdapter(views));
        mViewPager.setOnPageChangeListener(new MyPageChangeListener());

    }

    public class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 3) {
                start.setVisibility(View.VISIBLE);
            } else {
                start.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Click(R.id.guide_start)
    void clickStart() {

        Intent intent = new Intent(GuideActivity.this, MainActivity_.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
