package com.hefei.retrofit.api;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Multipart;
import retrofit2.http.OPTIONS;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/07/28
 *     desc  :
 * </pre>
 */
public interface ApiService {

    @GET()
    Call<ResponseBody> get(@Url String url, @QueryMap Map<String, String> maps);

    @FormUrlEncoded
    @POST()
    Call<ResponseBody> post(@Url String url, @FieldMap Map<String, String> maps);

    @FormUrlEncoded
    @POST()
    Call<ResponseBody> postForm(@Url() String url, @FieldMap Map<String, Object> maps);

    @POST()
    Call<ResponseBody> postBody(@Url() String url, @Body RequestBody requestBody);

    @HEAD()
    Call<ResponseBody> head(@Url String url, @QueryMap Map<String, String> maps);

    @OPTIONS()
    Call<ResponseBody> options(@Url String url, @QueryMap Map<String, String> maps);

    @FormUrlEncoded
    @PUT()
    Call<ResponseBody> put(@Url() String url, @FieldMap Map<String, String> maps);

    @FormUrlEncoded
    @PATCH()
    Call<ResponseBody> patch(@Url() String url, @FieldMap Map<String, String> maps);

    @FormUrlEncoded
    @DELETE()
    Call<ResponseBody> delete(@Url() String url, @FieldMap Map<String, String> maps);

    @Streaming
    @GET()
    Call<ResponseBody> downFile(@Url() String url, @QueryMap Map<String, String> maps);

    @Multipart
    @POST()
    Call<ResponseBody> uploadFiles(@Url() String url, @Part() List<MultipartBody.Part> parts);

}
