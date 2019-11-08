package com.codemen.eaglemaps

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.*
import org.json.JSONObject
import com.google.android.gms.maps.GoogleMap as GoogleMap1


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MapsFragment : Fragment(), OnMapReadyCallback ,GoogleMap1.OnMarkerClickListener{



    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var mMap: GoogleMap1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        private const val  LOCATION_PERMISSION_REQUEST_CODE = 1

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MapsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onMapReady(googleMap: GoogleMap1) {

        mMap= googleMap

        val  bogota = LatLng(-34.0,151.0)
        mMap.addMarker(MarkerOptions().position(bogota).title("estoy en bogota"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bogota))
        mMap.setOnMarkerClickListener(this)
        mMap.uiSettings.isZoomControlsEnabled = true
       // mMap.isMyLocationEnabled = true

    }

    override fun onMarkerClick(p0: Marker?)=false

    private fun setupMap(){
        if ( ActivityCompat.checkSelfPermission(Activity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Activity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)

                if (ActivityCompat.shouldShowRequestPermissionRationale(Activity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {

                } else {
                    ActivityCompat.requestPermissions(Activity(),
                        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                        1)
                }

            if (ActivityCompat.shouldShowRequestPermissionRationale(Activity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(Activity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1)
            }

                return

        }
        mMap.isMyLocationEnabled = true
        mMap.setOnMyLocationChangeListener(GoogleMap1.OnMyLocationChangeListener(){
            

        })
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                } else {

                }
                return
            }

            else -> {
            }
        }
    }


}

