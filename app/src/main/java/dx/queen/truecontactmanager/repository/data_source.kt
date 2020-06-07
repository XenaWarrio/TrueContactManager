package dx.queen.truecontactmanager.repository

import dx.queen.truecontactmanager.model.Contact
import dx.queen.truecontactmanager.R
import dx.queen.truecontactmanager.SharedPreferencesIsListChanged
import dx.queen.truecontactmanager.app_instance.AppInstance


class FakeDataSource {
    // basic immutable list
    private val immutableContactsList = arrayOf(
        Contact(
            R.drawable.mother,
            "Мамочка",
            "Батькивна",
            "ne_poniala_a_cho_tyt_pisat@gmail.com",
            "Бери трубку только по понедельникам, после её маникюра. NOTE: ты поел; нет ты не умрешь в одиночестве; да ,ты очень ей благодарен за сало;"
        ),
        Contact(
            R.drawable.traner,
            "Саша",
            "Тренер",
            "sport_musceles_pain@gmail.com",
            "Не бери трубку , пусть думает , что ты так сильно качаешься, что не можешь подойти к телефону. А вообще, пусть не брешет, 150 кг это норма, бабушка вон говорит , что это даже анорексия"
        ),
        Contact(
            R.drawable.boss,
            "Николай",
            "Босс",
            "evil@gmail.com",
            "Козел канешн, но лучше на звонок ответить. А еще будет круто, если голос не будет дрожать. "
        ),
        Contact(
            R.drawable.teacher,
            "Светлана Ивановна ",
            "Учитель Географии",
            "hate_children@gmail.com",
            "Может уже сдашь то домашнее задание за 8 клаcс? Долго она еще тебе в кошмарах будет сниться??"
        ),
        Contact(
            R.drawable.friend,
            "Лёха",
            "Друг С Детства ",
            "у него всё еще нокиа",
            " Если звонит , то хочет узнать когда ты ему вернешь долг. Нужно переезжать за границу, а то ведь так и вернуть придется"
        ),
        Contact(
            R.drawable.ex_girl,
            "Алена",
            "Бывшая",
            "sterva@gmail.com",
            "Если хочешь позвонить ей - ты пьян. Хорошо подумай , стоит ли этот телефонный звонок того, чтобы снова менять номер телефона... и квартиру...и имя..."
        ),
        Contact(
            R.drawable.teeth_doctor,
            "Он",
            "Стоматолог",
            "pain_one_love@gmail.com",
            "Ну не знаю, если хочешь боли и потратить кучу денег, может тогда уже бывшей позвонить? P.S еще у него такие белые зубы, что у тебя потом болят глаза "
        ),
        Contact(
            R.drawable.grandma,
            "Бабушка",
            "Спросит о внуках",
            "шлет письма в настоящий почтовый ящик",
            "Спрашивай её про огород , погоду и передачи, не дай коснуться двух тем : твоего питания и личной жизни. "
        ),
        Contact(
            R.drawable.woman_who_sit_behind_door,
            "Консьержка",
            "Хоббит за окном",
            "Думаю с сатаной они общаются не через почту",
            "О Господи, может не надо было фоткать её? Сэкономил бы на психологе."
        )
    )


    // list that will be changing
    var mutableContactList = immutableContactsList.map { it.copy() }.toMutableList()

    // change contact in mutableList from DetailContactFragment
    fun changeContact(contact: Contact) {

        //set that list have changed
        writeBooleanToSharedPreferences(true)

        mutableContactList.add(contact.positionInList, contact)
    }

    //delete contact in mutableList from ContactFragment
    fun deleteContact(position: Int) {

        //set that list have changed
        writeBooleanToSharedPreferences(true)

        mutableContactList.removeAt(position)
    }

    // display basic list
    fun revertChanges(): MutableList<Contact> {

        //set that list haven`t changed
        writeBooleanToSharedPreferences(false)

        //updating mutable list by immutable
        with(mutableContactList) {
            clear()
            addAll(immutableContactsList.map { it.copy() }.toMutableList())
        }

        return mutableContactList
    }

    private fun writeBooleanToSharedPreferences(change: Boolean) {
        val context = AppInstance.instance.applicationContext

        if (context != null) {
            SharedPreferencesIsListChanged(context).write(change)
            SharedPreferencesIsListChanged(context).setList(mutableContactList)
        }

    }

}