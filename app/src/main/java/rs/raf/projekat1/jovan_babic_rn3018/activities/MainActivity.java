package rs.raf.projekat1.jovan_babic_rn3018.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.rafwalletprojekat.R;
import rs.raf.projekat1.jovan_babic_rn3018.viewpager.PagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initViewPager();
        initNav();
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.bot_nav_viewpager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
    }

    private void initNav() {
        ((BottomNavigationView)findViewById(R.id.bottom_navbar)).setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.botnav_state: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_1, false); break;
                case R.id.botnav_entry: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_2, false); break;
                case R.id.botnav_lists: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_3, false); break;
                case R.id.botnav_profile: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_4, false); break;
            }
            return true;
        });
    }
}