package com.infy.esurio.outlet.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.infy.esurio.R;
import com.infy.esurio.middleware.DTO.OrdersDTO;
import com.infy.esurio.outlet.activities.main.MainActivity;
import com.infy.esurio.outlet.app.This;
import com.infy.esurio.outlet.app.services.OrdersService;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BarcodeActivity extends AppCompatActivity {

    private static final String TAG = BarcodeActivity.class.getSimpleName();
    private DecoratedBarcodeView barcodeView;
    private BeepManager beepManager;
    private String lastText;
    private String ip;
    private String employeeId;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(final BarcodeResult result) {
            Log.d(TAG, result.toString());
            ArrayList<String> list = new ArrayList<>();
            OrdersDTO dto = new OrdersDTO();
            dto.setIdentifier(result.toString());
            OrdersService.putServings(dto, list);
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(BarcodeActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.dialog_order_servings_list, null);
            alertDialog.setView(convertView);
            alertDialog.setTitle("Order Items");
            ListView lv = convertView.findViewById(R.id.lv);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(BarcodeActivity.this, android.R.layout.simple_list_item_activated_1, list);
            lv.setAdapter(adapter);
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Log.d(TAG, This.ORDERS.toString());
                    Log.d(TAG, result.toString());
                    for(OrdersDTO dto: This.ORDERS){
                        Log.d(TAG, dto.getIdentifier());
                        Log.d(TAG, result.toString());
                        if(result.toString().equals(dto.getIdentifier())){
                            Log.d(TAG, "EQUALS "+result.toString());
                            This.ORDERS.remove(dto);
                            break;
                        }
                    }
                    resume(barcodeView);
                }
            });
            alertDialog.show();
            pause(barcodeView);
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        barcodeView = (DecoratedBarcodeView) findViewById(R.id.barcode_scanner);
        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39);
        barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        barcodeView.initializeFromIntent(getIntent());
        barcodeView.decodeContinuous(callback);
        beepManager = new BeepManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        barcodeView.pause();
    }

    public void pause(View view) {
        barcodeView.pause();
    }

    public void resume(View view) {
        barcodeView.resume();
    }

    public void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

}
