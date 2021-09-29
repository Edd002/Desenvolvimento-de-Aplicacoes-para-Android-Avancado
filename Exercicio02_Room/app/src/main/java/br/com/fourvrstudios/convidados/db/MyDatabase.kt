package br.com.fourvrstudios.convidados.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Convidado::class], version = 1, exportSchema = true)
abstract class MyDatabase :
    RoomDatabase() {
    abstract val convidadoDAO: ConvidadoDAO

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "my_database"
                    ).build()
                }
                return instance
            }
        }
    }
}