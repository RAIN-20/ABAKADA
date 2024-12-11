package com.example.abakada.teacher.tabs.modules

import android.net.Uri

data class ModuleList(var name: String = "", var imageUri: Uri? = null)

data class ModuleVideo(var link: String ="", var description: String ="")

data class ModuleData(
    var id: String = "",
    var name: String = "",
    var type: String = "",
    var imageUrl: Uri? = null,
    var description: String = "",
    var parts: List<ModuleList> = emptyList(),
    var video: ModuleVideo = ModuleVideo("", "")
)