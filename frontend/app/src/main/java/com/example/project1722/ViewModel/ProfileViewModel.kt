package com.example.project1722.ViewModel

import androidx.lifecycle.ViewModel
import com.example.project1722.Repository.ProfileRepository

class ProfileViewModel(val repository: ProfileRepository):ViewModel() {
    constructor(): this(ProfileRepository())

    fun loadDataMyteam()=repository.myteamItems
    fun loadDataArchive()=repository.archiveItems
}