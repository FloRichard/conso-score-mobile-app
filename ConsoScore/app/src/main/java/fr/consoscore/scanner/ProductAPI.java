package fr.consoscore.scanner;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductAPI {
    class SellerProduct {
        public String bar_code;
        public int carbon_foot_print;
        public String category;
        public float conso_score;
        public String name;
        public float price;
        public String product_id;
        public String quantity_unity;
        public int tax;
        public String transport;
    }

    @GET("/datas/product/{bar_code}")
    Call<SellerProduct> getProduct(@Path("bar_code") String barcode);
}
