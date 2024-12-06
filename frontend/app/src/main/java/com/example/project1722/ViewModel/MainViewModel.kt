package com.example.project1722.ViewModel

import androidx.lifecycle.ViewModel
import com.example.project1722.Repository.MainRepository

class MainViewModel(val repository: MainRepository) : ViewModel() {
    constructor() : this(MainRepository())

    fun loadData() = repository.items
}