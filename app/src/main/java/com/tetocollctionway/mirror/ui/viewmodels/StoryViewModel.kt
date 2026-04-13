package com.tetocollctionway.mirror.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import org.json.JSONObject

class StoryViewModel : ViewModel() {
    var storiesJson = mutableStateOf("")

    fun loadStories(context: Context) {
        val jsonString = context.assets.open("free_stories.json").bufferedReader().use { it.readText() }
        storiesJson.value = jsonString
    }
}
