package villegas.marco.proyecto_final.scenes.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import villegas.marco.proyecto_final.dataClass.Recipe
import villegas.marco.proyecto_final.databinding.ItemRecipeBinding
class RecipeAdapter : ListAdapter<Recipe, RecipeAdapter.RecipeVH>(Diff) {

    object Diff : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeVH {
        val binding = ItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecipeVH(binding)
    }

    override fun onBindViewHolder(holder: RecipeVH, position: Int) {
        holder.bind(getItem(position))
    }

    class RecipeVH(private val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Recipe) = with(binding) {
            ivRecipeImage.setImageResource(item.imageRes)
            tvTitle.text = item.title
            tvRating.text = "${item.rating}"
            tvMeta1.text = "${item.minutes} min"
            tvMeta2.text = "${item.kcal} kcal"
        }
    }
}