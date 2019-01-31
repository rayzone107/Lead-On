package com.rachitgoyal.leadon.module.home;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.infideap.drawerbehavior.Advance3DDrawerLayout;
import com.rachitgoyal.leadon.R;
import com.rachitgoyal.leadon.model.Lead;
import com.rachitgoyal.leadon.module.base.BaseActivity;
import com.rachitgoyal.leadon.module.home.adapter.OfferFragment;
import com.rachitgoyal.leadon.module.home.adapter.OfferPagerAdapter;
import com.rachitgoyal.leadon.module.home.fragment_discount.DiscountDialogFragment;
import com.rachitgoyal.leadon.module.home.personal_fragment.TasksFragment;
import com.rachitgoyal.leadon.module.login.LoginActivity;
import com.rachitgoyal.leadon.util.Constants;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements HomeContract.View, OfferFragment.OnOfferClickedListener,
        NavigationView.OnNavigationItemSelectedListener, DiscountDialogFragment.OnDialogClickListener {

    private static final int NUM_PAGES = 3;
    private static final long DELAY_MS = 500;
    private static final long PERIOD_MS = 5000;

    @BindView(R.id.drawer_layout)
    Advance3DDrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.hamburger_iv)
    ImageView mHamburgerIV;

    @BindView(R.id.offer_view_pager)
    ViewPager mOfferViewPager;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.container)
    FrameLayout mContainer;

    private int mCurrentPage = 0;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        OfferPagerAdapter mPagerAdapter = new OfferPagerAdapter(getSupportFragmentManager());
        mOfferViewPager.setAdapter(mPagerAdapter);
        mOfferViewPager.setCurrentItem(mCurrentPage);
        mTabLayout.setupWithViewPager(mOfferViewPager, true);

        mHamburgerIV.setOnClickListener(v -> {
            if (!mDrawerLayout.isDrawerOpen(Gravity.START)) {
                mDrawerLayout.openDrawer(Gravity.START);
            } else {
                mDrawerLayout.closeDrawer(Gravity.START);
            }
        });

        mDrawerLayout.setViewRotation(Gravity.START, 15);
        mDrawerLayout.setViewScale(Gravity.START, 0.9f);
        mDrawerLayout.setViewScrimColor(Gravity.START, Color.TRANSPARENT);
        mDrawerLayout.setViewElevation(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK, 10);
        mDrawerLayout.setRadius(Gravity.START, 50);

        mNavView.setNavigationItemSelectedListener(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            logout();
        } else {
            View header = mNavView.getHeaderView(0);
            TextView userNameTV = header.findViewById(R.id.user_name_tv);
            TextView emailTV = header.findViewById(R.id.email_id_tv);
            ImageView imageIV = header.findViewById(R.id.user_image_iv);

            userNameTV.setText(user.getDisplayName());
            emailTV.setText(user.getEmail());
            Glide.with(imageIV).load(user.getPhotoUrl()).into(imageIV);
        }

        mOfferViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (mCurrentPage == NUM_PAGES) {
                mCurrentPage = 0;
            }
            mOfferViewPager.setCurrentItem(mCurrentPage++, true);
        };

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, TasksFragment.newInstance());
        ft.commit();

    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onOfferClicked(String type) {
        switch (type) {
            case Constants.OFFER_TYPE.DISCOUNT:
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                DiscountDialogFragment discountDialogFragment = new DiscountDialogFragment();
                discountDialogFragment.show(ft, "dialog");
                break;
            case Constants.OFFER_TYPE.REFERRAL:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String code = "DUMMY";
                if (user != null && user.getDisplayName() != null) {
                    code = user.getDisplayName().toUpperCase().replaceAll("\\s+", "").substring(0, 5);
                }
                sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.referral_message, code));
                sendIntent.setType("text/plain");
                this.startActivity(sendIntent);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_resume_course:
                Toast.makeText(this, R.string.courses_not_implemented_yet, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_all_courses:
                openSimplilearnWebsite();
                break;
            case R.id.nav_goto_website:
                openSimplilearnWebsite();
                break;
            case R.id.nav_rate_app:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.mobile.simplilearn")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mobile.simplilearn")));
                }
                break;
            case R.id.nav_logout:
                logout();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openSimplilearnWebsite() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.simplilearn.com/"));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    public void onSubmitClicked(Lead lead) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("leads");
        myRef.child(lead.getMobile()).setValue(lead);

        Toast.makeText(this, getString(R.string.details_saved), Toast.LENGTH_SHORT).show();
    }
}
