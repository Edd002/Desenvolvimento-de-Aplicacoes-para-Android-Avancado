package com.example.app.aula03_googlemapssnippets

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
import com.example.app.aula03_googlemapssnippets.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.*
import java.io.IOException
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap;
    private lateinit var binding: ActivityMapsBinding;
    private var locationManager: LocationManager? = null; // Responsável por gerenciar / configurar o rastreamento da geolocalização
    private var locationListener: LocationListener? = null; // Responsável por "rastrear" eventos relacionados a geolocalização
    private var usermaker: Marker? = null;

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

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this);
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap;

        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID;

        locationListener = LocationListener { location ->
            val usrPosition = LatLng(location.latitude, location.longitude);

            if (usermaker != null) {
                usermaker!!.remove();
            } // Remover marcadores anteriores

            usermaker = mMap.addMarker(
                MarkerOptions()
                    .position(usrPosition)
                    .title("Minha localização")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.home))
            );

            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    usrPosition,
                    19.0f // Zoom de 2 até 21
                )
            );
        }

        checkPermission();
        setupLocation();

        // Add a marker in Sydney and move the camera
        val puc = LatLng(-19.9332786, -43.9371484);
        mMap.addMarker(
            MarkerOptions().position(puc)
                .title("PUC Minas")
                .snippet("Unidade Pça. da Liberdade")
                .icon(
                    BitmapDescriptorFactory
                        .fromResource(R.drawable.home)
                )
        );

        // Zoom between 2 and 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(puc, 19.0f));

        mMap.setOnMapLongClickListener { latLang ->
            val latitude = latLang.latitude
            val longitude = latLang.longitude

            Toast.makeText(
                applicationContext,
                "Latitude: $latitude e Longitude: $longitude",
                Toast.LENGTH_SHORT
            ).show();

            mMap.addMarker(
                MarkerOptions().position(latLang)
                    .title("Ponto Adicional")
                    .icon(
                        BitmapDescriptorFactory
                            .fromResource(R.drawable.heliponto)
                    )
            );

            mMap.addCircle(
                CircleOptions()
                    .center(latLang)
                    .radius(250.0)
                    .strokeWidth(5.0f)
                    .strokeColor(Color.WHITE)
                    .fillColor(Color.argb(110, 100, 200, 200))
            );

            val polylineOptions = PolylineOptions();
            polylineOptions.add(latLang);
            polylineOptions.add(puc);
            polylineOptions.color(Color.GREEN).width(20.0f);
            mMap.addPolyline(polylineOptions);

            val results = FloatArray(1);
            Location.distanceBetween(
                puc.latitude,
                puc.longitude,
                latLang.latitude,
                latLang.longitude,
                results
            );
            Toast.makeText(
                applicationContext,
                "Distância do ponto até a PUC: ${results[0]}m",
                Toast.LENGTH_LONG
            ).show();
        }

        val polygonOptions = PolygonOptions();
        polygonOptions.add(LatLng(-19.9327225, -43.9388156));
        polygonOptions.add(LatLng(-19.9310411, -43.9383305));
        polygonOptions.add(LatLng(-19.9313083, -43.9372501));
        polygonOptions.add(LatLng(-19.9329917, -43.9376885));
        polygonOptions.strokeWidth(5.0f).strokeColor(Color.WHITE)
            .fillColor((Color.argb(110, 100, 200, 200)));
        mMap.addPolygon(polygonOptions);
    }

    /* Geocoding(Geocodificação): Transformação de endereço ou nome do local em coordenadas (latitude e longitude) */
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

    /* Reverse Geocoding(geocodificaçãoreversa): Transformação de coordenadas (latitude e longitude) em endereço ou descrição do local. */
    private fun reverseGeocodiing(latLng: LatLng): String? {
        val geocoder = Geocoder(applicationContext, Locale.getDefault());  //Locale representa uma região específica.
        try {
            val local = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (local != null && local.size > 0) {
                return local[0].getAddressLine(0).toString();
            }
        } catch (e: IOException) {
            e.message;
        }
        return null;
    }

    // 1) Validar permissões em tempo de execução (necessário para API 23 ou superior)
    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissões Ativadas", Toast.LENGTH_SHORT).show();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                /* Em uma IU educacional, explique ao usuário por que seu aplicativo requer esta permissão para um recurso específicose comportar conforme o
                esperado. Nesta IU, inclua um botão "cancelar" ou "não, obrigado" que permite ao usuário continue usando seu aplicativo sem conceder a permissão. */
                alertaPermissaoNegada();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                //Pedir permissão diretamente. O ActivityResultCallbackregistrado obtém o resultado desta solicitação (abaixo).
            }
        }
    }

    // 2) Calbackque exibe a janela de solicitação de permissão
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        isGranted: Boolean ->
        if (isGranted) {
            setupLocation(); // Método acoplado para setara localização
        }
    }

    // 3) Exibir IU educacional (recomendação do Google)
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
                // Nunca osaro comendo finish(). Fechar o app é uma prática pouco recomendada.
        }
        val alertDialog = alert.create();
        alertDialog.show();
    }

    // 4) Configurar a Geolocalização.
    private fun setupLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            /* requestLocationUpdates(Stringprovider, longminTimeMs, floatminDistanceM, LocationListenerlistener)
            1) provider-String: um provedor listado por getAllProviders() Este valor não pode ser null.(LocationManager.GPS_PROVIDERneste caso)
            2) miTimeMs-long: Intervalo mínimo de tempo entre as atualizações de localização em milissegundos(1000 msneste caso)
            3) minDistanceM: float: distância mínima entre atualizações de localização em metros
            4) listener: LocationListener: o ouvinte que receberá atualizações de localização Este valor não pode ser null. */
            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager;
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10f, locationListener!!);
        }
    }
}