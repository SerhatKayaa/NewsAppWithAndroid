package com.example.newsappjava;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String API_KEY = "b730516c9c1c473ca584e058db9008fa";
    ListView listNews;
    ProgressBar loader;
    LinearLayout item;

    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
    static final String AUTHOR = "author";
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String URLTOIMAGE = "urlToImage";
    static final String PUBLISHEDAT = "publishedAt";
    static final String CONTENT= "content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listNews = findViewById(R.id.listNews);
        loader = findViewById(R.id.loader);
        listNews.setEmptyView(loader);
        item = findViewById(R.id.item);



        getNews news = new getNews();
        news.execute();

    }

    /* public boolean onTouchEvent(MotionEvent event) {
        Log.e("msg", "Touch event detected");
        return true;
    } */


    class getNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() { super.onPreExecute(); }

        protected String doInBackground(String... args) {
            try {
                if (HelperService.isOnline(getApplicationContext())) {
                    String xml = HelperService.getRequest("https://newsapi.org/v2/top-headlines?country=tr&apiKey=" + API_KEY);
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                    return jsonArray.toString();

                } else {
                    Database db = new Database(getApplicationContext());
                    ArrayList<HashMap<String, String>> haberler = db.haberler();
                    JSONArray jsArray = new JSONArray(haberler);
                    return jsArray.toString();
                }
            }
            catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
            }
            return "Error";
        }

        @Override
        protected void onPostExecute(String xml) {
            Database db = new Database(getApplicationContext());
            if (HelperService.isOnline(getApplicationContext())) {

                db.resetTables();
            }


            try {
                JSONArray jsonArray = new JSONArray(xml);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<>();
                    map.put(AUTHOR, jsonObject.optString(AUTHOR));
                    map.put(TITLE, jsonObject.optString(TITLE));
                    map.put(DESCRIPTION, jsonObject.optString(DESCRIPTION));
                    map.put(URLTOIMAGE, jsonObject.optString(URLTOIMAGE));
                    map.put(PUBLISHEDAT, jsonObject.optString(PUBLISHEDAT));
                    map.put(CONTENT, jsonObject.optString(CONTENT));
                    if (HelperService.isOnline(getApplicationContext())) {
                        db.haberEkle(jsonObject.optString(TITLE), jsonObject.optString(AUTHOR), jsonObject.optString(DESCRIPTION),
                                jsonObject.optString(CONTENT), jsonObject.optString(PUBLISHEDAT), jsonObject.optString(URLTOIMAGE));
                    }
                    dataList.add(map);
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
            }

            ListNewsAdapter adapter = new ListNewsAdapter(MainActivity.this, dataList);
            listNews.setAdapter(adapter);

            listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    System.out.println("Position" + position + dataList.get(position).get("author"));
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    intent.putExtra("image", dataList.get(position).get("urlToImage"));
                    intent.putExtra("author", dataList.get(position).get("author"));
                    intent.putExtra("title", dataList.get(position).get("title"));
                    intent.putExtra("content", dataList.get(position).get("content"));
                    intent.putExtra("publishedAt", dataList.get(position).get("publishAt"));
                    startActivity(intent);
                }
            });


        }
    }

}