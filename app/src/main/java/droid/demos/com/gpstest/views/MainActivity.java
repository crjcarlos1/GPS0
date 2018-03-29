package droid.demos.com.gpstest.views;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import droid.demos.com.gpstest.R;
import droid.demos.com.gpstest.fragments.GPSFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showGPSFragment();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 1) {
            super.onBackPressed();
        } else {
            finish();
        }
    }

    private void showGPSFragment() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        GPSFragment gpsFragment = new GPSFragment();

        transaction.addToBackStack(GPSFragment.TAG);
        transaction.add(R.id.conteinerFragments, gpsFragment, GPSFragment.TAG).commit();
    }

}
