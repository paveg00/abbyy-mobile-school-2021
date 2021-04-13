package com.example.netsample

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity(), View.OnClickListener {
	private lateinit var textView: TextView
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		textView = findViewById(R.id.textView)
		findViewById<View>(R.id.badLoadButton).setOnClickListener(this)
		findViewById<View>(R.id.loadButton).setOnClickListener(this)
		findViewById<View>(R.id.loadAsyncButton).setOnClickListener(this)
		findViewById<View>(R.id.loadVolleyButton).setOnClickListener(this)
		findViewById<View>(R.id.loadRetrofitButton).setOnClickListener(this)
	}

	override fun onClick(v: View) {
		when(v.id) {
			R.id.badLoadButton -> onBadLoadClick()
			R.id.loadButton -> onLoadClick()
			R.id.loadAsyncButton -> loadPostAsync()
			R.id.loadVolleyButton -> loadPostVolley()
			R.id.loadRetrofitButton -> loadPostRetrofit()
		}
	}

	private fun onBadLoadClick() {
		showOnTextView(loadPost())
	}

	@SuppressLint("StaticFieldLeak")
	private fun onLoadClick() {
		showOnTextView("")
		object : AsyncTask<Void?, Void?, String>() {
			override fun doInBackground(vararg voids: Void?): String {
				return loadPost()
			}

			override fun onPostExecute(s: String) {
				showOnTextView(s)
			}
		}.execute()
	}

	private fun showOnTextView(post: String) {
		textView.text = post
	}

	private fun loadPost(): String {
		var connection: HttpURLConnection? = null
		try {
			val u = URL(POSTS_URL + "posts/")
			connection = u.openConnection() as HttpURLConnection
			connection.requestMethod = "GET"
			connection.connect()
			val status = connection.responseCode
			if (status != HttpURLConnection.HTTP_OK) {
				return "Error"
			} else {
				BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
					val stringBuilder = StringBuilder()
					var line: String?
					while (reader.readLine().also { line = it } != null) {
						stringBuilder.append(line).append("\n")
					}
					return getPostFromJson(JSONArray(stringBuilder.toString()))
				}
			}
		} catch (ex: IOException) {
			return "Error"
		} finally {
			connection?.disconnect()
		}
	}

	private fun loadPostAsync() {
		showOnTextView("")
		val okHttpClient = OkHttpClient()
		// Get по умолчанию
		val request: Request = Request.Builder().url(POSTS_URL + "posts/").build()
		// Асинхронность из коробки
		okHttpClient.newCall(request).enqueue(object : okhttp3.Callback {
			override fun onFailure(call: okhttp3.Call, e: IOException) {}

			@Throws(IOException::class)
			override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
				if(response.isSuccessful) {
					val jsonArray = JSONArray(response.body!!.string())
					val title = getPostFromJson(jsonArray)
					runOnUiThread { showOnTextView(title) }
				}
			}
		})
	}

	private fun getPostFromJson(jsonArray: JSONArray): String {
		return try {
			val jsonObject = jsonArray.getJSONObject(0)
			jsonObject.getString("title")
		} catch (e: JSONException) {
			"Error"
		}
	}

	private fun loadPostVolley() {
		val queue = Volley.newRequestQueue(this)
		showOnTextView("")
		val jsonRequest = JsonArrayRequest(com.android.volley.Request.Method.GET, POSTS_URL + "posts/",
			null,
			{
				showOnTextView(getPostFromJson(it))
			},
			{
				showOnTextView("Fail")
			}
		)
		queue.add(jsonRequest)
	}

	private fun loadPostRetrofit() {
		val retrofit = Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl(POSTS_URL)
			.build()
		val api = retrofit.create(Api::class.java)
		val call = api.posts
		showOnTextView("")
		call.enqueue(object : Callback<List<Post>> {
			override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
				if (response.isSuccessful) {
					val body = response.body() ?: return
					if (body.isEmpty()) return
					runOnUiThread { showOnTextView(body[0].title) }
				}
			}

			override fun onFailure(call: Call<List<Post>>, t: Throwable) {}
		})
	}

	internal class Post(
		@SerializedName("title")
		val title: String
	)

	internal interface Api {
		@get:GET("posts")
		val posts: Call<List<Post>>
	}

	companion object {
		private const val POSTS_URL = "https://jsonplaceholder.typicode.com/"
	}
}