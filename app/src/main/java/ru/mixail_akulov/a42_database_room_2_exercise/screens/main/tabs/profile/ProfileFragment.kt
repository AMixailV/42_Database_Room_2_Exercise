package ru.mixail_akulov.a42_database_room_2_exercise.screens.main.tabs.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.navOptions
import ru.mixail_akulov.a42_database_room_2_exercise.R
import ru.mixail_akulov.a42_database_room_2_exercise.Repositories
import ru.mixail_akulov.a42_database_room_2_exercise.databinding.FragmentProfileBinding
import ru.mixail_akulov.a42_database_room_2_exercise.model.accounts.entities.Account
import ru.mixail_akulov.a42_database_room_2_exercise.utils.findTopNavController
import ru.mixail_akulov.a42_database_room_2_exercise.utils.observeEvent
import ru.mixail_akulov.a42_database_room_2_exercise.utils.viewModelCreator
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel by viewModelCreator { ProfileViewModel(Repositories.accountsRepository) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        binding.editProfileButton.setOnClickListener { onEditProfileButtonPressed() }
        binding.logoutButton.setOnClickListener { onLogoutButtonPressed() }

        observeAccountDetails()
        observeRestartAppFromLoginScreenEvent()
    }

    private fun observeAccountDetails() {
        val formatter = SimpleDateFormat.getDateTimeInstance()
        viewModel.account.observe(viewLifecycleOwner) { account ->
            if (account == null) return@observe
            binding.emailTextView.text = account.email
            binding.usernameTextView.text = account.username
            binding.createdAtTextView.text = if (account.createdAt == Account.UNKNOWN_CREATED_AT)
                getString(R.string.placeholder)
            else
                formatter.format(Date(account.createdAt))
        }
    }

    private fun onEditProfileButtonPressed() {
        findTopNavController().navigate(R.id.editProfileFragment)
    }

    private fun observeRestartAppFromLoginScreenEvent() {
        viewModel.restartWithSignInEvent.observeEvent(viewLifecycleOwner) {
            findTopNavController().navigate(R.id.signInFragment, null, navOptions {
                popUpTo(R.id.tabsFragment) {
                    inclusive = true
                }
            })
        }
    }

    private fun onLogoutButtonPressed() {
        viewModel.logout()
    }


}