package rs.raf.projekat1.jovan_babic_rn3018.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import com.example.rafwalletprojekat.R;

import rs.raf.projekat1.jovan_babic_rn3018.viewpager.PagerAdapterTab;
import com.google.android.material.tabs.TabLayout;


public class ListFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    public ListFragment() {
        super(R.layout.fragment_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTabs(view);
    }

    private void initTabs(View view) {
        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager.setAdapter(new PagerAdapterTab(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }
}