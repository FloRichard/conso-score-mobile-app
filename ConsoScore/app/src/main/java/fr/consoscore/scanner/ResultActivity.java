package fr.consoscore.scanner;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class ResultActivity extends AppCompatActivity {

    private AlertDialog generateReturnDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(message);
        builder.setNegativeButton("Retour", (dialog, item) -> {
            this.finish();
        });
        builder.setOnDismissListener(dialog -> {
           this.finish();
        });
        return builder.create();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loading);
        String EAN = getIntent().getExtras().getString("EAN", "Error");

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


    }

    public int innerConsoScoreToNumberEquivalentPublicConsoScore(float consoScore){
        if(consoScore >= 10){
            return 5;
        }else if(consoScore <= 10 && consoScore > 7.5){
            return 4;
        }else if(consoScore <= 7.5 && consoScore > 5){
            return 3;
        }else if(consoScore <= 5 && consoScore > 2.5){
            return 2;
        }else{
            return 1;
        }
    }

    private int getConsoScoreDrawable(int i){
        switch (i){
            default:
            case 1:
                return R.drawable.consoscore_a;
            case 2:
                return R.drawable.consoscore_b;
            case 3:
                return R.drawable.consoscore_c;
            case 4:
                return R.drawable.consoscore_d;
            case 5:
                return R.drawable.consoscore_e;
        }
    }

    private int getConsoScoreBgDrawable(int i){
        switch (i){
            default:
            case 1:
                return R.drawable.layout_bg_a;
            case 2:
                return R.drawable.layout_bg_b;
            case 3:
                return R.drawable.layout_bg_c;
            case 4:
                return R.drawable.layout_bg_d;
            case 5:
                return R.drawable.layout_bg_e;
        }
    }

    private String getPriceTextFromConsoScore(int i){
        switch (i){
            default:
            case 1:
                return "Un produit d??fendant l'??cologie";
            case 2:
                return "";
            case 3:
                return "";
            case 4:
                return "";
            case 5:
                return "C'est le prix ?? payer pour un produit toxique pour l'environment";
        }
    }

    private void adaptDependingOnConsoScore(float consoScore){
        int consoScoreLetterEquivalent = innerConsoScoreToNumberEquivalentPublicConsoScore(consoScore);
        findViewById(R.id.mainLayout).setBackgroundResource(getConsoScoreBgDrawable(consoScoreLetterEquivalent));
        ((ImageView)findViewById(R.id.consoScoreImage)).setImageResource(getConsoScoreDrawable(consoScoreLetterEquivalent));
        ((TextView) findViewById(R.id.priceIndication)).setText(getPriceTextFromConsoScore(consoScoreLetterEquivalent));
    }


    private void renderSellerProduct(ProductAPI.SellerProduct sellerProduct){
        runOnUiThread(() -> {
            setContentView(R.layout.activity_result);
            findViewById(R.id.backButton).setOnClickListener(v -> {
                finish();
            });

            float tax = ((float)sellerProduct.tax / 100) * sellerProduct.price;
            float realPrice = tax + sellerProduct.price;

            adaptDependingOnConsoScore(sellerProduct.conso_score);

            TextView nameView = findViewById(R.id.productName);
            nameView.setText(sellerProduct.name);


            TextView categoryView = findViewById(R.id.category);
            categoryView.setText(sellerProduct.category);

            TextView whoseTaxesView = findViewById(R.id.whoseTaxes);
            whoseTaxesView.setText(getString(R.string.taxesDisplay, tax));

            TextView priceView = findViewById(R.id.price);
            priceView.setText(getString(R.string.priceDisplay, realPrice));

            TextView carboneFootprintView = findViewById(R.id.carbonFootprintQuantity);
            carboneFootprintView.setText(getString(R.string.carbonFootprintDisplay, sellerProduct.carbon_foot_print));
            TextView carboneFootprintUnitsView = findViewById(R.id.carbonFootprintUnits);
            carboneFootprintUnitsView.setText(getString(R.string.carbonFootprintUnitDisplay, sellerProduct.quantity_unity));


            TextView transportView = findViewById(R.id.transport);
            transportView.setText(getString(R.string.transportDisplay, sellerProduct.transport));
        });
    }


}