package ihandy.com.a2014011300;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;;import java.util.List;

/**
 * Created by ok on 2016/9/4.
 */
public class MyFragment extends Fragment{

        private TabLayout tab_FindFragment_title;                            //定义TabLayout
        private ViewPager vp_FindFragment_pager;                             //定义viewPager
        private FragmentPagerAdapter fAdapter;                               //定义adapter

        private List<Fragment> list_fragment;                                //定义要装fragment的列表
        private List<String> list_title;                                     //tab名称列表



}
