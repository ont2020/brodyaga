package com.impact.animalapp.fragments

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PointF
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.impact.animalapp.R
import com.impact.animalapp.adapters.TypeRvAdapter
import com.impact.animalapp.models.Global
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CompositeIcon
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import kotlinx.android.synthetic.main.fragment_new_animal.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [NewAnimalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewAnimalFragment : Fragment(), UserLocationObjectListener {
    private var typeNameList = mutableListOf<String>("Собака", "Кошка")
    private val REQUEST_IMAGE_CAPTURE = 1
    private var bitmap: Bitmap? = null
    private var uriImgFile: Uri? = null
    private var file: File? = null
    private var downloadUri = ""
    private var userLocationLayer: UserLocationLayer? = null
    private var mapView: MapView? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        MapKitFactory.setApiKey("20cec70e-e925-4d2e-8d66-accc84e9b541");
        MapKitFactory.initialize(requireContext());
        val root = inflater.inflate(R.layout.fragment_new_animal, container, false)
        val typeRv = root.findViewById<RecyclerView>(R.id.type_animal_rv)
        val descriptionText = root.findViewById<TextInputEditText>(R.id.description_animal_text)
        val contactsText = root.findViewById<TextInputEditText>(R.id.contact_animal_text)
        val acceptFab = root.findViewById<FloatingActionButton>(R.id.accept_fab)
        val animalPic = root.findViewById<ImageView>(R.id.dog_pic)
        val navController = findNavController()
        mapView = root.findViewById(R.id.yandex_map)
        mapView?.map?.move(CameraPosition(Point(0.0, 0.0), 14.0f, 0.0f, 0.0f))
        val mapKit = MapKitFactory.getInstance()
        userLocationLayer = mapKit.createUserLocationLayer(mapView?.mapWindow!!)
        userLocationLayer?.isVisible = true
        userLocationLayer?.isHeadingEnabled = true
        userLocationLayer?.setObjectListener(this)

        var locationManager = MapKitFactory.getInstance().createLocationManager()
        locationManager!!.requestSingleUpdate(object : LocationListener {

            override fun onLocationStatusUpdated(p0: LocationStatus) {

            }

            override fun onLocationUpdated(p0: Location) {
                Log.d("MyPositionMap", p0.position.latitude.toString() + p0.position.longitude.toString())
                latitude = p0.position.latitude
                longitude = p0.position.longitude
                Toast.makeText(requireContext(), latitude.toString(), Toast.LENGTH_LONG).show()
            }
        })



        val adapter = TypeRvAdapter(typeNameList)
        typeRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        typeRv.adapter = adapter

        animalPic.setOnClickListener {
            makePhoto()
        }

        acceptFab.setOnClickListener {

            if (descriptionText.text != null && contactsText.text != null) {
                Global.selectedType?.let { it1 -> saveData(it1, descriptionText.text.toString(), contactsText.text.toString()) }
                navController.navigate(R.id.action_newAnimalFragment_to_requestAnimalFragment)
            }
        }





        return root
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
        MapKitFactory.getInstance().onStop()
    }

    private fun saveData(type: String, description: String, contacts: String) {
        var downloadUri2: String? = null
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val baos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val mountainsRef = storageRef.child("images/${UUID.randomUUID()}.jpg")
        var uploadTask = mountainsRef.putBytes(data)


        // Register observers to listen for when the download is done or if it fails
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            mountainsRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                downloadUri = task.result.toString()
                Log.d("LoadSuccess", downloadUri)
                loadToFirestore(type, description, contacts, downloadUri, latitude, longitude)
            } else {
                // Handle failures
                // ...
            }
        }







    }

    private fun loadToFirestore(type: String, description: String, contacts: String, downloadUri: String, latitude: Double, longitude: Double) {

        val date = Date()
        val time = date.time
        val currentDate = date.date




        var hashMap = hashMapOf<String, Any>(
            "type" to type,
            "description" to description,
            "contacts" to contacts,
            "photo" to downloadUri.toString(),
            "status" to "В обработке",
            "date" to SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date()),
            "latitude" to latitude,
            "longitude" to longitude,
            "shelter" to "Нету",
            "state_health" to "Не определено",
            "chip" to false

        )

        val firebaseFirestore = FirebaseFirestore.getInstance()
            .collection("animals")
            .add(hashMap)
            .addOnSuccessListener {

            }
            .addOnFailureListener{
                Log.d("LoadData", it.message.toString())
            }

    }


    private fun makePhoto() {

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            activity?.packageManager?.let {
                takePictureIntent.resolveActivity(it)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap

            dog_pic.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
            bitmapToFile(bitmap!!)
        }
    }



    private fun bitmapToFile(bitmap: Bitmap) {
        val wrapper = ContextWrapper(context)
        var file2 = wrapper.getDir("imagePet", Context.MODE_PRIVATE)
        file2 = File(file2,"${UUID.randomUUID()}.jpg")


        try {
            val stream: OutputStream = FileOutputStream(file2)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.flush()
            stream.close()
        }catch(e: IOException){
            e.printStackTrace()
        }
        uriImgFile = Uri.parse(file2.absolutePath)
        file = file2

    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {

    }

    override fun onObjectRemoved(p0: UserLocationView) {
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        userLocationLayer?.setAnchor(PointF((mapView?.getWidth()!! * 0.5).toFloat(), (mapView?.getHeight()!! * 0.5).toFloat()),
            PointF((mapView?.getWidth()!! * 0.5).toFloat(), (mapView?.getHeight()!! * 0.83).toFloat()))


        userLocationView.getArrow().setIcon(
            ImageProvider.fromResource(
                requireContext(), R.drawable.ic_baseline_navigation_24
            )
        )
        Log.d("Pin22", userLocationView.pin.toString())

        val pinIcon: CompositeIcon = userLocationView.getPin().useCompositeIcon()

        /*pinIcon.setIcon(
            "icon",
            ImageProvider.fromResource(requireContext(), R.drawable.),
            IconStyle().setAnchor(PointF(0f, 0f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(0f)
                .setScale(1f)
        )*/

        /*pinIcon.setIcon(
            "pin",
            ImageProvider.fromResource(requireContext(), R.drawable.dog),
            IconStyle().setAnchor(PointF(0.5f, 0.5f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(1f)
                .setScale(0.5f)
        )*/

        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE)
    }








}