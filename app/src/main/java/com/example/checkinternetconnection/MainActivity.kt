package com.example.checkinternetconnection

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.checkinternetconnection.viewmodel.ConnectionType
import com.example.checkinternetconnection.viewmodel.InternetViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : InternetViewModel

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel = ViewModelProvider(this).get(InternetViewModel::class.java)



        // Button Check Internet ..
        Button_CheckInternet.setOnClickListener {
          viewModel.checkInternetConnection(this)
        }


        viewModel.internetConnection.observe(this, Observer { connection->
          when(connection){
              true ->{
                  TextView_InternetState.text = "Connected !"
                  TextView_InternetState.setTextColor(Color.GREEN)
              } else->{
              TextView_InternetState.text = "No Connection !"
              TextView_InternetState.setTextColor(Color.RED)
              TextView_ConnectionType.text = "No Type!"
              }
          }
        })

        viewModel.connectionType.observe(this, Observer { type->
            when(type){
                ConnectionType.WIFI->{ TextView_ConnectionType.text = "WiFi"}
                ConnectionType.CELLULAR->{ TextView_ConnectionType.text = "Cellular"}
                ConnectionType.ETHERNET->{ TextView_ConnectionType.text = "Ethernet"}
                ConnectionType.NO_CONNECTION->{ TextView_ConnectionType.text = "No Type"}
            }
        })



    }







}