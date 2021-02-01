package ru.trinitydigital.pagingcashe.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.trinitydigital.pagingcashe.data.model.PageKeys
import ru.trinitydigital.pagingcashe.data.model.RowsModel

@Database(entities = [RowsModel::class, PageKeys::class], version = 1)
abstract class PagingCasheAppDatabase : RoomDatabase() {
    abstract fun getPagingCasheDao(): PagingCasheDao
    abstract fun getPagingKeysDao(): PageKeysDao

    companion object {
        fun getInstanceDB(context: Context): PagingCasheAppDatabase {
            return Room.databaseBuilder(context, PagingCasheAppDatabase::class.java, "myDb")
                .allowMainThreadQueries()
                .build()
        }
    }
}