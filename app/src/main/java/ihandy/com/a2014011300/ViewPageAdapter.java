package ihandy.com.a2014011300;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by ok on 2016/9/4.
 */
public class ViewPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> list_fragment;                         //fragment列表
    private List<String> list_Tab;                              //tab名的列表

    public ViewPageAdapter(FragmentManager fm,List<Fragment> list_fragment,List<String> list_Tab) {
        super(fm);
        this.list_fragment = list_fragment;
        this.list_Tab = list_Tab;
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return list_Tab.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return list_Tab.get(position);
    }
}

