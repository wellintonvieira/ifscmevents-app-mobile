package br.com.ifscmeventsapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.ifscmeventsapp.model.Event
import br.com.ifscmeventsapp.model.Speaker
import br.com.ifscmeventsapp.model.Talk
import java.util.ArrayList

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASENAME, null, DATABASEVERSION){

    companion object {
        private const val DATABASENAME = "ifscmeventsapp.db"
        private const val DATABASEVERSION = 1
    }

    override fun onCreate(database: SQLiteDatabase?) {
        onCreateTable(database, "EVENT")
        onCreateTable(database, "SPEAKER")
        onCreateTable(database, "TALK")
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onDropTable(database, "EVENT")
        onDropTable(database, "SPEAKER")
        onDropTable(database, "TALK")
        onCreate(database)
    }

    private fun onCreateTable(database: SQLiteDatabase?, item: String){
        when (item) {
            "EVENT" -> {
                val query = "CREATE TABLE IF NOT EXISTS EVENT(id TEXT, name TEXT, `desc` TEXT, startDate TEXT, finishDate TEXT, image TEXT, latitude TEXT, longitude TEXT)"
                database!!.execSQL(query)
            }
            "SPEAKER" -> {
                val query = "CREATE TABLE IF NOT EXISTS  SPEAKER(id, TEXT, name TEXT, email TEXT, formation TEXT, bio TEXT, image TEXT)"
                database!!.execSQL(query)
            }
            else -> {
                val query = "CREATE TABLE IF NOT EXISTS TALK(id TEXT, idEvent TEXT, name TEXT, category TEXT, `desc` TEXT, maxPeople TEXT, date TEXT, startTime TEXT, finishTime TEXT, location TEXT)"
                database!!.execSQL(query)
            }
        }
    }

    private fun onDropTable(database: SQLiteDatabase?, item: String){
        when (item) {
            "EVENT" -> database!!.execSQL("DROP TABLE IF EXISTS EVENT")
            "SPEAKER" -> database!!.execSQL("DROP TABLE IF EXISTS SPEAKER")
            else -> database!!.execSQL("DROP TABLE IF EXISTS TALK")
        }
    }

    fun addEvents(events: List<Event>){
        val database = this.writableDatabase
        onDropTable(database, "EVENT")
        onCreateTable(database, "EVENT")

        for(event in events){
            var values = ContentValues()
            values.put("id", event.id)
            values.put("name", event.name)
            values.put("desc", event.desc)
            values.put("startDate", event.startDate)
            values.put("finishDate", event.finishDate)
            values.put("latitude", event.latitude)
            values.put("longitude", event.longitude)
            database.insert("EVENT", null, values)
        }
        database.close()
    }

    fun getEvents() : List<Event>{
        val database = this.writableDatabase
        val events = ArrayList<Event>()
        val cursor : Cursor
        cursor = database.rawQuery("SELECT * FROM EVENT", null)

        if(cursor != null){
            if(cursor.count > 0){
                cursor.moveToFirst()
                do {
                    val id = cursor.getString(cursor.getColumnIndex("id"))
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val desc = cursor.getString(cursor.getColumnIndex("desc"))
                    val startDate = cursor.getString(cursor.getColumnIndex("startDate"))
                    val finishDate = cursor.getString(cursor.getColumnIndex("finishDate"))
                    val latitude = cursor.getString(cursor.getColumnIndex("latitude"))
                    val longitude = cursor.getString(cursor.getColumnIndex("longitude"))
                    val event = Event(id.toLong(), name, desc, startDate, finishDate, "", latitude, longitude)
                    events.add(event)
                } while (cursor.moveToNext())
            }
        }
        database.close()
        return events
    }

    fun addSpeakers(speakers: List<Speaker>){
        val database = this.writableDatabase
        onDropTable(database, "SPEAKER")
        onCreateTable(database, "SPEAKER")

        for(speaker in speakers){
            var values = ContentValues()
            values.put("id", speaker.id)
            values.put("name", speaker.name)
            values.put("email", speaker.email)
            values.put("formation", speaker.formation)
            values.put("bio", speaker.bio)
            database.insert("SPEAKER", null, values)
        }
        database.close()
    }

    fun getSpeakers() : List<Speaker>{
        val database = this.writableDatabase
        val speakers = ArrayList<Speaker>()
        val cursor : Cursor
        cursor = database.rawQuery("SELECT * FROM SPEAKER", null)

        if(cursor != null){
            if(cursor.count > 0){
                cursor.moveToFirst()
                do {
                    val id = cursor.getString(cursor.getColumnIndex("id"))
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val email = cursor.getString(cursor.getColumnIndex("email"))
                    val formation = cursor.getString(cursor.getColumnIndex("formation"))
                    val bio = cursor.getString(cursor.getColumnIndex("bio"))
                    val speaker = Speaker(id.toLong(), name, email, formation, bio, "")
                    speakers.add(speaker)
                } while (cursor.moveToNext())
            }
        }
        database.close()
        return speakers
    }

    fun addTalks(talks: List<Talk>, id : Long){
        val database = this.writableDatabase
        onDropTable(database, "TALK")
        onCreateTable(database, "TALK")

        for(talk in talks){
            var values = ContentValues()
            values.put("id", talk.id)
            values.put("idEvent", id)
            values.put("name", talk.name)
            values.put("category", talk.category)
            values.put("desc", talk.desc)
            values.put("date", talk.date)
            values.put("startTime", talk.startTime)
            values.put("finishTime", talk.finishTime)
            values.put("maxPeople", talk.maxPeople)
            values.put("location", talk.location)
            database.insert("TALK", null, values)
        }
        database.close()
    }

    fun getTalks(id : Long) : List<Talk>{
        val database = this.writableDatabase
        val talks = ArrayList<Talk>()
        val cursor : Cursor
        cursor = database.rawQuery("SELECT * FROM TALK WHERE idEvent = $id", null)

        if(cursor != null){
            if(cursor.count > 0){
                cursor.moveToFirst()
                do {
                    val id = cursor.getString(cursor.getColumnIndex("id"))
                    val idEvent = cursor.getString(cursor.getColumnIndex("idEvent"))
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val category = cursor.getString(cursor.getColumnIndex("category"))
                    val desc = cursor.getString(cursor.getColumnIndex("desc"))
                    val maxPeople = cursor.getString(cursor.getColumnIndex("maxPeople"))
                    val date = cursor.getString(cursor.getColumnIndex("date"))
                    val startTime = cursor.getString(cursor.getColumnIndex("startTime"))
                    val finishTime = cursor.getString(cursor.getColumnIndex("finishTime"))
                    val location = cursor.getString(cursor.getColumnIndex("location"))
                    val talk = Talk(id.toLong(), idEvent.toLong(), name, category, desc, maxPeople.toInt(), date, startTime, finishTime, location)
                    talks.add(talk)
                } while (cursor.moveToNext())
            }
        }
        database.close()
        return talks
    }
}