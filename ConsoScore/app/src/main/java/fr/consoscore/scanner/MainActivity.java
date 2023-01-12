package fr.consoscore.scanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(v -> {
            IntentIntegrator intentIntegrator = new IntentIntegrator(this);
            intentIntegrator.setPrompt(getString(R.string.scanner_text));
            intentIntegrator.setOrientationLocked(false);
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.EAN_13);
            intentIntegrator.initiateScan();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() != null) {
                Intent myIntent = new Intent(this, ResultActivity.class);
                myIntent.putExtra("EAN", intentResult.getContents());
                startActivity(myIntent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}