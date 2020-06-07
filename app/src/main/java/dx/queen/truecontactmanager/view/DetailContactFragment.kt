package dx.queen.truecontactmanager.view

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dx.queen.truecontactmanager.model.Contact
import dx.queen.truecontactmanager.R
import dx.queen.truecontactmanager.databinding.FragmentDetailContactBinding
import dx.queen.truecontactmanager.viewModel.DetailContactViewModel
import kotlinx.android.synthetic.main.fragment_detail_contact.*


class DetailContactFragment : Fragment() {

    private lateinit var binding: FragmentDetailContactBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail_contact, container, false
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get specific contact
        val contact = requireArguments().getParcelable<Contact>("contact")

        val viewModel = ViewModelProvider(this).get(
            DetailContactViewModel::class.java
        )
        binding.viewModel = viewModel


        // set view fields
        if (contact != null) {
            with(binding) {
                imageViewContactImage.setImageResource(contact.image)
                textViewName.text = contact.firstName
                textViewLastName.text = contact.lastName
                textViewEmail.text = contact.email
                textViewNote.text = contact.notice
                textViewNote.animate().alpha(1.0F).setDuration(3000)
            }

            val visible = View.VISIBLE
            val invisible = View.INVISIBLE

            //default flags
            var isNameChanging = false
            var isLastNameChanging = false

            //if edit button pressed at  contact`s name
            val editTextObserverName = Observer<Unit> {
                with(binding) {
                    editTextName.visibility = visible
                    editTextName.setText(contact.firstName)
                    imageButtonAcceptChanges.visibility = visible
                    imageButtonRefuseChanges.visibility = visible
                    textViewName.visibility = invisible
                    imageButtonChangeLastName.isClickable = false
                    imageButtonChangeEmail.isClickable = false
                    isNameChanging = true
                }
            }

            //if edit button pressed at  contact`s last name
            val editTextObserverLastName = Observer<Unit> {
                with(binding) {
                    editTextLastName.visibility = visible
                    editTextLastName.setText(contact.lastName)
                    imageButtonAcceptChanges.visibility = visible
                    imageButtonRefuseChanges.visibility = visible
                    textViewLastName.visibility = invisible
                    imageButtonChangeName.isClickable = false
                    imageButtonChangeEmail.isClickable = false
                    isLastNameChanging = true
                }
            }

            //if edit button pressed at  contact`s email
            val editTextObserverEmail = Observer<Unit> {
                with(binding) {
                    editTextEmail.visibility = visible
                    editTextEmail.setText(contact.email)
                    imageButtonAcceptChanges.visibility = visible
                    imageButtonRefuseChanges.visibility = visible
                    textViewEmail.visibility = invisible
                    imageButtonChangeLastName.isClickable = false
                    imageButtonChangeName.isClickable = false
                }
            }

            // observe new value from edit text name
            val editTextNameValueObserver = Observer<String> {
                binding.textViewName.visibility = visible
                binding.textViewName.text = it
                contact.firstName = it
            }

            // observe new value from edit text last name
            val editTextLastNameValueObserver = Observer<String> {
                binding.textViewLastName.visibility = visible
                binding.textViewLastName.text = it
                contact.lastName = it
            }

            // observe new value from edit text email
            val editTextEmailValueObserver = Observer<String> {
                binding.textViewEmail.visibility = visible
                binding.textViewEmail.text = it
                contact.email = it
            }

            //when user click on buttons accept or close
            val acceptChangesObserver = Observer<Boolean> { changesIsAccepted ->
                if (changesIsAccepted) {
                    when {
                        isNameChanging -> {
                            with(binding) {

                                // start observe value for getting the last variant
                                viewModel.editTextName.observe(
                                    viewLifecycleOwner,
                                    editTextNameValueObserver
                                )
                                editTextName.visibility = invisible
                            }
                        }
                        isLastNameChanging -> {
                            with(binding) {

                                // start observe value for getting the last variant
                                viewModel.editTextLastName.observe(
                                    viewLifecycleOwner,
                                    editTextLastNameValueObserver
                                )
                                editTextLastName.visibility = invisible
                            }
                        }
                        else -> {
                            with(binding) {

                                // start observe value for getting the last variant
                                viewModel.editTextEmail.observe(
                                    viewLifecycleOwner,
                                    editTextEmailValueObserver
                                )
                                editTextEmail.visibility = invisible
                            }
                        }
                    }
                    viewModel.updateList(contact)
                    hideKeyboard()
                } else {
                    when {
                        isNameChanging -> {
                            binding.editTextName.visibility = invisible
                        }

                        isLastNameChanging -> {
                            binding.editTextLastName.visibility = invisible
                        }

                        else -> {
                            binding.editTextEmail.visibility = invisible
                        }
                    }
                }

                //set these buttons invisible
                imageButton_accept_changes.visibility = invisible
                imageButton_refuse_changes.visibility = invisible

                //set all editable button active again
                binding.imageButtonChangeName.isClickable = true
                binding.imageButtonChangeLastName.isClickable = true
                binding.imageButtonChangeEmail.isClickable = true

                //return default flags
                isLastNameChanging = false
                isNameChanging = false
            }


            //set observers
            viewModel.editTextVisibleName.observe(viewLifecycleOwner, editTextObserverName)
            viewModel.editTextVisibleLastName.observe(viewLifecycleOwner, editTextObserverLastName)
            viewModel.editTextVisibleEmail.observe(viewLifecycleOwner, editTextObserverEmail)
            viewModel.isChangesAccept.observe(viewLifecycleOwner, acceptChangesObserver)
        }

    }

    //for hide keyboard after button is clicked
    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}