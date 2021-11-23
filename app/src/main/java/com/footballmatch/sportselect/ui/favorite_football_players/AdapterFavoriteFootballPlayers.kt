package com.footballmatch.sportselect.ui.favorite_football_players

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.footballmatch.sportselect.R
import com.footballmatch.sportselect.model.FavoritePlayer
import com.footballmatch.sportselect.model.Player
import com.squareup.picasso.Picasso
import java.util.ArrayList

class AdapterFavoriteFootballPlayers(
    private var callTeamComposition: (name: String) -> Unit
) : RecyclerView.Adapter<AdapterFavoriteFootballPlayers.MyViewHolder>() {

    val data = mutableListOf<FavoritePlayer>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(cubes: ArrayList<FavoritePlayer>) {
        data.addAll(cubes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view_favorite_players, parent, false
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
        private var name: TextView = itemView.findViewById(R.id.item_view_favorite_players_name_txt)
        private var city: TextView = itemView.findViewById(R.id.item_view_favorite_players_city_txt)
        private var number: TextView =
            itemView.findViewById(R.id.item_view_favorite_players_number_players)
        private var logo: ImageView =
            itemView.findViewById(R.id.item_view_favorite_players_photo_players_img)

        @SuppressLint("NotifyDataSetChanged")
        fun bind() {
            name.text = data[adapterPosition].name
            city.text = data[adapterPosition].nameTeam
            number.text = data[adapterPosition].number.toString()
            when (data[adapterPosition].nameTeam) {
                "Барселона" -> logo.setBackgroundResource(R.drawable.ic_fc_barcelona)
                "Реал Мадрид" -> logo.setBackgroundResource(R.drawable.ic_realmadrid)
                "Бавария" -> logo.setBackgroundResource(R.drawable.ic_fc_bayern_munchen)
                "Валенсия" -> logo.setBackgroundResource(R.drawable.ic_valencia)
                "Аякс" -> logo.setBackgroundResource(R.drawable.ic_amsterdam)
                "Наполи" -> logo.setBackgroundResource(R.drawable.ic_napoli_logo)
                "Ростов" -> logo.setBackgroundResource(R.drawable.ic_rostov)
            }
            Picasso.get()
                .load(data[adapterPosition].logo)
                .fit()
                .into(logo)

            itemView.setOnClickListener {
                callTeamComposition.invoke(data[adapterPosition].name)
            }
        }
    }
}