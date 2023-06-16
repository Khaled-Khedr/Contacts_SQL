package com.example.contacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.contacts.data.DatabaseHandler
import com.example.contacts.model.Contact

class MainActivity : AppCompatActivity() {
    private lateinit var textViewData: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewData = findViewById(R.id.text_view_data)
        val db = DatabaseHandler(this)
        db.addContact(Contact("Khaled", "12364134"))  //adding a contact to the db
        db.addContact(Contact("John", "1236034"))
        db.addContact(Contact("Bob", "1223884"))
        db.addContact(Contact("Dark", "124534"))
        db.addContact(Contact("Jack", "1235524"))


//        val contact=db.getContact(3) //3rd element
//        textViewData.text="Name: ${contact.name}\nPhone Number: ${contact.phoneNumber}"
//
//        val newContact=contact
//        newContact.name="Alina"
//        newContact.phoneNumber="34234234234"
//        db.updateContact(newContact)
//
//        val updatedContact=db.getContact(3)
//        textViewData.text="Updated Name: ${updatedContact.name}\nUpdated Phone Number: ${updatedContact.phoneNumber}"


//        val contact1=db.getContact(1)
//        val contact2=db.getContact(2)   //run this seperately from contactCount otherwise it crashes
//
//        db.deleteContact(contact1)
//        db.deleteContact(contact2)


        val contactList = db.getAllContacts()
        var data = ""
        for (contact in contactList) {
            data += ("\nName: ${contact.name}" +
                    "\nPhoneNumber: ${contact.phoneNumber}\n")

            textViewData.text = data
            textViewData.append("The number of contacts: ${db.getContactsCount()}")
        }
    }


}