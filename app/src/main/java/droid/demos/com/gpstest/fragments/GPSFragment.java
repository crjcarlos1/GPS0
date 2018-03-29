package droid.demos.com.gpstest.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import droid.demos.com.gpstest.R;

/**
 * Created by carlos on 09/03/2018.
 */

public class GPSFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = GPSFragment.class.getSimpleName();
    private static final int REQUEST_GPS_PERMISSION = 1;

    private TextView txvResultLocation;
    private Button btnGetLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gps_fragment, container, false);

        txvResultLocation = (TextView) view.findViewById(R.id.txvResultLocation);
        btnGetLocation = (Button) view.findViewById(R.id.btnGetLocation);

        btnGetLocation.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGetLocation:
                checkGPSpermission();
                break;
        }
    }

    private void checkGPSpermission() {

        if (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Log.e("TAG","3");
            getLocation();


        } else {
            Log.e("TAG","0");
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.e("TAG","1");
                //escrivimos porque se necesita el permiso
                Toast.makeText(getContext(), "Se necesita permiso GPS para obtener abicación", Toast.LENGTH_LONG).show();
            } else {
                Log.e("TAG","2");
                //No necesitamos explicacion, podemos solicitar permisos
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_GPS_PERMISSION);
            }
        }

    }


    private void getLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                double latitude=location.getLatitude();
                double longitude=location.getLongitude();

                txvResultLocation.setText("Latitud: " + latitude + "\nLongitude: " + longitude);

                Geocoder geocoder;
                List<Address> listAddress;

                geocoder=new Geocoder(getContext(), Locale.getDefault());

                try
                {
                    listAddress=geocoder.getFromLocation(latitude,longitude,1);
                    String address = listAddress.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = listAddress.get(0).getLocality();
                    String state = listAddress.get(0).getAdminArea();
                    String country = listAddress.get(0).getCountryName();
                    String postalCode = listAddress.get(0).getPostalCode();
                    String knownName = listAddress.get(0).getFeatureName(); // Only if available else return NULL
                    txvResultLocation.setText("Latitud: " + latitude + "\nLongitude: " + longitude+"\n\n" +
                            "Address: "+address+"\n\n"+
                            "city: "+city+"\n\n"+
                            "state: "+state+"\n\n"+
                            "country: "+country+"\n\n"+
                            "postalCode: "+postalCode+"\n\n"+
                            "");
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } else {
            Toast.makeText(getContext(), "Conseder permisos de ubicación", Toast.LENGTH_LONG).show();
        }

    }

}
