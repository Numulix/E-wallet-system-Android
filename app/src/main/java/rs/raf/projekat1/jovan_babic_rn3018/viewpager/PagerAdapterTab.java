package rs.raf.projekat1.jovan_babic_rn3018.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import rs.raf.projekat1.jovan_babic_rn3018.fragments.tabs.ExpensesFragment;
import rs.raf.projekat1.jovan_babic_rn3018.fragments.tabs.IncomesFragment;

public class PagerAdapterTab extends FragmentPagerAdapter {

    private final int ITEM_COUNT = 2;
    public static final int FRAGMENT_1 = 0;
    public static final int FRAGMENT_2 = 1;

    public PagerAdapterTab(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case FRAGMENT_1: fragment = new IncomesFragment(); break;
            default: fragment = new ExpensesFragment(); break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case FRAGMENT_1: return "Prihodi";
            default: return "Rashodi";
        }
    }
}
