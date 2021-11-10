package com.example.paringjsonfile

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paringjsonfile.adapters.FavImagesAdapter
import com.example.paringjsonfile.database.DatabaseImage
import com.example.paringjsonfile.database.EntityImage
import com.example.paringjsonfile.database.RepositoryImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private val imageDao by lazy { DatabaseImage.getDatabase(this).imageDao()}
    private val repository  by lazy { RepositoryImage(imageDao) }
    lateinit var rvView : RecyclerView
    lateinit var lv : List<EntityImage>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        rvView = findViewById(R.id.rcvF)
        lv = listOf()
        getNotesList()
        updateRV()
    }
    fun getNotesList(){
        CoroutineScope(Dispatchers.IO).launch {
            val data = async {
                repository.getNotes
            }.await()
            if(data.isNotEmpty()){
                lv = data
                updateRV()
            }else{
                Log.e("MainActivity", "Unable to get data", )
            }
        }
    }
    fun updateRV(){
        rvView.adapter = FavImagesAdapter(this, lv)
        rvView.layoutManager = LinearLayoutManager(this)

    }
    fun deleteNote(ID : Int, title: String, author: String){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteImage(EntityImage(ID, title,author))
        }
    }
    fun checkDeleteDialog(id: Int){
        val dialogBuilder = AlertDialog.Builder(this)
        val checkTextView = TextView(this)
        checkTextView.text = "  Are sure you want to delete this Image\nfrom your favorite list ?!"

        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {

                    _, _ ->
                run{
                    deleteNote(id, "","")
                    Toast.makeText(this, "image got deleted", Toast.LENGTH_LONG).show()
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Check the deletion")
        alert.setView(checkTextView)
        alert.show()
    }
    }
