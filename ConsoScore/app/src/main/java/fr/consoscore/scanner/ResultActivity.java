package fr.consoscore.scanner;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class ResultActivity extends AppCompatActivity {

    private AlertDialog generateReturnDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(message);
        builder.setNegativeButton("Retour", (dialog, item) -> {
            this.finish();
        });
        return builder.create();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String EAN = getIntent().getExtras().getString("EAN", "Error");

        ProductAPI.SellerProduct s = new ProductAPI.SellerProduct();
        s.maker_product = new ProductAPI.Product();
        s.maker_product.name = "Product Name";
        renderSellerProduct(s);
        /*
        Thread thread = new Thread(() -> {
            try {
                ProductAPI.SellerProduct sellerProduct = ProductRequesterUtils.requestProduct(EAN);
                renderSellerProduct(sellerProduct);
            } catch (IOException e) {
                runOnUiThread(() -> {
                    AlertDialog alert = generateReturnDialog(getString(R.string.serverConnectionErrorMessage));
                    alert.show();
                });
            } catch (ProductRequesterUtils.ProductNotFoundException e) {
                runOnUiThread(() -> {
                    AlertDialog alert = generateReturnDialog(getString(R.string.productNotFoundErrorMessage));
                    alert.show();
                });
            }
        });
        thread.start();
         */

    }

    private void renderSellerProduct(ProductAPI.SellerProduct sellerProduct){
        runOnUiThread(() -> {
            TextView nameView = findViewById(R.id.productName);
            nameView.setText(sellerProduct.maker_product.name);
        });
    }


}