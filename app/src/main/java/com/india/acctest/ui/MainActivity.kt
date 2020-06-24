package com.india.acctest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.india.acctest.R
import com.india.acctest.data.db.AppDatabase
import com.india.acctest.data.network.NetworkCall
import com.india.acctest.data.repository.AnimalRepo
import com.india.acctest.ui.viewmodel.AnimalViewModel
import com.india.acctest.ui.viewmodel.AnimalViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var db:AppDatabase
    private lateinit var networkCall: NetworkCall
    private lateinit var animalRepo: AnimalRepo
    private lateinit var animalViewModelFactory: AnimalViewModelFactory
    private lateinit var animalViewModel: AnimalViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase(this)
        networkCall = NetworkCall()
        animalRepo = AnimalRepo(networkCall,db)
        animalViewModelFactory = AnimalViewModelFactory(animalRepo)

        animalViewModel = ViewModelProviders.of(this, animalViewModelFactory)
            .get(AnimalViewModel::class.java)

        title = "--"


        Coroutines.io{

            animalViewModel.callAnimalList()
        }

        animalViewModel.animalList.observe(this, Observer {
            Log.d("LIST",it.toString())
        })
    }
}



object Coroutines {


    fun io(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.IO).launch {
            work()
        }

}