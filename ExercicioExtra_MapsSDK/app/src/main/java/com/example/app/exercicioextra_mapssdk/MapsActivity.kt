package com.example.app.exercicioextra_mapssdk

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.app.exercicioextra_mapssdk.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.*
import java.io.IOException
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap;
    private lateinit var binding: ActivityMapsBinding;
    private var locationManager: LocationManager? = null;
    private var locationListener: LocationListener? = null;
    private var usermaker: Marker? = null;
    private var searchedmarker: Marker? = null;
    private var radiusmarker: Marker? = null;
    private var usrPosition: LatLng? = null;
    private var polylineDistanceSearchedAndActualPositions: Polyline? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(layoutInflater);

        binding.buttonAdd.setOnClickListener() {
            if (binding.editTextAdd.text.toString() != "") {
                var geoloc = geocoding(binding.editTextAdd.text.toString());
                if (geoloc != null) {
                    Toast.makeText(
                        applicationContext,
                        "Latitude: ${geoloc?.latitude}\nLongitude: ${geoloc?.longitude}",
                        Toast.LENGTH_LONG
                    ).show();

                    if (searchedmarker != null) {
                        searchedmarker!!.remove();
                    }

                    val searchedPosition = LatLng(geoloc?.latitude, geoloc?.longitude);
                    searchedmarker = mMap.addMarker(
                        MarkerOptions()
                            .position(searchedPosition)
                            .title("Localização pesquisada")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.drone))
                    );

                    if (polylineDistanceSearchedAndActualPositions != null) {
                        polylineDistanceSearchedAndActualPositions!!.remove();
                    }

                    if (searchedPosition != null && usrPosition != null) {
                        val distance = FloatArray(1);
                        Location.distanceBetween(
                            searchedPosition.latitude,
                            searchedPosition.longitude,
                            usrPosition!!.latitude,
                            usrPosition!!.longitude,
                            distance
                        );

                        val polylineOptions = PolylineOptions();
                        polylineOptions.add(searchedPosition);
                        polylineOptions.add(usrPosition);
                        polylineOptions.color(if (distance[0] > 500.0f) Color.RED else Color.GREEN).width(20.0f);
                        polylineDistanceSearchedAndActualPositions = mMap.addPolyline(polylineOptions);
                    }

                    mMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            searchedPosition,
                            17.0f
                        )
                    );
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Local não encontrado.",
                        Toast.LENGTH_LONG
                    ).show();
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Informe o nome do local.",
                    Toast.LENGTH_LONG
                ).show();
            }
        }

        setContentView(binding.root);
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment;
        mapFragment.getMapAsync(this);
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap;
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID;

        locationListener = LocationListener { location ->
            usrPosition = LatLng(location.latitude, location.longitude);

            if (usermaker != null) {
                usermaker!!.remove();
            }

            usermaker = mMap.addMarker(
                MarkerOptions()
                    .position(usrPosition!!)
                    .title("Minha localização")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.home))
            );

            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    usrPosition,
                    17.0f
                )
            );
        }

        checkPermission();
        setupLocation();

        mMap.setOnMapLongClickListener { latLang ->
            radiusmarker = mMap.addMarker(
                MarkerOptions().position(latLang)
                    .title("Heliponto - Raio 2km")
                    .icon(
                        BitmapDescriptorFactory
                            .fromResource(R.drawable.heliponto)
                    )
            );

            var circle = CircleOptions()
                .center(latLang)
                .radius(2000.0)
                .strokeWidth(5.0f)
                .strokeColor(Color.WHITE)
                .fillColor(Color.argb(110, 100, 200, 200));
            mMap.addCircle(circle);
        }
    }

    private fun geocoding(descricaoLocal: String): LatLng? {
        val geocoder = Geocoder(applicationContext, Locale.getDefault());
        try {
            val local = geocoder.getFromLocationName(descricaoLocal, 1);
            if (local != null && local.size > 0) {
                var destino = LatLng(local[0].latitude, local[0].longitude);
                return destino;
            }
        } catch (e: IOException) {
            e.message;
        }
        return null;
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissões Ativadas", Toast.LENGTH_SHORT).show();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                alertaPermissaoNegada();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        isGranted: Boolean ->
        if (isGranted) {
            setupLocation();
        }
    }

    private fun alertaPermissaoNegada() {
        val alert = AlertDialog.Builder (this);
        alert.setTitle("Permissões Requeridas");
        alert.setMessage("Para continuar utilizando todos os recursos do aplicativo, é altamente recomendado autorizar o acesso a sua localização.");
        alert.setCancelable(false);
        alert.setPositiveButton("Corrigir") {
                dialog, which -> requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        alert.setNegativeButton("Cancelar") {
                dialog, which ->
                Toast.makeText(getApplicationContext(), "Algumas das funcionalidades do app foram desabilitadas.", Toast.LENGTH_LONG).show();
        }
        val alertDialog = alert.create();
        alertDialog.show();
    }

    private fun setupLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager;
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10f, locationListener!!);
        }
    }
}