package villegas.marco.proyecto_final.scenes.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import villegas.marco.proyecto_final.databinding.FragmentProfileBinding
import villegas.marco.proyecto_final.scenes.main.view.MainActivity
import villegas.marco.proyecto_final.sharedPreference.SharedPreferenceConstants
import villegas.marco.proyecto_final.sharedPreference.SharedPreferenceManager
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var pendingPhotoUri: Uri? = null

    // Funcion para pedir permiso de utilizar la camara
    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                openCamera()
            } else {
                Toast.makeText(requireContext(), "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }

    // Funcion para abrir camara y tomar foto
    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                pendingPhotoUri?.let { uri ->
                    // Actualiza foto en Profile Fragment
                    binding.ivProfilePhoto.setImageURI(uri)

                    // Guardar para usarla en el avatar del inicio
                    val prefs = SharedPreferenceManager(requireContext())
                    prefs.setString(SharedPreferenceConstants.PROFILE_PHOTO_URI_KEY, uri.toString())
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mostrar datos del usuario
        val prefs = SharedPreferenceManager(requireContext())
        val savedUri = prefs.getString(SharedPreferenceConstants.PROFILE_PHOTO_URI_KEY)
        if (savedUri.isNotBlank()) {
            this.binding.ivProfilePhoto.setImageURI(Uri.parse(savedUri))
        }
        this.binding.tvUsername.text = prefs.getString(SharedPreferenceConstants.USER_NAME_KEY)
        this.binding.tvUserEmail.text = prefs.getString(SharedPreferenceConstants.USER_EMAIL_KEY)

        binding.btnUpdatePhoto.setOnClickListener {
            val granted = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED

            // Solo intentar abrir camara si se concedio permiso
            if (granted) {
                openCamera()
            } else {
                // Pedir permiso para utilizar la camara
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }

        this.binding.btnLogout.setOnClickListener {
            // Regresar al Login (MainActivity) y limpiar el backstack
            val intent = Intent(requireContext(), MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun createTempImageUri(): Uri {
        val dir = File(requireContext().cacheDir, "images")
        if (!dir.exists()) dir.mkdirs()

        val file = File(dir, "profile_photo.jpg") // sobrescribe la anterior
        return FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            file
        )
    }

    private fun openCamera() {
        val uri = createTempImageUri()
        pendingPhotoUri = uri
        takePictureLauncher.launch(uri)
    }
}
