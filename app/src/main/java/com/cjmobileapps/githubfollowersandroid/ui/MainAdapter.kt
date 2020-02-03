package com.cjmobileapps.githubfollowersandroid.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.cjmobileapps.githubfollowersandroid.R
import com.cjmobileapps.githubfollowersandroid.network.models.Follower
import com.cjmobileapps.githubfollowersandroid.ui.details.DetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_follower.view.*

class MainAdapter(var followers: List<Follower>) : RecyclerView.Adapter<MainAdapter.MainAdapterHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainAdapterHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_follower, parent, false))


    override fun getItemCount() = followers.size

    override fun onBindViewHolder(holder: MainAdapterHolder, position: Int) {
        holder.bind(followers[position])
    }

    inner class MainAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(follower: Follower) {

            Picasso.get()
                .load(follower.avatar_url)
                .fit()
                .centerCrop()
                .into(itemView.follower_avatar)

            itemView.follower_username.text = follower.login
            itemView.follower_container.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)


                intent.putExtra(DetailActivity.USER_NAME, follower.login)

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as MainActivity,
                    itemView.follower_avatar,
                    itemView.resources.getString(R.string.transition))

                itemView.context.startActivity(intent, options.toBundle())
            }

        }
    }
}
