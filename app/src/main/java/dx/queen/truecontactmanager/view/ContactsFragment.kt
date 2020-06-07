package dx.queen.truecontactmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dx.queen.truecontactmanager.model.Contact
import dx.queen.truecontactmanager.ContactsAdapter
import dx.queen.truecontactmanager.R
import dx.queen.truecontactmanager.SharedPreferencesIsListChanged
import dx.queen.truecontactmanager.databinding.FragmentContactsBinding
import dx.queen.truecontactmanager.viewModel.ContactsViewModel

class ContactsFragment : Fragment() {
    private lateinit var binding: FragmentContactsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_contacts, container, false
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ac = activity as SingleActivity

        val viewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)
        binding.viewModel = viewModel

        //should have worked like a flag that said if there was a saved contact list by SharedPreferences
        val isItSavedList = requireArguments().getBoolean("sharedPreferencesValue")

        // init recyclerview
        val recyclerView = binding.recyclerViewContacts
        recyclerView.layoutManager = LinearLayoutManager(context)

        // set adapter
        val adapter = ContactsAdapter()
        recyclerView.adapter = adapter


        // observer for deleting contact
        val deleteContactObserver = Observer<Contact> {
            viewModel.deleteContact(it)
        }

        //observer for open certain DetailFragment by clicking in item
        val clickContactObserver = Observer<Contact> {
            ac.openDetailContactFragment(it)
        }

        val contactListObserver = Observer<MutableList<Contact>> {
            if (isItSavedList) {
                // should worked :  if SharedPreferences has list  adapter would display it
                adapter.setContactsList(
                    SharedPreferencesIsListChanged(requireContext()).readList().toMutableList()
                )
                SharedPreferencesIsListChanged(requireContext()).write(false)
            } else {
                // else we get new mutable list from repository without changing
                adapter.setContactsList(it)
            }

            // if there are changes in list -> we change button`s color and clickable
            if (SharedPreferencesIsListChanged(requireContext()).read()) {
                binding.buttonUpdate.setBackgroundResource(R.drawable.ic_update_active)
                binding.buttonUpdate.isClickable = true
                binding.buttonUpdate.setOnClickListener {
                    viewModel.revertChanges()
                }
            } else {
                binding.buttonUpdate.setBackgroundResource(R.drawable.ic_update_unactive)
                binding.buttonUpdate.isClickable = false
            }
        }

        //subscribe observers
        viewModel.contactsLiveData.observe(viewLifecycleOwner, contactListObserver)
        viewModel.oldList.observe(viewLifecycleOwner, contactListObserver)
        adapter.clickOnItem.observe(viewLifecycleOwner, clickContactObserver)
        adapter.deleteItem.observe(viewLifecycleOwner, deleteContactObserver)
    }

}