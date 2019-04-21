package werpx.marketopia;
import java.util.ArrayList;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import werpx.marketopia.categoriesmodel.categoryroot;
import werpx.marketopia.modelsauth.Roottoken;
import werpx.marketopia.pointsmodel.Pointroot;
import werpx.marketopia.productmodels.Rootproductdetail;
import werpx.marketopia.productmodels.getallproductsroot;
import werpx.marketopia.salehistorymodel.Selleshistory;
import werpx.marketopia.salemodel.Saleobject;
import werpx.marketopia.salemodel.Saleroot;
import werpx.marketopia.storemodel.Storecode;
import werpx.marketopia.storemodelbyid.Storeroot;
import werpx.marketopia.usermodels.Userroot;

public interface Endpoints {


    @POST("barcode")
    Call<Rootproductdetail> getdetails(@Header("Authorization") String token, @Query("barcode") String barcodeproduct);

    @FormUrlEncoded
    @POST("login")
    Call<Roottoken> signuser(@Header("Content-Type") String content, @Field("email") String email, @Field("password") String string);

    @GET("user")
    Call<Userroot> getuserdata(@Header("Authorization") String auth);

    @GET("product")
    Call<getallproductsroot> getproductdetails(@Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("retailersale")
    Call<Saleroot> getsalecondition(@Field("barcode") String barcode, @Field("quantity") int qua, @Field("retailer_id") String id, @Field("live") Boolean value, @Field("product_id") String productid);


    @Multipart
    @POST("image")
    Call<ResponseBody> uploadimg(@Part MultipartBody.Part img, @Part("type") RequestBody mtype);

    @FormUrlEncoded
    @POST("appstore")
    Call<ResponseBody> registerstore(@Field("latitude") String lat, @Field("longitude") String longtitude
            , @Field("address") String addres, @Field("image_id") String imgid, @Field("active") boolean active
            , @Field("city") String city, @Field("close_delivery") String Cdelivery
            , @Field("close_time") String Ctime, @Field("currency") String currency, @Field("del_charge") String deletecharge,
                                     @Field("delivery_time") String Dtime, @Field("description") String desc, @Field("email") String email
            , @Field("name") String nam, @Field("open_delivery") String Odelivery, @Field("open_time") String Otime
            , @Field("phone") String phone, @Field("place_id") String placeid, @Field("website") String web, @Field("category") String storetype);


    @FormUrlEncoded
    @POST("appretailer")
    Call<ResponseBody> registerretailer(@Field("store") String id, @Field("name") String adminname, @Field("email") String email
            , @Field("password") String pass, @Field("mobile") String mob, @Field("level") String level, @Field("category") String cat);

    @GET("store")
    Call<Storeroot> getstore(@Header("Authorization") String token, @Query("id") String storeid);

    @FormUrlEncoded
    @PATCH("retailer/id")
    Call<ResponseBody> updateretailerinfo(@Header("Authorization") String token, @Query("id") String retailerid, @Field("store") String id, @Field("name") String adminname, @Field("email") String email
            , @Field("password") String pass, @Field("mobile") String mob, @Field("level") String level);


    @FormUrlEncoded
    @POST("getstorebycode")
    Call<Storecode> getstorebycode(@Field("code") String storecode);

    @FormUrlEncoded
    @POST("salehistory")
    Call<Selleshistory> getsalehistory(@Field("retailer_id") String retailerid);


    @FormUrlEncoded
    @POST("loyalty/retailerpoints")
    Call<Pointroot> getretailerpoints(@Header("Authorization") String token, @Field("id") String retailerid);


    @POST("retailersale")
    Call<ResponseBody> saleall(@Body ArrayList<Saleobject> data);

    @FormUrlEncoded
    @POST("product")
    Call<ResponseBody> addproduct(@Header("Authorization") String token, @Field("barcode") String barcode, @Field("category") String category
            , @Field("description") String description, @Field("name") String name, @Field("price") String price, @Field("producer") String producer
            , @Field("reorder") String reorder, @Field("unit") String unit, @Field("localname") String localname);


    @FormUrlEncoded
    @POST("product")
    Call<ResponseBody> addproductsasarray(@Header("Authorization") String token, @Field("barcode[]") ArrayList<String> barcode, @Field("category[]") ArrayList<String> category
            , @Field("description[]") ArrayList<String> description, @Field("name[]") ArrayList<String> name, @Field("price[]") ArrayList<String> price, @Field("producer[]") ArrayList<String> producer
            , @Field("reorder[]") ArrayList<String> reorder, @Field("unit[]") ArrayList<String> unit, @Field("localname[]") ArrayList<String> localname);


    @FormUrlEncoded
    @POST("retailersale")
    Call<ResponseBody> salearray(
            @Field("barcode[]") ArrayList<String> barcodes, @Field("quantity[]") ArrayList<String> quantities, @Field("retailer_id[]") ArrayList<String> retailerids,
            @Field("product_id[]") ArrayList<String> productids, @Field("live[]") ArrayList<Boolean> lives);

    @FormUrlEncoded
    @PATCH("store/{id}")
    Call<ResponseBody> updatestoreinfo(@Header("Authorization") String token, @Path("id") String storeid, @Field("id") String id, @Field("latitude") String lat, @Field("longitude") String longtitude
            , @Field("address") String addres, @Field("image_id") String imgid, @Field("active") boolean active
            , @Field("city") String city, @Field("close_delivery") String Cdelivery
            , @Field("close_time") String Ctime, @Field("currency") String currency, @Field("del_charge") String deletecharge,
                                       @Field("delivery_time") String Dtime, @Field("description") String desc, @Field("email") String email
            , @Field("name") String nam, @Field("open_delivery") String Odelivery, @Field("open_time") String Otime
            , @Field("phone") String phone, @Field("place_id") String placeid,
                                       @Field("website") String web, @Field("category") String storetype, @Field("_method") String method);

    @GET("category")
    Call<categoryroot> getcategories();


    @FormUrlEncoded
    @POST("productname")
    Call<getallproductsroot> getsserchproductbyName(@Header("Authorization") String token, @Field("productname") String searchname);

//


    @FormUrlEncoded
    @POST("consumer/create")
    Call<ResponseBody> registerconsumer(@Header("Content-Type")String contenttype,@Field("name")String name,@Field("mobile")String mobile,@Field("password")String pass,@Field("email")String email,@Field("address")String address,@Field("city")String city ,@Field("image_id")String imageid);

}
