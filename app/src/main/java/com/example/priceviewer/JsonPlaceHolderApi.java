package com.example.priceviewer;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {

    /*
    //main url den sonra gelen data path için ekleniyor https://jsonplaceholder.typicode.com/posts
    @GET("posts")
    Call<List<Post>> getPosts();
     */

    /*
    *   @GET("posts")
        Call<List<Post>> getPosts(
            @Query("UserID") Integer[] userId,
            @Query("_sort" ) String sort,
            @Query("_order") String order);
    * */

    // aşağıdaki query internet sayfasında = url/posts?userId=1&_sort=id&_order=desc şeklinde görülür.
    //her bir query parametresi & (ampersand) şeklinde eklenir.
    //int yerine Integer koyulursa null olark bu değer girilebilir

    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String, String > parameters);

    @GET("posts")
    Call<List<Post>> getPosts(
            @Query("UserID") Integer userId,
            @Query("_sort" ) String sort,
            @Query("_order") String order);

    // GETdeki id ile pathdaki id aynı olacak postId ismi farklı olabilir.
    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);


    //eğer parça parça değil direk url vermek için aşağıdaki şekilde girmek gerekecek.
    @GET
    Call<List<Comment>> getComments(@Url String url);


    @POST("posts")
    Call<Post> createPost(@Body Post post);

    //aşağıdaki ifadenin karşılşığı userId=23&title=New%20Title&body=New%20Text
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@FieldMap Map<String, String> fields);


    //add headers

    //post değerini tamamne değiştirmek için kullanılır
    @Headers({"Static-Header1: 123", "Static-Header2: 456"} )
    @PUT("posts/{id}")
    Call<Post> putPost(@Header("Dynamic-Header") String header, @Path("id") int id, @Body Post post);

    //post değerini tamamne değiştirmek için kullanılır
    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id, @Body Post post);

    //post değerindeki bir ifadeyi değiştirmek için kullanılır. Burada değiştirlmeyecek ifade için null gönderilmeli.
    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id") int id, @Body Post post);


    @PATCH("posts/{id}")
    Call<Post> patchPost(@HeaderMap Map<String , String> headers,
                         @Path("id") int id, @Body Post post);


    @DELETE("posts/{id}")
    Call<Void>  deletePost(@Path("id") int id);


}
