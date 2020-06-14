package com.impact.animalapp.fragments

import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.impact.animalapp.R
import com.impact.animalapp.models.Animal
import com.impact.animalapp.models.Global
import com.squareup.picasso.Picasso
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CompositeIcon
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [AnimalProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AnimalProfileFragment : Fragment(), UserLocationObjectListener {
    private var mapView: MapView? = null
    private var userLocationLayer: UserLocationLayer? = null
    private var animal: Animal? = Global.animal
    private var latitude = animal?.latitude?.toDouble()
    private var longitude = animal?.longitude?.toDouble()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MapKitFactory.setApiKey("20cec70e-e925-4d2e-8d66-accc84e9b541");
        MapKitFactory.initialize(requireContext());
        val root = inflater.inflate(R.layout.fragment_animal_profile, container, false)
        val navController = findNavController()
        val image = root.findViewById<ImageView>(R.id.image_animal_profile)
        val status = root.findViewById<TextView>(R.id.status_animal_profile_text)
        val stateHealth = root.findViewById<TextView>(R.id.state_health_animal_profile)
        val isChip = root.findViewById<TextView>(R.id.chip_bool_profile)
        val shelter = root.findViewById<TextView>(R.id.shelter_animal_profile_text)
        val contacts = root.findViewById<TextView>(R.id.contacts_animal_profile_text)
        val dateText = root.findViewById<TextView>(R.id.date_profile_text)
        val description = root.findViewById<TextView>(R.id.description_animal_profile_text)
        val editFab = root.findViewById<FloatingActionButton>(R.id.edit_animal_profile_fab)
        mapView = root.findViewById<MapView>(R.id.map_profile)
        val mapKit = MapKitFactory.getInstance()
        userLocationLayer = mapKit.createUserLocationLayer(mapView?.mapWindow!!)
        userLocationLayer?.isVisible = true
        userLocationLayer?.isHeadingEnabled = true
        userLocationLayer?.setObjectListener(this)


        var animal: Animal? = Global.animal
        latitude = animal?.latitude?.toDouble()
        longitude = animal?.longitude?.toDouble()
        mapView?.map?.move(CameraPosition(Point(latitude!!, longitude!!), 18F, 0F, 0F))



        Picasso.get().load(animal?.image).into(image)
        status.text = animal?.status
        stateHealth.text = animal?.stateHealth
        isChip.text = animal?.isChip.toString()
        shelter.text = animal?.shelter
        contacts.text = animal?.contacts
        dateText.text = animal?.date_require
        description.text = animal?.description



        editFab.setOnClickListener {
            navController.navigate(R.id.action_animalProfileEditorFragment_to_animalListFragment)
            onDetach()
        }


        return root
    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {

    }

    override fun onObjectRemoved(p0: UserLocationView) {

    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        userLocationLayer?.setAnchor(
            PointF((mapView?.getWidth()!! * 0.5).toFloat(), (mapView?.getHeight()!! * 0.5).toFloat()),
            PointF((mapView?.getWidth()!! * 0.5).toFloat(), (mapView?.getHeight()!! * 0.83).toFloat())
        )


        userLocationView.getArrow().setIcon(
            ImageProvider.fromResource(
                requireContext(), R.drawable.ic_baseline_navigation_24
            )
        )
        Log.d("Pin22", userLocationView.pin.toString())

        val pinIcon: CompositeIcon = userLocationView.getPin().useCompositeIcon()



        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE)
    }


}