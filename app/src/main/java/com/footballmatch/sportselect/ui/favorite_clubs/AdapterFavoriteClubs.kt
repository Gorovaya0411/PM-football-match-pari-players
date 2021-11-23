package com.footballmatch.sportselect.ui.favorite_clubs

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.footballmatch.sportselect.R
import com.footballmatch.sportselect.model.FootballCommand
import com.squareup.picasso.Picasso
import java.util.ArrayList

class AdapterFavoriteClubs(
    private var callTeamComposition: (name: String) -> Unit
) : RecyclerView.Adapter<AdapterFavoriteClubs.MyViewHolder>() {

    val data = mutableListOf<FootballCommand>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(cubes: ArrayList<FootballCommand>) {
        data.addAll(cubes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view_favorite_clubs, parent, false
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
        private var goOver: ImageView =
            itemView.findViewById(R.id.item_view_favorite_clubs_go_over_img)
        private var name: TextView = itemView.findViewById(R.id.item_view_favorite_clubs_name_txt)
        private var city: TextView = itemView.findViewById(R.id.item_view_favorite_clubs_city_txt)
        private var logo: ImageView =
            itemView.findViewById(R.id.item_view_favorite_clubs_photo_players_img)

        @SuppressLint("NotifyDataSetChanged")
        fun bind() {
            name.text = data[adapterPosition].teamName
            city.text = data[adapterPosition].teamCity
            when (data[adapterPosition].teamName) {
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

            goOver.setOnClickListener {
                callTeamComposition.invoke(data[adapterPosition].teamName)
            }
        }
    }
}