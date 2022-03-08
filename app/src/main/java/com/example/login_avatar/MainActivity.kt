package com.example.login_avatar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContentProviderCompat.requireContext
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.login_avatar.adapter.UserAdapter
import com.example.login_avatar.databinding.ActivityMainBinding
import com.example.login_avatar.databinding.LayoutBottomSheetBinding
import com.example.login_avatar.models.User
import com.example.login_avatar.models.Valyuta
import com.example.login_avatar.utils.NetworkHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var networkHelper: NetworkHelper
    lateinit var requestQueue: RequestQueue
    var url = "http://cbu.uz/uzc/arkhiv-kursov-valyut/json/"
    private val TAG = "MainActivity"
    private lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        networkHelper = NetworkHelper(this)

        if (networkHelper.isNetworkConnected()){
            binding.tekstt.visibility = View.GONE
            requestQueue = Volley.newRequestQueue(this)
            val jsonArrayRequest = JsonArrayRequest(Request.Method.GET,url,null,
            object : Response.Listener<JSONArray>{
                override fun onResponse(response: JSONArray?) {
                    val type = object : TypeToken<List<Valyuta>>(){}.type
                    var list:List<Valyuta> = Gson().fromJson(response.toString(),type)
                    userAdapter = UserAdapter(list,object:UserAdapter.OnItemClickListener{
                        override fun onItemClick(valyuta: Valyuta) {

                            var bottomsheet = BottomSheetDialog(binding.root.context)
                            val v = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)
                            val bottom_sheetbinding =
                                LayoutBottomSheetBinding.bind(v)


                            bottom_sheetbinding.index.text = "1 ${valyuta.CcyNm_UZ} = ${valyuta.Rate} so'm"
                            bottom_sheetbinding.date.text = "${valyuta.Date}"
                            bottom_sheetbinding.understand.setOnClickListener {
                                bottomsheet.dismiss()
                            }


                            bottomsheet.setContentView(v)
                            bottomsheet.show()




                        }

                    })
                    binding.rv.adapter = userAdapter

                    for (user in list) {
                        Log.d(TAG, "onResponse: $user")
                    }
                }


            },object:Response.ErrorListener{
                    override fun onErrorResponse(error: VolleyError?) {

                    }

                })
            requestQueue.add(jsonArrayRequest)

        }else {
            binding.tekstt.visibility = View.VISIBLE
            binding.tekstt.text = "Your internet is disconnected"
        }
    }
}