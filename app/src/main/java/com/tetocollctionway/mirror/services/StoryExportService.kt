package com.tetocollctionway.mirror.services

import android.content.Context
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileOutputStream

class StoryExportService(private val context: Context) {
    
    // دالة تحويل القصص لملف Word
    fun exportStoriesToWord(jsonContent: String, fileName: String): File? {
        return try {
            val document = XWPFDocument()
            val paragraph = document.createParagraph()
            val run = paragraph.createRun()
            
            // هنا بنرتب القصص جوه الملف
            run.setText("مكتبة قصص المرآة - النسخة الكاملة")
            run.addBreak()
            run.setText(jsonContent) // سيتم معالجة الـ JSON ليظهر كعناوين ونصوص مرتبة

            val file = File(context.getExternalFilesDir(null), "$fileName.docx")
            val out = FileOutputStream(file)
            document.write(out)
            out.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
