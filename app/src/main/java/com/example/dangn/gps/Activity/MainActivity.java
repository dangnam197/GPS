package com.example.dangn.gps.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dangn.gps.Adapter.AdapterCityWeather;
import com.example.dangn.gps.Adapter.PagerAdapter;
import com.example.dangn.gps.Api.ApiWeather;
import com.example.dangn.gps.Fragment.FragmentWeatherNextdays;
import com.example.dangn.gps.Model.CityWeather;
import com.example.dangn.gps.Model.CityWeatherList;
import com.example.dangn.gps.Fragment.FragmentWeathToday;
import com.example.dangn.gps.Model.Weather;
import com.example.dangn.gps.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener,ApiWeather.UpdatateView, android.support.v7.widget.SearchView.OnQueryTextListener,AdapterCityWeather.onClickListener {

    private Location location;
    private GoogleApiClient googleApiClient;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private LocationRequest locationRequest;
    private static final long UPDATE_INTERVAL = 5000, FASTEST_INTERVAL = 5000; // = 5 seconds

    private double lat;
    private double lon;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private Weather weather;
    private ArrayList<Weather> listWeathers;
    // integer for permissions results request
    private static final int ALL_PERMISSIONS_RESULT = 1011;
    private Button button;

    private RecyclerView recyclerViewCity;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<Fragment> listFragment;
    private ArrayList<String> listTitle;
    private boolean isVisibility=true;
    private ApiWeather apiWeather;
    private CityWeatherList cityWeatherList;
    private AdapterCityWeather adapterCityWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar();
        recylerCityWeather();
        initFrament();
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionsToRequest = permissionsToRequest(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), 111);
                requestPermissions(permissionsToRequest.toArray(
                        new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }
        googleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();

//        final FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }


    private void Toolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }
    private void recylerCityWeather(){
        cityWeatherList = new CityWeatherList(getApplicationContext());
        recyclerViewCity = findViewById(R.id.rv_cityweather);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewCity.setLayoutManager(linearLayoutManager);
        recyclerViewCity.addItemDecoration(new DividerItemDecoration(recyclerViewCity.getContext(), DividerItemDecoration.VERTICAL));
    }
    private Location getLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return l;
        }

        return null;
    }

    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wantedPermissions) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!checkPlayServices()) {
            //locationTv.setText("You need to install Google Play Services to use the App properly");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // stop location updates
        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_view,menu);
        MenuItem searchItem = menu.findItem(R.id.search_view);

        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
        searchView.setQueryHint("Nhập tỉnh thành");
        searchView.setOnQueryTextListener(this);
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                visibleRecycler();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                invisibleRecycler();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.seach){
//            Intent intent = new Intent(MainActivity.this,SeachActivity.class);
//            startActivity(intent);
//            if(isVisibility) {
//                viewPager.setVisibility(View.INVISIBLE);
//                tabLayout.setVisibility(View.INVISIBLE);
//                isVisibility=false;
//            }else {
//                viewPager.setVisibility(View.VISIBLE);
//                tabLayout.setVisibility(View.VISIBLE);
//                isVisibility=true;
//            }
//            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
            } else {
                finish();
            }

            return false;
        }

        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Permissions ok, we get last location
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (location != null) {
            //locationTv.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());

            if(apiWeather==null){
                Log.d("location", "Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());
                apiWeather =  new ApiWeather(location.getLatitude(),location.getLongitude(),getApplicationContext(),this);
                apiWeather.getWeatherCurrent();
                apiWeather.getWeatherNextDays();
            }

        }

        startLocationUpdates();
    }

    private void startLocationUpdates() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
           // locationTv.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perm : permissionsToRequest) {
                    if (!hasPermission(perm)) {
                        permissionsRejected.add(perm);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            new AlertDialog.Builder(MainActivity.this).
                                    setMessage("These permissions are mandatory to get your location. You need to allow them.").
                                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.
                                                        toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    }).setNegativeButton("Cancel", null).create().show();

                            return;
                        }
                    }
                } else {
                    if (googleApiClient != null) {
                        googleApiClient.connect();
                    }
                }

                break;
        }
    }
    public void initFrament(){
        weather = new Weather();
        listWeathers = new ArrayList<>();
    }
    private void setDataFrament(){
        listFragment = new ArrayList<>();
        listTitle = new ArrayList<>();
        listFragment.add(new FragmentWeathToday(weather));
        listFragment.add(new FragmentWeatherNextdays(listWeathers));
        listTitle.add("Hiện tại");
        listTitle.add("Những ngày tới");
        viewPager = findViewById(R.id.vp_test);
        tabLayout = findViewById(R.id.tl_test);
        if(!isVisibility){
            viewPager.setVisibility(View.INVISIBLE);
            tabLayout.setVisibility(View.INVISIBLE);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        PagerAdapter pagerAdapter = new PagerAdapter(fragmentManager,listFragment,listTitle);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @Override
    public void update(Weather weather) {
        Toast.makeText(this,weather.getWindSpeed()+"",Toast.LENGTH_LONG).show();
        this.weather = weather;
        setDataFrament();
    }

    @Override
    public void updateNextDays(ArrayList<Weather> listNextDays) {
        this.listWeathers = listNextDays;
        Toast.makeText(this,listNextDays.get(0).getWeatherMain()+"",Toast.LENGTH_LONG).show();
        setDataFrament();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(getApplicationContext(),query+" submit",Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.length()>1){

            adapterCityWeather = new AdapterCityWeather(getApplicationContext(),cityWeatherList.seachCity(newText));
            adapterCityWeather.setOnclickListener(this);
            recyclerViewCity.setAdapter(adapterCityWeather);
        }
        return false;
    }
    private void invisibleRecycler(){
        if(!isVisibility&&viewPager!=null&&tabLayout!=null){
            viewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
        }
        isVisibility = true;
        recyclerViewCity.setVisibility(View.INVISIBLE);
    }
    private void visibleRecycler(){
        if(isVisibility&&viewPager!=null&&tabLayout!=null){
            viewPager.setVisibility(View.INVISIBLE);
            tabLayout.setVisibility(View.INVISIBLE);
        }
        isVisibility = false;
        recyclerViewCity.setVisibility(View.VISIBLE);
    }

    @Override
    public void itemClick(View v, int poisition,CityWeather cityWeather) {
        lat = cityWeather.getLat();
        lon = cityWeather.getLon();
        apiWeather = new ApiWeather(lat,lon,getApplicationContext(),this);
        apiWeather.getWeatherCurrent();
        apiWeather.getWeatherNextDays();
        toolbar.collapseActionView();
    }
}
