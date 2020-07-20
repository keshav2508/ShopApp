package com.example.shop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Fragment_1 extends Fragment {
    GoogleMap gmap;
    int flag=0;
    Marker finalMarker;

    public Fragment_1() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fragment_1, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_1_map);
        assert mapFragment != null;
        mapFragment.getMapAsync(googleMap -> {
            gmap = googleMap;
            gmap.setMinZoomPreference(12);
            gmap.setIndoorEnabled(true);
            UiSettings uiSettings = gmap.getUiSettings();
            uiSettings.setIndoorLevelPickerEnabled(true);
            uiSettings.setMyLocationButtonEnabled(true);
            uiSettings.setMapToolbarEnabled(true);
            uiSettings.setCompassEnabled(true);
            uiSettings.setZoomControlsEnabled(true);
            LatLng ny = new LatLng(28.63576, 77.22445);

            gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
            final Marker[] currentMarker = {null};

            gmap.setOnMapClickListener(latLng -> {

                if (currentMarker[0] != null) {
                    currentMarker[0].remove();
                    currentMarker[0] = null;
                }
                gmap.clear();

                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                double latitude = latLng.latitude;
                double longitude = latLng.longitude;
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert addresses != null;
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String zip = addresses.get(0).getPostalCode();
                String country = addresses.get(0).getCountryName();

                currentMarker[0] = gmap.addMarker(new MarkerOptions().position(latLng).title(address));
                finalMarker = currentMarker[0];
                flag=1;


            });
        });
        Button fragment_1_set_btn = rootView.findViewById(R.id.fragment_1_set_button);
        fragment_1_set_btn.setOnClickListener(v -> {
            if(flag==0){
                showToast("Set Location");
            }else{
                String address_set = (String) finalMarker.getTitle();
                LatLng ll =(LatLng) finalMarker.getPosition();

                SharedPreferences sp = Objects.requireNonNull(this.getActivity()).getSharedPreferences("pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("location",address_set);
                //editor.putString("address_set",address_set);
                editor.apply();
                Registration_page var = (Registration_page)getActivity();
                assert var != null;
                var.viewPager.setCurrentItem(2);
            }

        });


        SearchView search = rootView.findViewById(R.id.fragment_1_searchView);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onQueryTextSubmit(String query) {

                Geocoder geocoder = new Geocoder(getActivity());
                List<Address> addresses;

                try {
                    addresses = geocoder.getFromLocationName( query  , 3);
                    if (addresses != null && !addresses.equals("")) {

                        Address address = (Address) addresses.get(0);
                        double home_long = address.getLongitude();
                        double home_lat = address.getLatitude();
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                        /*String addressText = String.format(
                                "%s, %s",
                                address.getMaxAddressLineIndex() > 0 ? address
                                        .getAddressLine(0) : "", address.getCountryName());

                         */
                        String add = address.getAddressLine(0);

                        MarkerOptions markerOptions = new MarkerOptions();

                        markerOptions.position(latLng);
                        markerOptions.title(add);

                        gmap.clear();

                        finalMarker = gmap.addMarker(markerOptions);
                        flag=1;
                        gmap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        gmap.animateCamera(CameraUpdateFactory.zoomTo(15));
                        TextView locationTv = (TextView) rootView.findViewById(R.id.fragment_1_lat_long);
                        locationTv.setText("Latitude:" + address.getLatitude() + ", Longitude:"
                                + address.getLongitude());
                    }

                } catch (Exception ignored) {

                }
                return true;

            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return rootView;
    }
    public void showToast(String toastText) {
        Toast.makeText(getActivity(), toastText, Toast.LENGTH_SHORT).show();
    }
}