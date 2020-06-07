package dx.queen.truecontactmanager.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    var image: Int,
    var firstName: String,
    var lastName: String,
    var email: String,
    var notice: String,
    var positionInList: Int = 0
) : Parcelable {}