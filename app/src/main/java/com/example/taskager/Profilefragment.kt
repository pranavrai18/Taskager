package com.example.taskager

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import android.Manifest
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {

    private lateinit var profileImage: CircleImageView
    private lateinit var editAvatarButton: ImageButton
    private lateinit var nameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var maleRadioButton: RadioButton
    private lateinit var femaleRadioButton: RadioButton
    private lateinit var saveButton: MaterialButton
    private lateinit var genderRadioGroup: RadioGroup
    private var selectedImageUri: Uri? = null

    // Launcher for picking an image
    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val uri = result.data?.data
            if (uri != null) {
                selectedImageUri = uri
                profileImage.setImageURI(uri)
            }
        }
    }

    // Launcher for requesting storage/media permission
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openImagePicker()
        } else {
            Toast.makeText(requireContext(), "Permission needed to select image", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profilefragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileImage = view.findViewById(R.id.profileImage)
        editAvatarButton = view.findViewById(R.id.editAvatarButton)
        nameEditText = view.findViewById(R.id.nameEditText)
        emailEditText = view.findViewById(R.id.emailEditText)
        maleRadioButton = view.findViewById(R.id.maleRadioButton)
        femaleRadioButton = view.findViewById(R.id.femaleRadioButton)
        saveButton = view.findViewById(R.id.saveButton)
        genderRadioGroup = view.findViewById(R.id.genderRadioGroup)

        // Avatar pick handling
        editAvatarButton.setOnClickListener { ensureImagePermissionAndPick() }
        view.findViewById<View>(R.id.editAvatarText).setOnClickListener { ensureImagePermissionAndPick() }

        loadProfile()

        saveButton.setOnClickListener {
            saveProfile()
        }
    }

    // Checks for permission and launches image picker if allowed
    private fun ensureImagePermissionAndPick() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED) {
            openImagePicker()
        } else {
            permissionLauncher.launch(permission)
        }
    }

    // Launches gallery intent
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        imagePickerLauncher.launch(intent)
    }

    // Save profile data to SharedPreferences
    private fun saveProfile() {
        val name = nameEditText.text?.toString()?.trim()
        val email = emailEditText.text?.toString()?.trim()
        val gender = when {
            maleRadioButton.isChecked -> "male"
            femaleRadioButton.isChecked -> "female"
            else -> ""
        }
        val avatarUriString = selectedImageUri?.toString() ?: ""

        if (name.isNullOrEmpty() || email.isNullOrEmpty() || gender.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPref = requireContext().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("name", name)
            putString("email", email)
            putString("gender", gender)
            putString("avatarUri", avatarUriString)
            apply()
        }
        (activity as? MainActivity)?.updateNavHeader(name, avatarUriString)


        Toast.makeText(requireContext(), "Profile updated!", Toast.LENGTH_SHORT).show()
    }

    // Load profile data from SharedPreferences
    private fun loadProfile() {
        val sharedPref = requireContext().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        nameEditText.setText(sharedPref.getString("name", ""))
        emailEditText.setText(sharedPref.getString("email", ""))
        when (sharedPref.getString("gender", "")) {
            "male" -> maleRadioButton.isChecked = true
            "female" -> femaleRadioButton.isChecked = true
        }
        val avatarUri = sharedPref.getString("avatarUri", "")
        if (!avatarUri.isNullOrEmpty()) {
            selectedImageUri = Uri.parse(avatarUri)
            profileImage.setImageURI(selectedImageUri)
        } else {
            profileImage.setImageResource(R.drawable.default_avatar)
        }
    }
}
