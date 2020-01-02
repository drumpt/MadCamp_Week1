package com.example.recyclerview;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.http.HttpsConnection;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.Place;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class FragmentRestaurant2 extends Fragment implements OnMapReadyCallback, PlacesListener {
    View v;
    private ImageView refreshButton;
    private ImageView backButton;
    private TextView titleText;
    private TextView countText;
    LatLng currentLocation;
    static double latitude, longitude;

    final static int MAX_COUNT = 10;
    final static int RADIUS = 1000;

    private GoogleMap mMap;
    private MapView mapView = null;
    List<MarkerOptions> previous_markerOptions = new ArrayList<>();

    Calendar calendar = Calendar.getInstance();
    int month = Calendar.getInstance().get(Calendar.MONTH); // month+1
    int date = Calendar.getInstance().get(Calendar.DATE);
    int year = Calendar.getInstance().get(Calendar.YEAR);
    private String dateString = month + "/" + date + "/" + year;

    private static final HashMap<Integer, String> int2Str = new HashMap<>();
    static {
        int2Str.put(1, "첫");
        int2Str.put(2, "두");
        int2Str.put(3, "세");
        int2Str.put(4, "네");
        int2Str.put(5, "다섯");
        int2Str.put(6, "여섯");
        int2Str.put(7, "일곱");
        int2Str.put(8, "여덟");
        int2Str.put(9, "아홉");
        int2Str.put(10, "열");
    }

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.restaurant2_fragment, container, false);
        final int count = readCount("myfile.txt");
        final int newCount = writeCount("myfile.txt");
        titleText = (TextView) v.findViewById(R.id.textView);
        titleText.setText("오늘의 " + int2Str.get(MAX_COUNT - newCount) + " 번째 맛집");
        countText = (TextView) v.findViewById(R.id.count_left);
        countText.setText("오늘 남은 재시도 횟수 : " + newCount);
        refreshButton = (ImageView) v.findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), Integer.toString(newCount), Toast.LENGTH_SHORT).show();
                if (count > 0) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    FragmentRestaurant2 fragmentRestaurant2 = new FragmentRestaurant2();
                    fragmentTransaction.replace(((ViewGroup) getView().getParent()).getId(), fragmentRestaurant2);
                    fragmentTransaction.commitAllowingStateLoss();
                } else Toast.makeText(getContext(), "남은 맛집이 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        backButton = (ImageView) v.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentRestaurant fragmentRestaurant = new FragmentRestaurant();
                fragmentTransaction.replace(((ViewGroup) getView().getParent()).getId(), fragmentRestaurant);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView = (MapView) v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.restaurant2_fragment);
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        fetchLastLocaton();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
//        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//        startActivity(intent);
//        if (lm == null) {
//            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(intent);
//        }
//        while (lm == null) { }

        Location lmLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (lmLocation == null) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
            lmLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        while (lmLocation == null) {
        } // Lock

        mMap.clear();
        if (previous_markerOptions != null) previous_markerOptions.clear();

        latitude = lmLocation.getLatitude();
        longitude = lmLocation.getLongitude();
        currentLocation = new LatLng(latitude, longitude);
        MapsInitializer.initialize(getContext());
        showPlaceInformation(currentLocation);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
    }

    @Override
    public void onPlacesFailure(PlacesException e) {
        Log.d("waeidortu234882940t", "failed!!!!!!!!!!!!!!!!!!!!!!");
    }

    @Override
    public void onPlacesStart() {

    }

    @Override
    public void onPlacesSuccess(final List<Place> places) {
        Log.d("!!!!!!!!!!!!!!!!!!!!!!!!!!!", "onplaceSuccesssssssssssss");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMap.clear();
                previous_markerOptions.clear();
                String str, receiveMsg;
                for (noman.googleplaces.Place place : places) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.title(place.getName()); // 가게 이름
                    LatLng latLng = new LatLng(place.getLatitude(), place.getLongitude()); // 가게 위치(경도, 위도)
                    markerOptions.position(latLng);
                    String markerSnippet = getCurrentAddress(latLng); // 가게 주소
                    markerOptions.snippet(markerSnippet);

//                    HttpURLConnection myConnection = null;
//                    try {
//                        String urlString = "https://maps.googleapis.com/maps/api/place/details/json?place_id="
//                                + place.getPlaceId()
//                                + "&fields=international_phone_number,rating,photo&key="
//                                + "AIzaSyBz7Wzq-hDppkuq3c6wfe73KipHyRyKTio";
//                        URL url = new URL(urlString);
//                        Log.d("3290578ew90r21***", urlString);
//                        myConnection = (HttpsURLConnection) url.openConnection();
//                        myConnection.setRequestProperty("User-Agent", "com.example.recyclerview");
//                        if (myConnection.getResponseCode() == myConnection.HTTP_OK) {
//                            InputStreamReader responseBodyReader = new InputStreamReader(myConnection.getInputStream(), "UTF-8");
//                            BufferedReader reader = new BufferedReader(responseBodyReader);
//                            StringBuffer buffer = new StringBuffer();
//                            while ((str = reader.readLine()) != null) {
//                                buffer.append(str);
//                            }
//                            receiveMsg = buffer.toString();
//                            Log.i("receiveMsg : ", receiveMsg);
//                            reader.close();
//                            Log.d("123345567789", receiveMsg);
//                        } else {
//
//                        }
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//
//                    } finally {
//
//                    }

                    // 별점, 사진 추가하면 좋음
                    Log.d("13907d0z9f*", place.getPlaceId());
                    Log.d("f235840", markerSnippet);

//                    Log.d("123rwsadg*3*2tr43*", places.toString());
//                    String url = "https://maps.googleapis.com/maps/api/place/search/json?radius="
//                            + RADIUS
//                            + "&sensor=false&key="
//                            + "AIzaSyBz7Wzq-hDppkuq3c6wfe73KipHyRyKTio"
//                            + "&location="
//                            + latitude
//                            + ","
//                            + longitude;


                    //                    Log.d("194503u125092", url);

                    previous_markerOptions.add(markerOptions);
                }
                // Distinct MarkerOptions
                HashSet<MarkerOptions> hashSet = new HashSet<>();
                hashSet.addAll(previous_markerOptions);
                previous_markerOptions.clear();
                previous_markerOptions.addAll(hashSet);

                Log.d("Succ***4*134*132*4132*4", Integer.toString(previous_markerOptions.size()));
                int index = new Random().nextInt(previous_markerOptions.size());

                Log.d("awefiofxzbvcnmllsdkf", Integer.toString(index));

                MarkerOptions restaurant = previous_markerOptions.get(index);

                Log.d("2390578qazvc134", restaurant.getTitle());

                mMap.addMarker(restaurant).showInfoWindow();
                mMap.setMyLocationEnabled(true);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(restaurant.getPosition()));
            }
        });
    }

    @Override
    public void onPlacesFinished() {

    }

    public void showPlaceInformation(LatLng location) {
        mMap.clear();
        if (previous_markerOptions != null) previous_markerOptions.clear();
        new NRPlaces.Builder()
                .listener(this)
                .key("AIzaSyBz7Wzq-hDppkuq3c6wfe73KipHyRyKTio")
                .latlng(location.latitude, location.longitude)
//                .latlng(36.363123, 127.358255)
                .radius(RADIUS) // Meter
                .type("restaurant")
                .build()
                .execute();
    }

    public String getCurrentAddress(LatLng latlng) {
        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(latlng.latitude, latlng.longitude, 1);
        } catch (IOException ioException) { // Network Problem
            Toast.makeText(getActivity(), "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(getActivity(), "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(getActivity(), "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }
    }

    public int readCount (String filename){
        try {
            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
            FileInputStream fis = getContext().openFileInput(filename);//파일명
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
            String str = buffer.readLine().trim(); // 파일에서 한줄을 읽어옴
            if (str == "") return MAX_COUNT; // 최초 실행
            else {
                String[] tmpList = str.split(" ");
                if (tmpList[0].equals(dateString)) return Integer.parseInt(tmpList[1]);
                else return MAX_COUNT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MAX_COUNT;
        }
    }

    public int writeCount (String filename){
        int previous_count = readCount(filename);
        String fullResult;
        try {
            if (previous_count == 0) return previous_count;
            else {
                FileOutputStream fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE);
                previous_count--;
                fullResult = dateString + " " + previous_count;
                PrintWriter out = new PrintWriter(fos);
                out.println(fullResult);
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return previous_count;
    }
}