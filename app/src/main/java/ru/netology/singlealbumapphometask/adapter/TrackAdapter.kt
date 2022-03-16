package ru.netology.singlealbumapphometask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.singlealbumapphometask.R
import ru.netology.singlealbumapphometask.databinding.CardTrackBinding
import ru.netology.singlealbumapphometask.dto.Track

interface OnInteractionListener {
    fun onPlayOrPause(track: Track) {}
}

class TrackAdapter(
    private  val onInteractionListener: OnInteractionListener
    ): ListAdapter<Track, TrackViewHolder>(TrackDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = CardTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = getItem(position)
        holder.bind(track)
    }

}

class TrackViewHolder(
    private val binding: CardTrackBinding,
    private val onInteractionListener: OnInteractionListener
)
    : RecyclerView.ViewHolder(binding.root) {
    fun bind(track: Track) {
        with(binding){
            trackTitle.text = track.id.toString()
            albumTitle.text = track.file
            if (track.isPlaying) {
                interactionButton.setImageResource(R.drawable.ic_pause_circle)
            } else {
                interactionButton.setImageResource(R.drawable.ic_play_circle)
            }

            interactionButton.setOnClickListener {
                onInteractionListener.onPlayOrPause(track)
            }
        }
    }
}

class TrackDiffCallBack: DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }


}