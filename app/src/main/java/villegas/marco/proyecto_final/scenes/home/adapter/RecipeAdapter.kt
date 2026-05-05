package villegas.marco.proyecto_final.scenes.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import villegas.marco.proyecto_final.dataClass.Recipe
import villegas.marco.proyecto_final.databinding.ItemRecipeBinding

// Adapter que conecta la lista de recetas con el RecyclerView.
class RecipeAdapter : ListAdapter<Recipe, RecipeAdapter.RecipeVH>(Diff) {

    // DiffUtil ayuda a actualizar solo los elementos que cambiaron.
    object Diff : DiffUtil.ItemCallback<Recipe>() {
        // Compara si dos recetas representan el mismo elemento.
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem.id == newItem.id

        // Compara si el contenido visible de la receta cambio.
        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem == newItem
    }

    // Crea la vista de cada item de receta.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeVH {
        val binding = ItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecipeVH(binding)
    }

    // Manda la receta de la posicion actual al ViewHolder.
    override fun onBindViewHolder(holder: RecipeVH, position: Int) {
        holder.bind(getItem(position))
    }

    // ViewHolder que llena los textos e imagen de una receta.
    class RecipeVH(private val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        // Pinta los datos de una receta en el layout item_recipe.xml.
        fun bind(item: Recipe) = with(binding) {
            // Coil carga la imagen desde internet usando su URL.
            ivRecipeImage.load(item.imageUrl) { crossfade(true) }
            tvTitle.text = item.title
            tvRating.text = "${item.rating}"
            tvMeta1.text = "${item.minutes} min"
            tvMeta2.text = "${item.kcal} kcal"
        }
    }
}
