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
        String EAN2 = "1234";

        Thread thread = new Thread(() -> {
            try {
                ProductAPI.SellerProduct sellerProduct = ProductRequesterUtils.requestProduct(EAN2);
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


    }

    private void renderSellerProduct(ProductAPI.SellerProduct sellerProduct){
        runOnUiThread(() -> {
            TextView nameView = findViewById(R.id.productName);
            TextView consoScoreView = findViewById(R.id.consoScore);
            TextView priceView = findViewById(R.id.price);
            TextView whoseTaxesView = findViewById(R.id.whoseTaxes);
            TextView carboneFootprintView = findViewById(R.id.carboneFootprint);
            TextView categoryView = findViewById(R.id.category);
            TextView transportView = findViewById(R.id.transport);

            nameView.setText(sellerProduct.name);
            consoScoreView.setText(getString(R.string.consoScoreText, sellerProduct.conso_score));
            priceView.setText(getString(R.string.priceDisplay, sellerProduct.price));
            whoseTaxesView.setText(getString(R.string.taxesDisplay, sellerProduct.tax));
            carboneFootprintView.setText(getString(R.string.carbonFootprintDisplay, sellerProduct.carbon_foot_print, sellerProduct.quantity_unity));
            categoryView.setText(sellerProduct.category);
            transportView.setText(getString(R.string.transportDisplay, sellerProduct.transport));
        });
    }


}