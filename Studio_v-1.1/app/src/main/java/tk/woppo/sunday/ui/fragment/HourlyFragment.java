package tk.woppo.sunday.ui.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import tk.woppo.sunday.R;
import tk.woppo.sunday.model.Weather;
import tk.woppo.sunday.ui.adapter.HourlyAdapter;
import tk.woppo.sunday.widget.jazzylistview.JazzyListView;

/**
 * Created by Ho on 2014/9/9.
 */
@EFragment(R.layout.fragment_hourly)
public class HourlyFragment extends BaseFragment {

    @ViewById(R.id.lv_hourly)
    JazzyListView mListView;

    @Bean
    HourlyAdapter mAdapter;

    @AfterViews
    void initFragment() {
        initView();
    }

    private void initView() {
        mListView.setAdapter(mAdapter);
    }

    public void updateView(List<Weather> list) {
        mAdapter.appendToList(list);
    }

}
