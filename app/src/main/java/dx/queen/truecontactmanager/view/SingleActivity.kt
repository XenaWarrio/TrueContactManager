package dx.queen.truecontactmanager.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dx.queen.truecontactmanager.model.Contact
import dx.queen.truecontactmanager.R
import dx.queen.truecontactmanager.SharedPreferencesIsListChanged

class SingleActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        navController = Navigation.findNavController(
            this,
            R.id.nav_host_fragment
        )

        //should work as a getting a save changed contact list
        val boolean = SharedPreferencesIsListChanged(this).read()

        val bundle = bundleOf("sharedPreferencesValue" to boolean)

        //navigate to start fragment
        navController.navigate(R.id.contactsFragment, bundle )
    }

    //open certain DetailFragment
    fun openDetailContactFragment(contact: Contact) {
        val bundle = bundleOf("contact" to contact)
        navController.navigate(R.id.action_contactsFragment_to_detailContactFragment, bundle)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

}