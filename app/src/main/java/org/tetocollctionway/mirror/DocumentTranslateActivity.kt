package org.tetocollctionway.mirror

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DocumentTranslateActivity : AppCompatActivity() {

    private lateinit var edtUrlOrPath: EditText
    private lateinit var btnTranslateDoc: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_translate)

        edtUrlOrPath = findViewById(R.id.edtUrlOrPath)
        btnTranslateDoc = findViewById(R.id.btnTranslateDoc)

        findViewById<Button>(R.id.btnBrowse).setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            startActivityForResult(intent, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                edtUrlOrPath.setText(uri.toString())
                btnTranslateDoc.visibility = View.VISIBLE
            }
        }
    }
}
