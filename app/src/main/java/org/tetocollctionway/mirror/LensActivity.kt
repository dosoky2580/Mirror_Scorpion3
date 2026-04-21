package org.tetocollctionway.mirror

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import org.tetocollctionway.mirror.databinding.ActivityLensBinding
import java.util.concurrent.Executors

class LensActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLensBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLensBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            try {
                val cameraProvider = cameraProviderFuture.get()
                val preview = androidx.camera.core.Preview.Builder().build().also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA, preview)
            } catch (e: Exception) {
                android.util.Log.e("Mirror", "Camera Error: ${e.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }
}
