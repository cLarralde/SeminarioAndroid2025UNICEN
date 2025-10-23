package ar.edu.unicen.seminario.ui
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.edu.unicen.seminario.ddl.models.Game
import ar.edu.unicen.seminario.databinding.ListitemgameBinding
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import android.content.Intent
import ar.edu.unicen.seminario.R


class GameAdapter(private val games: List<Game>, private val onGameClick: (Game) -> Unit): RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ListitemgameBinding = ListitemgameBinding.inflate(layoutInflater, parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game: Game = games[position]
        holder.bind(game)
    }

    override fun getItemCount(): Int {
        return games.size
    }
    inner class GameViewHolder(private val binding: ListitemgameBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(game: Game){
            binding.gameName.text = game.name ?: "Sin nombre"
            Glide.with(binding.imgThumbnail.context)
                .load(game.thumbnail  ?: R.drawable.ic_launcher_foreground)
                .into(binding.imgThumbnail)
            binding.gameGenres.text = game.info?.genres?.joinToString(", ")
            binding.gamePlatforms.text = game.info?.platforms?.joinToString(", ")
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, GameDetailActivity::class.java)
                intent.putExtra("id", game.id)
                binding.root.context.startActivity(intent)

            }
        }
    }

}


