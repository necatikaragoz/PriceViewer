package com.example.priceviewer;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.priceviewer.ui.main.MainFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResults;

    JsonPlaceHolderApi jsonpalheolderApiObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();

        }
        textViewResults = findViewById(R.id.textView_Result);

        Gson gsonObj = new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); //ctrl + B ile source dosyasına gidiyhor.

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .header("Interceptor-Header", "xyz")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();


        Retrofit retrofitObj = new Retrofit.Builder().
                baseUrl("https://jsonplaceholder.typicode.com/").
                addConverterFactory(GsonConverterFactory.create(gsonObj))
                .client(okHttpClient)
                .build();

        jsonpalheolderApiObj = retrofitObj.create(JsonPlaceHolderApi.class);

        getPosts();

        //getComments();

        //createPost();

        //updatePost();

        //deletePost();

    }


    public void getPosts(){
        //Call<List<Post>> callObj = jsonpalheolderApiObj.getPosts();

        // Birden fazla integer girilmek istenirse
        // Call<List<Post>> callObj = jsonpalheolderApiObj.getPosts( new Integer[]{2,3,6}, null, null );

        // sort ve order gibi değerleri girilmek isteniyorsa "null" girilebilir.
        //Call<List<Post>> callObj = jsonpalheolderApiObj.getPosts( null, null, null );

        // mapli olan get post için aşağıdaki şekilde bir ifade girilebilir.
        Map<String, String> parameters = new HashMap<>();

        parameters.put("userId","1");
        parameters.put("_sort","id");
        parameters.put("_order","desc");
        //Call<List<Post>> callObj = jsonpalheolderApiObj.getPosts( parameters);

        Call<List<Post>> callObj = jsonpalheolderApiObj.getPosts( 1, "id", "desc" );


        callObj.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if(!response.isSuccessful())
                { // 404 hatası gibi hata gelirse
                    textViewResults.setText("code : " + response.code());
                    return;
                }
                List<Post> posts = response.body();

                for(Post  post : posts)
                {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getTest() + "\n\n";

                    textViewResults.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResults.setText(t.getMessage());
            }
        });

    }


    public void getComments(){
        //Call<List<Comment>> callObj = jsonpalheolderApiObj.getComments( 1 );

        Call<List<Comment>> callObj = jsonpalheolderApiObj.getComments("https://jsonplaceholder.typicode.com/posts/3/comments");

        callObj.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if(!response.isSuccessful())
                { // 404 hatası gibi hata gelirse
                    textViewResults.setText("code : " + response.code());
                    return;
                }
                List<Comment> cmts = response.body();

                for(Comment  cmt : cmts)
                {
                    String content = "";
                    content += "ID: " + cmt.getId() + "\n";
                    content += "Post ID: " + cmt.getPostId() + "\n";
                    content += "Name: " + cmt.getName() + "\n";
                    content += "Email: " + cmt.getEmail() + "\n";
                    content += "Text: " + cmt.getTest() + "\n\n";

                    textViewResults.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResults.setText(t.getMessage());
            }
        });

    }


    private void createPost(){

       // Post post = new Post(23, "new title", "new text" ) ;

        // for using map ?
        Map<String, String> mapObj = new HashMap<>();

        mapObj.put("userId", "25");
        mapObj.put("title", "New title");

        Call<Post> callObj = jsonpalheolderApiObj.createPost(mapObj);


        //Call<Post> callObj = jsonpalheolderApiObj.createPost(23, "New Title", "New Text");


        callObj.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(!response.isSuccessful())
                { // 404 hatası gibi hata gelirse
                    textViewResults.setText("code : " + response.code());
                    return;
                }

                Post postResponce = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";;
                content += "ID: " + postResponce.getId() + "\n";
                content += "User ID: " + postResponce.getUserId() + "\n";
                content += "Title: " + postResponce.getTitle() + "\n";
                content += "Text: " + postResponce.getTest() + "\n\n";

                textViewResults.append(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResults.setText(t.getMessage());
            }
        });

    }


    private void updatePost(){

         Post post = new Post(23, null, "new text" ) ;

        //Call<Post> callObj = jsonpalheolderApiObj.patchPost(5, post);
        //Call<Post> callObj = jsonpalheolderApiObj.putPost(5, post);

        //hedaer
        //Call<Post> callObj = jsonpalheolderApiObj.putPost("abc", 5, post);

        Map<String, String> header = new HashMap<>();

        header.put("Map-Header1", "def");
        header.put("Map-Header2", "ghi");

        Call<Post> callObj = jsonpalheolderApiObj.patchPost(header, 5, post);


        callObj.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(!response.isSuccessful())
                { // 404 hatası gibi hata gelirse
                    textViewResults.setText("code : " + response.code());
                    return;
                }

                Post postResponce = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";;
                content += "ID: " + postResponce.getId() + "\n";
                content += "User ID: " + postResponce.getUserId() + "\n";
                content += "Title: " + postResponce.getTitle() + "\n";
                content += "Text: " + postResponce.getTest() + "\n\n";

                textViewResults.append(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResults.setText(t.getMessage());
            }
        });

    }

    private void deletePost(){

        Call<Void> callObj = jsonpalheolderApiObj.deletePost(5);

        callObj.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                textViewResults.setText("code : " + response.code());
                // ekranda 200 görünmesi işlemin doğru olduğunu gösteriyor.
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResults.setText(t.getMessage());
            }
        });

    }
}

