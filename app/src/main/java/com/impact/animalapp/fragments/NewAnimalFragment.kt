package com.impact.animalapp.fragments

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.storage.FirebaseStorage
import com.impact.animalapp.R
import com.impact.animalapp.adapters.TypeRvAdapter
import kotlinx.android.synthetic.main.fragment_new_animal.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [NewAnimalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewAnimalFragment : Fragment() {
    private var typeNameList = mutableListOf<String>("Собака", "Кошка", "Корова", "Енот")
    private val REQUEST_IMAGE_CAPTURE = 1
    private var bitmap: Bitmap? = null
    private var uriImgFile: Uri? = null
    private var file: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_new_animal, container, false)
        val typeRv = root.findViewById<RecyclerView>(R.id.type_animal_rv)
        val descriptionText = root.findViewById<TextInputEditText>(R.id.description_animal_text)
        val contactsText = root.findViewById<TextInputEditText>(R.id.contact_animal_text)
        val acceptFab = root.findViewById<FloatingActionButton>(R.id.accept_fab)
        val animalPic = root.findViewById<ImageView>(R.id.dog_pic)

        val adapter = TypeRvAdapter(typeNameList)
        typeRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        typeRv.adapter = adapter

        animalPic.setOnClickListener {
            makePhoto()
        }

        acceptFab.setOnClickListener {
            if (descriptionText.text != null && contactsText.text != null) {

            }
        }





        return root
    }

    private fun saveData() {
        val storageRef = FirebaseStorage.getInstance().reference
            .putFile(uriImgFile!!)
            .addOnSuccessListener {
                
            }
            .addOnFailureListener {
                Log.d("LoadStatus", it.message.toString())
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



}