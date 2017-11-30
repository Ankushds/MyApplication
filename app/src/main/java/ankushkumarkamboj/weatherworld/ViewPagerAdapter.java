package ankushkumarkamboj.weatherworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    List<String> list = new ArrayList<>();
    SharedPreferences sf;
    Fragment fragment1;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        sf = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public Fragment getItem(int position) {
        long lll = sf.getLong("zero", 0000);
        switch ((int) lll) {
            case 1:
                fragment1 = new Fragment1();
                break;
            case 2:
                fragment1 = new Fragment2();
                break;
            case 3:
                fragment1 = new Fragment3();
                break;
            case 4:
                fragment1 = new Fragment5();
                break;
            default:
                fragment1 = new Fragment1();
        }
        return fragment1;
    }

    @Override
    public int getItemPosition(Object object) {
        super.getItemPosition(object);
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void addItemTab(String name) {
        list.add(name);
        notifyDataSetChanged();
    }

    public void removeItemTab(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }
}
