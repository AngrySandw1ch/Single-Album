package ru.netology.singlealbumapphometask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.singlealbumapphometask.R
import ru.netology.singlealbumapphometask.adapter.TrackAdapter
import ru.netology.singlealbumapphometask.databinding.ActivityAppBinding
import ru.netology.singlealbumapphometask.viewmodel.TrackViewModel

class AppActivity : AppCompatActivity() {
    lateinit var binding: ActivityAppBinding
    lateinit var viewModel: TrackViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        binding = ActivityAppBinding.inflate(layoutInflater)
        viewModel = defaultViewModelProviderFactory.create(TrackViewModel::class.java)

        // findViewById<MaterialButton>(R.id.button).setOnClickListener {
        //      MediaPlayer.create(this, Uri.parse("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")).start()
        //  }
        val adapter = TrackAdapter()
        binding.recycler.adapter = adapter
        viewModel.data.observe(this) {
            adapter.submitList(it)
        }
    }
}