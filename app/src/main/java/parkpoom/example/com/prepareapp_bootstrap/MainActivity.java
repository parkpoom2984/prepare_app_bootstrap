package parkpoom.example.com.prepareapp_bootstrap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    AdView adView;
    TextView textView;

    private final static int MY_PERMISSIONS_READ_PHONE_STATE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adView = (AdView) findViewById(R.id.adView);
        textView = (TextView) findViewById(R.id.textView);
        MobileAds.initialize(this, getString(R.string.ads_app_id));
        initAd();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_READ_PHONE_STATE);
        } else {
            showPhoneNo();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showPhoneNo();
                }
            }
        }
    }


    public void initAd() {
        AdRequest.Builder adBuilder = new AdRequest.Builder();
        AdRequest adRequest = adBuilder.build();
        adView.loadAd(adRequest);
    }

    public void showPhoneNo() {
        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (tMgr != null) {
            @SuppressLint({"MissingPermission", "HardwareIds"}) String mPhoneNumber = tMgr.getLine1Number();
            textView.setText(mPhoneNumber);
        }
    }
}
