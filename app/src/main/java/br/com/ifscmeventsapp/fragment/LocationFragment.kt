package br.com.ifscmeventsapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ifscmeventsapp.R
import br.com.ifscmeventsapp.model.Event
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationFragment : Fragment(), OnMapReadyCallback {

    private var latitude : Double = 0.0
    private var longitude : Double = 0.0
    private var mMapView : MapView? = null
    private var event : Event? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMapView?.onSaveInstanceState(outState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_location, container, false)

        val event = arguments?.getSerializable("event") as Event?
        if(event != null) {
            latitude = event.latitude.toDouble()
            longitude = event.longitude.toDouble()
            this.event = event
        }

        mMapView = view?.findViewById(R.id.mapViewEvent) as MapView
        mMapView?.onCreate(savedInstanceState)
        mMapView?.getMapAsync(this)

        return view
    }

    override fun onResume() {
        super.onResume()
        mMapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView?.onPause()
    }

    override fun onStart() {
        super.onStart()
        mMapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMapView?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView?.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val newLatLng = LatLng(latitude, longitude)
        googleMap?.addMarker(MarkerOptions().position(newLatLng).title(event?.name))?.showInfoWindow()
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 17F))
        googleMap?.uiSettings?.isZoomControlsEnabled = true
        googleMap?.uiSettings?.isMyLocationButtonEnabled = true
        googleMap?.uiSettings?.isZoomGesturesEnabled = true
    }
}