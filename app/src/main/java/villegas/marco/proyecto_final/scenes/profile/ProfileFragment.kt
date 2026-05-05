package villegas.marco.proyecto_final.scenes.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import java.io.File
import villegas.marco.proyecto_final.databinding.FragmentProfileBinding
import villegas.marco.proyecto_final.scenes.main.view.MainActivity
import villegas.marco.proyecto_final.sharedPreference.SharedPreferenceConstants
import villegas.marco.proyecto_final.sharedPreference.SharedPreferenceManager

// Fragment que muestra datos del usuario, foto de perfil y boton de logout.
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var pendingPhotoUri: Uri? = null

    // Pide permiso de camara y abre la camara solo si el usuario acepta.
    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                openCamera()
            } else {
                Toast.makeText(requireContext(), "Permiso de camara denegado", Toast.LENGTH_SHORT).show()
            }
        }

    // Recibe el resultado de la camara despues de tomar la foto.
    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                pendingPhotoUri?.let { uri ->
                    // Muestra la foto nueva en el perfil.
                    binding.ivProfilePhoto.setImageURI(uri)

                    // Guarda la URI para mostrar la misma foto en Home.
                    val prefs = SharedPreferenceManager(requireContext())
                    prefs.setString(SharedPreferenceConstants.PROFILE_PHOTO_URI_KEY, uri.toString())
                }
            }
        }

    // Infla el layout del fragment.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Configura datos del usuario y listeners cuando la vista ya existe.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

            if (granted) {
                openCamera()
            } else {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }

        this.binding.btnLogout.setOnClickListener {
            // Limpia el backstack para volver al login sin regresar a Home.
            val intent = Intent(requireContext(), MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    // Crea una URI temporal donde la camara guardara la foto.
    private fun createTempImageUri(): Uri {
        val dir = File(requireContext().cacheDir, "images")
        if (!dir.exists()) dir.mkdirs()

        val file = File(dir, "profile_photo.jpg")
        return FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            file
        )
    }

    // Prepara la URI y lanza la camara.
    private fun openCamera() {
        val uri = createTempImageUri()
        pendingPhotoUri = uri
        takePictureLauncher.launch(uri)
    }
}
