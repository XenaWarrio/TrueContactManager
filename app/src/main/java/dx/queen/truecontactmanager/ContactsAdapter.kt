package dx.queen.truecontactmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import dx.queen.truecontactmanager.databinding.SingleContactCardBinding
import dx.queen.truecontactmanager.model.Contact
import kotlinx.android.synthetic.main.single_contact_card.view.*

class ContactsAdapter :
    RecyclerView.Adapter<ContactViewHolder>() {

    val clickOnItem = MutableLiveData<Contact>()
    val deleteItem = MutableLiveData<Contact>()

    private var contactsList = mutableListOf<Contact>()

    fun setContactsList(list: MutableList<Contact>) {
        contactsList = list

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = SingleContactCardBinding.inflate(layoutInflater, parent, false)

        return ContactViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactsList[position]
        contact.positionInList = position

        holder.itemView.setOnClickListener {
            clickOnItem.value = contact
        }
        holder.itemView.imageButton_delete_contact.setOnClickListener {
            deleteItem.value = contact
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, contactsList.size)
        }
        holder.bind(contactsList[position])
    }
}

class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(contact: Contact) {
        itemView.textView_contact_name.text = contact.firstName
        itemView.textView_contact_last_name.text = contact.lastName
        itemView.imageView_contact_image.setImageResource(contact.image)
    }
}