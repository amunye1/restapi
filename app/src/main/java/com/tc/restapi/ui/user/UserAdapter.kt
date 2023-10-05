package com.tc.restapi.ui.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tc.restapi.R
import com.tc.restapi.data.model.user.UserDataItemModel
import com.tc.restapi.databinding.ItemUserUserBinding

// Declare a class named PeopleAdapter that extends RecyclerView.Adapter
// The adapter is responsible for managing the data and creating views for the RecyclerView.
class UserAdapter(
    private val userData: ArrayList<UserDataItemModel>,
    private val function: (item: UserDataItemModel) -> Unit,
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    // Inner class representing a view holder for each item in the RecyclerView.
    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Bind the XML layout for each item in the RecyclerView.
        val binding = ItemUserUserBinding.bind(view)

        // Set up the UI for a specific item using the provided peopleData and position.
        fun setUpUI(userData: UserDataItemModel, position: Int) {
            // Set the first name and last name to the corresponding TextView.
//            binding..text = "${userData.firstName} ${peopleData.lastName}"
//            // Set the job title and email to the corresponding TextView.
//            binding.email.text = "${peopleData.jobtitle}\n${peopleData.email}"
            binding.login.text = userData.login
            // Get the avatar URL from the data.
            val avatarUrl = userData.avatarUrl

            // Load the avatar image using Glide and set it to the ImageView.
            Glide.with(itemView.context)
                .load(avatarUrl)
                .placeholder(R.drawable.ic_home_black_24dp)
                .into(binding.avatarImage)
        }
    }

    // Override the onCreateViewHolder method to create a new PeopleViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_user, parent, false)
        )

    // Override the onBindViewHolder method to bind data to a specific PeopleViewHolder.
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        // Get the current person's data at the given position.
        val currentPerson = userData[position]

        // Set up the UI for the current person using the holder's setUpUI method.
        holder.setUpUI(currentPerson, position)

        // Set a click listener for the item view.
        // Set a click listener for the item view
        holder.itemView.setOnClickListener {
            function.invoke(currentPerson)
        }
    }

    // Override the getItemCount method to return the total number of items in pplData.
    override fun getItemCount() = userData.size

    fun updatePeopleList(peopleDetails: java.util.ArrayList<UserDataItemModel>) {
        userData.addAll(peopleDetails)
        notifyDataSetChanged()
    }
}