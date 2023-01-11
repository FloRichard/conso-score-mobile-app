package fr.consoscore.scanner;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductAPI {
    class SellerProduct {
        public String id;
        public double price;
        public double conso_score;
        public double bar_code;
        public double tax;
        public Product maker_product;
    }
    class Product {
        public String id;
        public String name;
        public double price;
        public double carbon_foot_print;
        public String quantity_unity;
        public String category_id;
        public String expedition_transport_id;
    }

    @GET("/product/{bar_code}")
    Call<SellerProduct> getProduct(@Path("bar_code") String barcode);
}
