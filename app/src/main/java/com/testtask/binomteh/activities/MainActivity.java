package com.testtask.binomteh.activities;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.testtask.binomteh.R;
import com.testtask.binomteh.data.Data;
import com.testtask.binomteh.data.User;
import com.testtask.binomteh.fragments.DetailsFragment;
import com.testtask.binomteh.overlay.DetailsOverlayItem;
import com.testtask.binomteh.overlay.IconOverlayItem;
import com.testtask.binomteh.overlay.OverlayItemFactory;
import com.testtask.binomteh.overlay.TypeOfOverlayItem;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String ZOOM_MAP = "MainActivity.ZOOM_MAP";
    private static final String LATITUDE = "MainActivity.LATITUDE";
    private static final String LONGITUDE = "MainActivity.LONGITUDE";

    private static final int DEFAULT_MIN_ZOOM_LEVEL = 3;
    private static final int DEFAULT_ZOOM = 3;

    private static final int ANIMATION_DURATION_BUTTON = 300;
    private static final int ANIMATION_REPEAT = 1;

    private Context mContext;
    private List<User> mUsersList;
    private MapView mMapView;
    private MyLocationNewOverlay mLocationOverlay;
    private IMapController mMapController;
    private ArrayList<OverlayItem> items;

    private ImageButton mImgButZoomPlus;
    private ImageButton mImgButZoomMinus;
    private ImageButton mImgButMyLocation;
    private ImageButton mImgButNextTracker;

    private int positionOfTracker = 0;

    private DetailsFragment mFragmentDetails;
    private FragmentTransaction mFragmentTransaction;

    private OverlayItemFactory overlayItemFactory;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        Configuration.getInstance().load(mContext, PreferenceManager.getDefaultSharedPreferences(mContext));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }

        setContentView(R.layout.main_activity);

        mUsersList = new ArrayList<>();
        overlayItemFactory = new OverlayItemFactory();
        initData();
        mMapView.getOverlays().add(initItemizedIconOverlay(items));
    }

    private void initData() {
        Data data = new Data(mContext);
        mUsersList = data.getData();
        initView();
    }

    private void initView() {
        mMapView = (MapView) findViewById(R.id.map_view);
        mMapView.setTilesScaledToDpi(true);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);
        mMapView.setMinZoomLevel(DEFAULT_MIN_ZOOM_LEVEL);

        mMapController = mMapView.getController();
        mMapController.setZoom(DEFAULT_ZOOM);
        mMapController.setCenter(mUsersList.get(positionOfTracker).getGeoPoint());

        mFragmentDetails = new DetailsFragment();

        mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()), mMapView);
        mLocationOverlay.enableMyLocation();
        mLocationOverlay.setPersonIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_mylocation_55dp));
        mLocationOverlay.setDirectionArrow(BitmapFactory.decodeResource(getResources(), R.drawable.ic_mylocation_55dp),
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_my_tracker_46dp));

        mMapView.getOverlays().add(mLocationOverlay);

        mImgButZoomPlus = (ImageButton) findViewById(R.id.zoom_plus_button);
        mImgButZoomPlus.setOnClickListener(v -> {
            animateButton(mImgButZoomPlus);
            mMapView.getController().zoomIn();
        });

        mImgButZoomMinus = (ImageButton) findViewById(R.id.zoom_minus_button);
        mImgButZoomMinus.setOnClickListener(v -> {
            animateButton(mImgButZoomMinus);
            mMapView.getController().zoomOut();
        });

        mImgButMyLocation = (ImageButton) findViewById(R.id.my_location_button);
        mImgButMyLocation.setOnClickListener(v -> {
            animateButton(mImgButMyLocation);
            if (mLocationOverlay.getMyLocation() != null) {
                mMapView.getController().animateTo(mLocationOverlay.getMyLocation());
            }
        });

        mImgButNextTracker = (ImageButton) findViewById(R.id.next_traker_button);
        mImgButNextTracker.setOnClickListener(v -> {
            animateButton(mImgButNextTracker);
            mMapController.animateTo(mUsersList.get(nextTracker()).getGeoPoint());
            if (mFragmentDetails.isVisible()) {
                sendDataToFragment(mFragmentDetails, positionOfTracker);
                mFragmentDetails.animationForChangingData();
            }
        });

        initEventOnClickOnMap();
    }

    private void animateButton(View v) {
        YoYo.with(Techniques.Pulse)
                .duration(ANIMATION_DURATION_BUTTON)
                .repeat(ANIMATION_REPEAT)
                .playOn(v);
    }

    private void initEventOnClickOnMap() {
        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                if (mFragmentDetails.isVisible()) {
                    mFragmentTransaction = getFragmentManager().beginTransaction();
                    mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    mFragmentTransaction.remove(mFragmentDetails);
                    mFragmentTransaction.addToBackStack(null);
                    mFragmentTransaction.commit();
                }
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };

        MapEventsOverlay OverlayEvents = new MapEventsOverlay(getBaseContext(), mReceive);
        mMapView.getOverlays().add(OverlayEvents);

        initOverlayItemList();
    }

    private void initOverlayItemList() {
        items = new ArrayList<>();
        for (User user : mUsersList) {
            items.add(overlayItemFactory.getOverlayItem(user, IconOverlayItem.ICON_TYPE_OVERLAY_ITEM, mContext));
            items.add(overlayItemFactory.getOverlayItem(user, DetailsOverlayItem.DETAILS_TYPE_OVERLAY_ITEM, mContext));
        }
    }

    private int nextTracker() {
        if (positionOfTracker < mUsersList.size() - 1) {
            positionOfTracker++;
        } else {
            positionOfTracker = 0;
        }
        return positionOfTracker;
    }

    private ItemizedIconOverlay initItemizedIconOverlay(List<org.osmdroid.views.overlay.OverlayItem> items) {
        return new ItemizedIconOverlay<>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        TypeOfOverlayItem typeItem = (TypeOfOverlayItem) item;
                        if (typeItem.getType() == IconOverlayItem.ICON_TYPE_OVERLAY_ITEM) {
                            sendDataToFragment(mFragmentDetails, Integer.parseInt(item.getUid()));

                            if (mFragmentTransaction == null || !mFragmentDetails.isVisible()) {
                                mFragmentTransaction = getFragmentManager().beginTransaction();
                                mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                mFragmentTransaction.replace(R.id.details_frame_layout, mFragmentDetails);
                                mFragmentTransaction.addToBackStack(null);
                                mFragmentTransaction.commit();
                            } else {
                                mFragmentDetails.animationForChangingData();
                            }
                        }
                        return true;
                    }

                    public boolean onItemLongPress(final int index, final org.osmdroid.views.overlay.OverlayItem item) {
                        return false;
                    }
                }, this);
    }

    private void sendDataToFragment(DetailsFragment detailsFragment, int positionInUserList) {
        detailsFragment.setIconUser(mUsersList.get(positionInUserList).getPhoto());
        detailsFragment.setUsername(mUsersList.get(positionInUserList).getName());
        detailsFragment.setStatusGps(mUsersList.get(positionInUserList).getStatusGPS());
        detailsFragment.setDate(mUsersList.get(positionInUserList).getDate());
        detailsFragment.setTime(mUsersList.get(positionInUserList).getTime());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ZOOM_MAP, mMapView.getZoomLevel());
        outState.putDouble(LATITUDE, mMapView.getMapCenter().getLatitude());
        outState.putDouble(LONGITUDE, mMapView.getMapCenter().getLongitude());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMapController.setZoom(savedInstanceState.getInt(ZOOM_MAP));
        GeoPoint geoPointCenterMap = new GeoPoint(savedInstanceState.getDouble(LATITUDE), savedInstanceState.getDouble(LONGITUDE));
        mMapController.setCenter(geoPointCenterMap);

    }
}
