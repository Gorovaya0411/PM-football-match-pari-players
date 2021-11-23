package com.footballmatch.sportselect.ui.composition

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.footballmatch.sportselect.R
import com.footballmatch.sportselect.model.Player
import com.squareup.picasso.Picasso
import java.util.ArrayList

class AdapterTeamComposition(
    private var callTeamComposition: (name: String) -> Unit,
    private var callAddFavoritePlayer: (name: String) -> Unit,
    nameCommands: String,
    logo: String
) : RecyclerView.Adapter<AdapterTeamComposition.MyViewHolder>() {

    val data = mutableListOf<Player>()
    val nameCommandsAdapter = nameCommands
    val logoAdapter = logo

    @SuppressLint("NotifyDataSetChanged")
    fun setData(cubes: ArrayList<Player>) {
        data.addAll(cubes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view_team_composition, parent, false
            )
        )
    }

    override fun getItemCount(): Int = data.count()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind()
    }

    inner class MyViewHolder(
        itemView: View
    ) :
        RecyclerView.ViewHolder(itemView) {
        private var addFavoritePlayer: ImageView =
            itemView.findViewById(R.id.item_view_team_composition_add_players_img)
        private var name: TextView = itemView.findViewById(R.id.item_view_team_composition_name_txt)
        private var addFavoriteTxt: TextView =
            itemView.findViewById(R.id.item_view_team_composition_add_favorite)
        private var city: TextView = itemView.findViewById(R.id.item_view_team_composition_city_txt)
        private var number: TextView =
            itemView.findViewById(R.id.item_view_team_composition_number_players_txt)
        private var logo: ImageView =
            itemView.findViewById(R.id.item_view_team_composition_photo_players_img)

        @SuppressLint("NotifyDataSetChanged")
        fun bind() {
            name.text = data[adapterPosition].name
            city.text = nameCommandsAdapter
            number.text = data[adapterPosition].number.toString()
            when (nameCommandsAdapter) {
                "Барселона" -> logo.setBackgroundResource(R.drawable.ic_fc_barcelona)
                "Реал Мадрид" -> logo.setBackgroundResource(R.drawable.ic_realmadrid)
                "Бавария" -> logo.setBackgroundResource(R.drawable.ic_fc_bayern_munchen)
                "Валенсия" -> logo.setBackgroundResource(R.drawable.ic_valencia)
                "Аякс" -> logo.setBackgroundResource(R.drawable.ic_amsterdam)
                "Наполи" -> logo.setBackgroundResource(R.drawable.ic_napoli_logo)
                "Ростов" -> logo.setBackgroundResource(R.drawable.ic_rostov)
            }
            Picasso.get()
                .load(logoAdapter)
                .fit()
                .into(logo)

            addFavoritePlayer.setOnClickListener {
                addFavoriteTxt.text = addFavoriteTxt.context.getString(R.string.added_favorites)

                callAddFavoritePlayer.invoke(data[adapterPosition].name)
            }
            itemView.setOnClickListener {
                callTeamComposition.invoke(data[adapterPosition].name)
            }
        }
    }
}