package dx.queen.truecontactmanager.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dx.queen.truecontactmanager.model.Contact
import dx.queen.truecontactmanager.repository.FakeDataSource

class DetailContactViewModel : ViewModel() {

    //get repository
    private val repository = FakeDataSource()

    //visibility of edit text , when the button is clicked
    var editTextVisibleName = MutableLiveData<Unit>()
    var editTextVisibleLastName = MutableLiveData<Unit>()
    var editTextVisibleEmail = MutableLiveData<Unit>()

    //edit text value
    var editTextName = MutableLiveData<String>()
    var editTextLastName = MutableLiveData<String>()
    var editTextEmail = MutableLiveData<String>()

    var isChangesAccept = MutableLiveData<Boolean>()

    fun changeName() {
        editTextVisibleName.value = Unit
    }

    fun changeLastName() {
        editTextVisibleLastName.value = Unit
    }

    fun changeEmail() {
        editTextVisibleEmail.value = Unit
    }

    //when user tap on updateButton
    fun updateList(contact: Contact) {
        repository.changeContact(contact)
    }

    //for accepting changes
    fun acceptChanges() {
        isChangesAccept.value = true
    }

    // close
    fun refuseChanges() {
        isChangesAccept.value = false
    }

}