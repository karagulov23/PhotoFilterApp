package orlo.karagulov.photofilterapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants
import orlo.karagulov.photofilterapp.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = intent.getStringExtra("imageUri").toString()

        val dsPhotoEditorIntent = Intent(this, DsPhotoEditorActivity::class.java)

        dsPhotoEditorIntent.data = imageUri.toUri()

        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "ImageEditorApp")
        startActivityForResult(dsPhotoEditorIntent, 100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                val outputUri: Uri? = data!!.data
                binding.ivEditedImage.setImageURI(outputUri)
                Toast.makeText(this, "Saved on Gallery", Toast.LENGTH_SHORT).show()

                binding.btnSharePhoto.setOnClickListener {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "image/*"
                    shareIntent.putExtra(Intent.EXTRA_STREAM, outputUri)
                    startActivity(Intent.createChooser(shareIntent, "Share image"))
                }
            }
        }
    }

}