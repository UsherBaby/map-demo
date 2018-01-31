package com.pacific.arch.example

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.content.Context

@Entity(tableName = "my_content")
data class MyContent(@ColumnInfo(name = "content") val content: String,
                     @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int? = null)

@Dao
interface MyContentDao {
    @Insert(onConflict = REPLACE)
    fun save(content: MyContent)
}


@Database(entities = [(MyContent::class)], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun contentDao(): MyContentDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase? {
            if (INSTANCE == null) {
                synchronized(MyDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                                context.applicationContext,
                                MyDatabase::class.java,
                                "myDatabase.db"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}