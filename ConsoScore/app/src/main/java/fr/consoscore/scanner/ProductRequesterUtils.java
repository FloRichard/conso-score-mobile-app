package fr.consoscore.scanner;

import androidx.annotation.NonNull;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductRequesterUtils {
    public static class ProductNotFoundException extends Exception{
        public ProductNotFoundException(String barcode){
            super("Product " + barcode + " not found in the database.");
        }
    }

    public static ProductAPI.SellerProduct requestProduct(String barcode) throws IOException, ProductNotFoundException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductAPI productService = retrofit.create(ProductAPI.class);

        Call<ProductAPI.SellerProduct> call = productService.getProduct(barcode);
        Response<ProductAPI.SellerProduct> response = call.execute();
        if(response.isSuccessful()){
            return response.body();
        } else {
            throw new ProductNotFoundException(barcode);
        }
    }
}
