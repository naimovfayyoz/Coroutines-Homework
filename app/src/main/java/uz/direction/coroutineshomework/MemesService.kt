package uz.direction.coroutineshomework

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.InputStream
import java.nio.charset.Charset
import kotlin.random.Random


class MemesService {

    suspend fun subscribeToMemes(scope: CoroutineScope, context: Context): Meme {
        //should we cancel this job?
        val job = scope.async(Dispatchers.IO) {
            val jsonString: String
            val inputStream: InputStream = context.assets.open("facts.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            jsonString = String(buffer, Charset.defaultCharset())

            val memes = Gson().fromJson(jsonString, Array<Meme>::class.java)
            val randomIndex = Random.nextInt(memes.size - 1)
            return@async memes[randomIndex]
        }
        return job.await()
    }
}
