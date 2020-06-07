package dx.queen.truecontactmanager.app_instance

import android.app.Application

// using for SharedPreferences
class AppInstance : Application() {
    companion object {
        lateinit var instance: AppInstance
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}