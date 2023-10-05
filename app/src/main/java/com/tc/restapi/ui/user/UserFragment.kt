package com.tc.restapi.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tc.restapi.R
import com.tc.restapi.data.model.user.UserDataItemModel
import com.tc.restapi.databinding.FragmentUserBinding
import com.tc.restapi.util.ResponseHandling
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserFragment: Fragment() {

    // Private property to hold the binding for the fragment.
    private var _binding: FragmentUserBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    // Access the binding property safely using the binding getter.
    private val binding get() = _binding!!

    // Declare a property to hold the UserAdapter instance.
    private lateinit var adapter: UserAdapter

    // Override the onCreateView method to create and return the fragment's view.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        // Create a ViewModel instance for handling people-related data.
        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        // Inflate the layout for the fragment using the binding.
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Observe changes in personDetails LiveData and update the UI accordingly.
        userViewModel.userDetails.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseHandling.Success<*> -> {
                    // Set up the RecyclerView with a LinearLayoutManager and the PeopleAdapter.
                    updateAdapter(it.result as ArrayList<UserDataItemModel>)
                    binding.progressBar.visibility = View.GONE
                }

                is ResponseHandling.Failure -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }

                else -> {
                    // Loading
//                    Toast.makeText(context, "Loading. . .", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        // Initialize the adapter with an empty list and fetch person details.
        adapter = UserAdapter(arrayListOf()) {
            findNavController().navigate(
                R.id.action_navigation_user_to_profileFragment,
                bundleOf()
            )
        }
        binding.recyclerviewUser.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapter
        }




        userViewModel.getUserDetails()

        // Return the root view of the fragment.
        return root
    }

    // Override the onDestroyView method to release the binding when the view is destroyed.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updateAdapter(ourData: ArrayList<UserDataItemModel>) {
        adapter.updatePeopleList(ourData)
    }
}