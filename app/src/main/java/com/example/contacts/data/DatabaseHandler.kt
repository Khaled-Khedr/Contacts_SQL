package com.example.contacts.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract.Contacts
import com.example.contacts.model.Contact
import com.example.contacts.utils.Constants

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        //After the constants is the type
        val CREATE_TABLE_CONTACTS = "CREATE TABLE ${Constants.TABLE_NAME} (" +
                "${Constants.KEY_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Constants.KEY_NAME} TEXT," +
                "${Constants.KEY_PHONE_NUMBER} TEXT)"

        db?.execSQL(CREATE_TABLE_CONTACTS) //creating a table
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS ${Constants.TABLE_NAME}") //if table old version is not equal to new one drops (deletes) table and creates a new one

        onCreate(db)  //creating a new table if versions didn't match

    }

    /*
    CRUD operations -Create, Read, Update, Delete
     */

    fun addContact(contact: Contact) {
        val db =
            this.writableDatabase  //calls an instance of the db that allows us to use functions like insert update delete
        val values =
            ContentValues() //an instance of the class that allows us to add key value pairs like maps etc
        values.put(Constants.KEY_NAME, contact.name)
        values.put(Constants.KEY_PHONE_NUMBER, contact.phoneNumber)

        //insert to row
        db.insert(Constants.TABLE_NAME, null, values)
        db.close() //closing the db its imp
    }

    fun getContact(id: Int): Contact {
        val db = this.readableDatabase  //getting values so we read from db
        val cursor = db.query(
            Constants.TABLE_NAME,
            arrayOf(Constants.KEY_ID, Constants.KEY_NAME, Constants.KEY_PHONE_NUMBER),
            Constants.KEY_ID + "=?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )
        //cursor iterates through the db and performs queries etc
        cursor?.let {//if cursor isn't null move to first
            it.moveToFirst() //method moves the cursor to the first row
        }
        return Contact(cursor.getString(0).toInt(), cursor.getString(1), cursor.getString(2))
        //basically getting the key_id as an int since it is a number then key_name then key_phone number
    }

    fun getAllContacts(): MutableList<Contact> {
        val db = this.readableDatabase
        val contactList = mutableListOf<Contact>()

        val selectAll = "SELECT * FROM ${Constants.TABLE_NAME}"
        val cursor = db.rawQuery(selectAll, null)

        if (cursor.moveToFirst()) {
            do {
                val contact = Contact(-1,"","")
                contact.id = cursor.getString(0).toInt()
                contact.name = cursor.getString(1)
                contact.phoneNumber = cursor.getString(2)

                //add contact to the contact list
                contactList.add(contact)
            } while (cursor.moveToNext()) //cursor moves to next row
        }
        return contactList
    }

    fun updateContact(contact: Contact):Int{

        val db=this.writableDatabase
        val values=ContentValues()
        values.put(Constants.KEY_NAME,contact.name)
        values.put(Constants.KEY_PHONE_NUMBER, contact.phoneNumber)

        //update row
        return db.update(Constants.TABLE_NAME,values,Constants.KEY_ID+ "=?"
        ,arrayOf(contact.id.toString()))

    }

    fun deleteContact(contact: Contact)
    {
        val db=this.writableDatabase
        db.delete(Constants.TABLE_NAME,Constants.KEY_ID+"=?", arrayOf(contact.id.toString()))
    }

    fun getContactsCount():Int
    {
        val countQuery="SELECT * FROM ${Constants.TABLE_NAME}"
        val db=this.readableDatabase
        val cursor=db.rawQuery( countQuery,null)

        return cursor.count
    }


}