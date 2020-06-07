package dx.queen.truecontactmanager.viewModel

import android.util.Log
import androidx.lifecycle.*
import dx.queen.truecontactmanager.model.Contact
import dx.queen.truecontactmanager.repository.FakeDataSource

class ContactsViewModel : ViewModel() {

    // get repository
    private val repository = FakeDataSource()

    // mutable list
    var contactsLiveData = MutableLiveData(repository.mutableContactList)

    //immutable basic list
    val oldList = MutableLiveData<MutableList<Contact>>()

    //delete certain contact
    fun deleteContact(contact: Contact) {
        repository.deleteContact(contact.positionInList)
    }

    //get basic list state
    fun revertChanges() {
       oldList.value =  repository.revertChanges()
    }
}