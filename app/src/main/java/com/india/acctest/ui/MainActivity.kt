package com.india.acctest.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.india.acctest.R
import com.india.acctest.data.db.AppDatabase
import com.india.acctest.data.models.Animal
import com.india.acctest.data.network.NetworkCall
import com.india.acctest.data.repository.AnimalRepo
import com.india.acctest.ui.viewmodel.AnimalViewModel
import com.india.acctest.ui.viewmodel.AnimalViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var db:AppDatabase
    private lateinit var networkCall: NetworkCall
    private lateinit var animalRepo: AnimalRepo
    private lateinit var animalViewModelFactory: AnimalViewModelFactory
    private lateinit var animalViewModel: AnimalViewModel
    private var list = mutableListOf<Animal>()
    private lateinit var animalAdapter: AnimalAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase(this)
        networkCall = NetworkCall()
        animalRepo = AnimalRepo(networkCall,db)
        animalViewModelFactory = AnimalViewModelFactory(animalRepo)

        animalAdapter = AnimalAdapter(this,list)
        animalRecyclerView.adapter= animalAdapter
        animalRecyclerView.layoutManager = LinearLayoutManager(this)

        animalViewModel = ViewModelProviders.of(this, animalViewModelFactory)
            .get(AnimalViewModel::class.java)

        title = "About Canada"


        Coroutines.io{

            animalViewModel.callAnimalList()
        }

        animalViewModel.animalList.observe(this, Observer {

            Coroutines.io {
                animalViewModel.clearTable()
            }

            list.addAll(it)
            animalAdapter.notifyDataSetChanged()
        })
    }


}


class AnimalAdapter( private val context:Context,private var animals:List<Animal>):RecyclerView.Adapter<AnimalAdapter.ItemViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    class ItemViewHolder(v:View): RecyclerView.ViewHolder(v){
        val title:TextView = v.findViewById(R.id.titleTextView)
        val desc:TextView = v.findViewById(R.id.descTextView)
        val img:ImageView = v.findViewById(R.id.imageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            inflater.inflate(R.layout.cell_animal, parent, false)

      return  ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return animals.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val animal = animals[position]

        animal.title?.let {
            holder.title.text = it
        }

        animal.description?.let {
            holder.desc.text = it
        }

        animal.imageHref?.let {

             val requestOptions =
                 RequestOptions().placeholder(R.drawable.ic_launcher_background)
                     .error(R.drawable.ic_launcher_background)

             Glide.with(context)
                 .setDefaultRequestOptions(requestOptions)
                 .load(it)
                 .into(holder.img)
        }
    }
}



object Coroutines {


    fun io(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.IO).launch {
            work()
        }

}