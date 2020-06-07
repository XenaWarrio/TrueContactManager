package dx.queen.truecontactmanager

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dx.queen.truecontactmanager.model.Contact


class SharedPreferencesIsListChanged(context: Context) {
    private val APP_SETTINGS = "APP_SETTINGS"
    private val CHANGE = "changed"
    private val SAVE_LIST = "save_list"

    private val prefs = context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE)
    private val prefsEditor: SharedPreferences.Editor = prefs.edit()

    // write is list was changed
    fun write(isChanged: Boolean) {
        with(prefsEditor) {
            putBoolean(CHANGE, isChanged)
            apply()
        }
    }

    // get is list was changed
    fun read() = prefs.getBoolean(CHANGE, false)

    //convert list to json
    fun <T> setList(list: List<T>) {
        val gson = Gson()
        val json = gson.toJson(list)
        set(json)
    }

    // save it in SharedPreferences
    fun set(value: String) {
        with(prefsEditor) {
            putString(SAVE_LIST, value)
            apply()
        }
    }


    //get list
    fun readList(): List<Contact> {
        val emptyList = Gson().toJson(ArrayList<Contact>())
        return Gson().fromJson(
            prefs.getString(SAVE_LIST, emptyList),
            object : TypeToken<List<Contact>>() {
            }.type
        )

    }

}