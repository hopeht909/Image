package com.example.paringjsonfile

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paringjsonfile.adapters.RVAdapter
import com.example.paringjsonfile.database.DatabaseImage
import com.example.paringjsonfile.database.DoaImage
import com.example.paringjsonfile.database.EntityImage
import com.example.paringjsonfile.database.RepositoryImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val imageDao by lazy { DatabaseImage.getDatabase(this).imageDao()}
    private val repository  by lazy { RepositoryImage(imageDao) }

    lateinit var rcv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rcv = findViewById(R.id.rcv)

        val jsonf = getJsonData(this, "data.json")
        val jsonArray = JSONArray(jsonf)
        val images = arrayListOf<Image>()

        rcv.adapter = RVAdapter(this, images)
        rcv.layoutManager = LinearLayoutManager(this)

        for (i in 0 until jsonArray.length()) {
            val author = jsonArray.getJSONObject(i).getString( "author")
            val id = jsonArray.getJSONObject(i).getString("id")
            val url = jsonArray.getJSONObject(i).getString("download_url")
            images.add(Image(id,author,url))
        }
    }

    private fun getJsonData(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite ->{
            val intent = Intent(this,FavoriteActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }
    fun addToFavorite(author:String ,url: String){
        val dialogBuilder = AlertDialog.Builder(this)
        val checkTextView = TextView(this)
        checkTextView.text = "  You want to add this Image to your favorite List ?!"

        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {

                    _, _ ->
                run{
                    addImage(author,url)
                    Toast.makeText(this, "Image got added", Toast.LENGTH_LONG).show()
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Confirmation")
        alert.setView(checkTextView)
        alert.show()
    }

    private fun addImage(author: String, url: String) {
            CoroutineScope(Dispatchers.IO).launch {
                repository.addImage(EntityImage(0, author,url))
            }
        }
    }
