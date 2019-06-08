package digitald.technologies.crypto_tracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import digitald.technologies.crypto_tracker.TabFragments.TabPortfoFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {




        return mFragmentList.get(position);
    }



    @Override
    public int getCount() {
        return mFragmentList.size();
    }

   /*/ @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
/*/
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
}
