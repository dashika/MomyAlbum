package cf.dashika.momyalbum.Worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import cf.dashika.momyalbum.Model.AppDatabase
import cf.dashika.momyalbum.Model.Entity.Baby
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader

class SeedDatabaseWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    private val TAG by lazy { SeedDatabaseWorker::class.java.simpleName }

    override fun doWork(): Result {
//        val plantType = object : TypeToken<List<Baby>>() {}.type
//        var jsonReader: JsonReader? = null
//
//        return try {
//            val inputStream = applicationContext.assets.open(PLANT_DATA_FILENAME)
//            jsonReader = JsonReader(inputStream.reader())
//            val plantList: List<Baby> = Gson().fromJson(jsonReader, plantType)
//            val database = AppDatabase.getInstance(applicationContext)
//            database.plantDao().insertAll(plantList)
//            Result.SUCCESS
//        } catch (ex: Exception) {
//            Log.e(TAG, "Error seeding database", ex)
//            Result.FAILURE
//        } finally {
//            jsonReader?.close()
//        }
        return Result.success()
    }
}
