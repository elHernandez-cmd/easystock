package com.example.project1722.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project1722.Adapter.ArchiveAdapter
import com.example.project1722.Adapter.MyTeamAdapter
import com.example.project1722.R
import com.example.project1722.ViewModel.ProfileViewModel
import com.example.project1722.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    val profileViewModel:ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            val myteamAdapter by lazy {MyTeamAdapter(profileViewModel.loadDataMyteam())  }
            viewTeam.apply {
                adapter=myteamAdapter
                layoutManager=LinearLayoutManager(
                    this@ProfileActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }


            val archiveAdapter by lazy {ArchiveAdapter(profileViewModel.loadDataArchive())}
            viewArchive.apply {
                adapter=archiveAdapter
                layoutManager=LinearLayoutManager(
                    this@ProfileActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
        }
    }
}