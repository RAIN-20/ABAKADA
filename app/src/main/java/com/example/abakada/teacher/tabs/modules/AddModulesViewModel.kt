package com.example.abakada.teacher.tabs.modules

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddModulesViewModel : ViewModel() {
    val moduleData = MutableLiveData<ModuleData>()

    init {
        moduleData.value = ModuleData()
    }
}