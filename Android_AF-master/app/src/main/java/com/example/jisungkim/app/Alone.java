package com.example.jisungkim.app;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Alone extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener, MapView.POIItemEventListener {

    ArrayList<MyItem> arItem;
    String text;
    String text2;
    String text3;
    String text4;

    String name[]= {"A", "B","C","D"};

    double lat[] = {37.5913103, 37.591361, 37.588369, 37.591079};
    double lon[] = {127.0199425, 127.019481, 127.022764, 127.027215};
    int a;


    boolean clickCheck = false;
    @BindView(R.id.fab_gps) FloatingActionButton fab_gps;

    private static final String LOG_TAG = "LocationDemoActivity";

    private MapView mMapView;
    private MapPOIItem mDefaultMarker;



    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.custom_callout_ballon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            ((ImageView) mCalloutBalloon.findViewById(R.id.badge)).setImageResource(R.drawable.ic_launcher);
            ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText(poiItem.getItemName());
            ((TextView) mCalloutBalloon.findViewById(R.id.desc)).setText("별점OR거리OR가격");
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alone);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("맛집GO!");

        ButterKnife.bind(this);

        fab_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
            }
        });
        mMapView = (MapView) findViewById(R.id.map_view);
        mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        mMapView.setMapViewEventListener(this);
        mMapView.setPOIItemEventListener(this);
        mMapView.setCurrentLocationEventListener(this);
        createDefaultMarker(mMapView);
        showAll();

        Intent intent= getIntent();
        final ArrayList<MyItem> arItem=new ArrayList();

        String text = intent.getStringExtra("a1");
        String text2=intent.getStringExtra("a2");
        String text3=intent.getStringExtra("a3");
        String text4=intent.getStringExtra("a4");


        arItem.add(new MyItem("A",text,"2인 2만원대"));
        arItem.add(new MyItem("B",text2,"2인 1만원대"));
        arItem.add(new MyItem("C",text3,"2인 2만원대"));
        arItem.add(new MyItem("D",text4,"2인 3만원대"));

        final MyListAdapter MyAdapter = new MyListAdapter(this, R.layout.list,arItem);
        final ListView MyList;
        MyList=(ListView) findViewById(R.id.list_item);
        MyList.setAdapter(MyAdapter);

        MyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/**
 * ListView의 Item을 Click 할 때 수행할 동작
 * @param parent 클릭이 발생한 AdapterView.
 * @param view 클릭 한 AdapterView 내의 View(Adapter에 의해 제공되는 View).
 * @param position 클릭 한 Item의 position
 * @param id 클릭 된 Item의 Id
 */
                ImageButton btn = (ImageButton) view.findViewById(R.id.btn);

         if(btn.getVisibility()==View.INVISIBLE) {
//지도마커 연동
             MapPOIItem[] poiItems = mMapView.getPOIItems();

             if(poiItems.length > 0) {
                 mMapView.selectPOIItem(poiItems[position], false);
             }

             btn.setVisibility(View.VISIBLE);
             btn.setFocusable(false);
         }
         else {
             MapPOIItem[] poiItems = mMapView.getPOIItems();
             if(poiItems.length > 0) {
                 mMapView.deselectPOIItem(poiItems[position]);
             }
             btn.setVisibility(View.INVISIBLE);
             btn.setFocusable(false);
         }
            }
        });

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(Alone.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mMapView.setShowCurrentLocationMarker(false);
    }
    public void mmOnClick(View v){
        ImageButton btn=(ImageButton)findViewById(R.id.btn);
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://store.naver.com/restaurants/detail?id=1630366010"));
        startActivity(intent);
    }

    public void mOnClick(View v){

        switch(v.getId()){

            case R.id.rankbtn:
                arItem=new ArrayList<MyItem>();
                MyItem mi1;
                mi1=new MyItem("A",text2,"2인 3만원대");arItem.add(mi1);
                mi1=new MyItem("B",text,"2인 2만원대");arItem.add(mi1);
                mi1=new MyItem("C",text4,"2인 1만원대");arItem.add(mi1);
                mi1=new MyItem("D",text3,"2인 2만원대");arItem.add(mi1);

                MyListAdapter MyAdapter1 = new MyListAdapter(this, R.layout.list,arItem);
                ListView MyList1;
                MyList1=(ListView) findViewById(R.id.list_item);
                MyList1.setAdapter(MyAdapter1);
                break;

            case R.id.lengthbtn:
                arItem=new ArrayList<MyItem>();
                MyItem mi2;
                mi2=new MyItem("A",text3,"2인 5만원대");arItem.add(mi2);
                mi2=new MyItem("B",text,"2인 3만원대");arItem.add(mi2);
                mi2=new MyItem("C",text2,"2인 1만원대");arItem.add(mi2);
                mi2=new MyItem("D",text4,"2인 2만원대");arItem.add(mi2);

                MyListAdapter MyAdapter2 = new MyListAdapter(this, R.layout.list,arItem);
                ListView MyList2;
                MyList2=(ListView) findViewById(R.id.list_item);
                MyList2.setAdapter(MyAdapter2);
                break;

            case R.id.pricebtn:
                arItem=new ArrayList<MyItem>();
                MyItem mi3;
                mi3=new MyItem("A",text3,"2인 3만원대");arItem.add(mi3);
                mi3=new MyItem("B",text4,"2인 6만원대");arItem.add(mi3);
                mi3=new MyItem("C",text,"2인 2만원대");arItem.add(mi3);
                mi3=new MyItem("D",text2,"2인 2만원대");arItem.add(mi3);

                MyListAdapter MyAdapter3 = new MyListAdapter(this, R.layout.list,arItem);
                ListView MyList3;
                MyList3=(ListView) findViewById(R.id.list_item);
                MyList3.setAdapter(MyAdapter3);
                break;


        }

    }
       @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
    }


    private void createDefaultMarker(MapView mapView) {


        for(int i=0 ; i < 4 ; i++) {


            mDefaultMarker = new MapPOIItem();
            mDefaultMarker.setItemName(name[i]);
            mDefaultMarker.setTag(i);
            mDefaultMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat[i], lon[i]));
            mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
            mDefaultMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

            mapView.addPOIItem(mDefaultMarker);
            mapView.selectPOIItem(mDefaultMarker, true);
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat[i], lon[i]), false);
        }
    }

    private void showAll() {
        int padding = 20;
        float minZoomLevel = 7;
        float maxZoomLevel = 10;
        MapPointBounds bounds = new MapPointBounds(MapPoint.mapPointWithGeoCoord(lat[0], lon[0]), MapPoint.mapPointWithGeoCoord(lat[3], lon[3]));
        mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(bounds, padding, minZoomLevel, maxZoomLevel));
    }


    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {}

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {}

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {}

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {}

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {}

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {}

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {}

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {}

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {}

    public void findPOIItemByTag(int tag){
        mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.RedPin);
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) { }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) { }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(Alone.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            case R.id.action_settings:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
