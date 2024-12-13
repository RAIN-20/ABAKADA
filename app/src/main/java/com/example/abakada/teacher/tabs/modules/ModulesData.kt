package com.example.abakada.teacher.tabs.modules

import android.net.Uri
import java.security.Timestamp

data class ModulePart(var name: String = "", var imageUri: Uri? = null)

data class ModuleVideo(var link: String ="", var description: String ="")

data class ModuleData(
    var id: String = "",
    var name: String = "",
    var type: String = "",
    var imageUrl: Uri? = null,
    var description: String = "",
    var parts: MutableList<ModulePart> = mutableListOf(),
    var video: ModuleVideo = ModuleVideo("", ""),
    val createdAt: Timestamp? = null
)