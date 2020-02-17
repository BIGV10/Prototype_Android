package com.example.prototype.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prototype.R
import com.example.prototype.adapter.RequestAdapter
import com.example.prototype.ServiceBuilder
import com.example.prototype.model.Request
import com.example.prototype.service.RequestService
import kotlinx.android.synthetic.main.activity_last_requests.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LastRequestsActivity : AppCompatActivity() {

    var requestList = ArrayList<Request>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last_requests)

        recyclerView_requestsList.layoutManager = LinearLayoutManager(this)
        recyclerView_requestsList.adapter = RequestAdapter(requestList)

        loadLastRequests()
    }

    fun loadLastRequests() {
        val requestService =
            ServiceBuilder.buildService(RequestService::class.java)
        val requestCall = requestService.getLastRequests()
        requestCall.enqueue(object : Callback<List<Request>> {
            override fun onResponse(call: Call<List<Request>>, response: Response<List<Request>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    requestList = ArrayList(body!!)
                    recyclerView_requestsList.adapter = RequestAdapter(requestList)
                    Toast.makeText(
                        this@LastRequestsActivity,
                        "Всё загрузилось\n" + response.code(), Toast.LENGTH_LONG
                    ).show()

                } else { //Status code is not 200's
                    if (response.code() == 500) {
                        Toast.makeText(
                            this@LastRequestsActivity,
                            "\n" + response.code(), Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this@LastRequestsActivity,
                            "Не удалось получить информацию о заявках" + response.code(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<Request>>, t: Throwable) {
                Toast.makeText(this@LastRequestsActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
