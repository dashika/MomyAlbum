package cf.dashika.momyalbum.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import cf.dashika.momyalbum.Model.DAO.IMamyDAO
import cf.dashika.momyalbum.Model.Entity.*
import cf.dashika.momyalbum.Util.DATABASE_NAME
import cf.dashika.momyalbum.Worker.SeedDatabaseWorker

/**
 * The Room database for this app
 */
@Database(entities = [Mamy::class, Baby::class, Post::class, PostContainer::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mamyDao(): IMamyDAO

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                        WorkManager.getInstance().enqueue(request)
                    }
                })
                .build()
        }
    }
}
