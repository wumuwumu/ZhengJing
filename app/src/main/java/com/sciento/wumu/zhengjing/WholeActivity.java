package com.sciento.wumu.zhengjing;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.sciento.wumu.zhengjing.Fragment.HomeFragment;
import com.sciento.wumu.zhengjing.Fragment.PersonFragment;
import com.sciento.wumu.zhengjing.Fragment.WebFragment;
import com.sciento.wumu.zhengjing.adapter.NoScrollViewPager;
import com.sciento.wumu.zhengjing.adapter.ViewPagerAdapter;

public class WholeActivity extends AppCompatActivity {

   private BottomNavigationView bottomNavigationView;


    private NoScrollViewPager noScrollViewPager;

    //Fragment
    HomeFragment homeFragment;
    PersonFragment personFragment;
    WebFragment webFragment;
    MenuItem prevMenuItem;

    private static final int REQUEST_ENABLE_BT = 2;


    public static BleManager bleManager;
    private BluetoothAdapter mBluetoothAdapter;
    private static final String TAG = "WholeActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mayRequestLocation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole);
        init();
        initEvent();
        checkBLEFeature();
        setupViewPager(noScrollViewPager);

    }

    @Override
    protected void onDestroy() {
        bleManager.closeBluetoothGatt();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (!mBluetoothAdapter.isEnabled()) {
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//        }
        bleManager.enableBluetooth();
    }

    private void setupViewPager(NoScrollViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = HomeFragment.newInstance();
        webFragment = WebFragment.newInstance();
        personFragment = PersonFragment.newInstance();
        viewPagerAdapter.addFragment(homeFragment);
        viewPagerAdapter.addFragment(webFragment);
        viewPagerAdapter.addFragment(personFragment);
        viewPager.setAdapter(viewPagerAdapter);


    }

    private void initEvent() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId())
                        {
                            case  R.id.navigation_home:
                                noScrollViewPager.setCurrentItem(0);
                                return true;
                            case  R.id.navigation_web:
                                noScrollViewPager.setCurrentItem(1);
                                return true;
                            case  R.id.navigation_person:
                                noScrollViewPager.setCurrentItem(2);
                                return true;
                        }
                        return false;
                    }
                }
        );

        noScrollViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        viewPager.setOnTouchListener(new View.OnTouchListener()
//        {
//            @Override
//            public boolean onTouch(View v, MotionEvent event)
//            {
//                return true;
//            }
//        });



    }

    private void init() {
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        noScrollViewPager = (NoScrollViewPager)findViewById(R.id.viewpager);
    }


    private static final int REQUEST_FINE_LOCATION = 0;

    private void mayRequestLocation() {
        String[] permissionString = {
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_PRIVILEGED,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        if (Build.VERSION.SDK_INT >= 23) {
            for (int i = 0; i < permissionString.length; i++) {
                int checkCallPhonePermission = ContextCompat.checkSelfPermission(WholeActivity.this, permissionString[i]);
                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                    //判断是否需要 向用户解释，为什么要申请该权限
                    // if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION))
                    //Toast.makeText(context,R.string.ble_need, 1).show();
                    ActivityCompat.requestPermissions(this, permissionString, REQUEST_FINE_LOCATION);
                    return;
                } else {
                }
            }
        } else {

        }
    }

    private void checkBLEFeature() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        bleManager = new BleManager(this);
        bleManager.enableBluetooth();
    }

}
