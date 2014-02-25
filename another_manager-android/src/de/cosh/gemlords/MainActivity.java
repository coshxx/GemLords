package de.cosh.gemlords;

import android.app.PendingIntent;
import android.content.*;
import android.graphics.Color;
import android.os.*;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.android.vending.billing.IInAppBillingService;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.ads.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AndroidApplication implements IActivityRequestHandler {
    protected AdView adView;

    IInAppBillingService mService;

    private final int SHOW_PURCHASE_WINDOW = 2;
    private final int SHOW_ADS = 1;
    private final int HIDE_ADS = 0;

    private boolean fullVersionUser;


    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);

            try {
                Bundle ownedItems = mService.getPurchases(3, getPackageName(), "inapp", null);
                int response = ownedItems.getInt("RESPONSE_CODE");
                if( response == 0 ) {
                    ArrayList<String> ownedSkus =
                            ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                    for( String thisItem : ownedSkus ) {
                        if( thisItem.equals("full_version_no_ads")) {
                            fullVersionUser = true;
                        }
                        if( thisItem.equals("android.test.purchased")) {
                            playWelcomeBack();
                        }
                    }
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    private void playWelcomeBack() {
        Toast.makeText(this, "Welcome back!", Toast.LENGTH_LONG).show();
        fullVersionUser = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;

        initialize(new GemLord(), cfg);
        */

        RelativeLayout layout = new RelativeLayout(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        View gameView = initializeForView(new GemLord(this), true);
        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-7121879894953636/5591882705");
        adView.setAdSize(AdSize.BANNER);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("EFAC6CABE4081A83F75C76A7CDD574BA")
                .build();

        layout.addView(gameView);
        RelativeLayout.LayoutParams adParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);


        layout.addView(adView, adParams);
        setContentView(layout);

        adView.setBackgroundColor(Color.TRANSPARENT);
        adView.loadAd(request);

        bindService(new Intent("com.android.vending.billing.InAppBillingService.BIND"),
                mServiceConn, Context.BIND_AUTO_CREATE);


    }

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch( msg.what ) {
                case SHOW_ADS:
                {
                    adView.setVisibility(View.VISIBLE);
                    break;
                }
                case HIDE_ADS:
                {
                    adView.setVisibility(View.INVISIBLE);
                    break;
                }
            }
        }
    };

    @Override
    public void showAds(boolean show) {
        handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS );
    }

    @Override
    public void showInAppPurchases() {
        handler.sendEmptyMessage(SHOW_PURCHASE_WINDOW);

        ArrayList<String> skuList = new ArrayList<String>();
        skuList.add("full_version_no_ads");
        String fullPrice;
        Bundle querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);

        try {
            Bundle skuDetails = mService.getSkuDetails(3, getPackageName(), "inapp", querySkus);
            int response = skuDetails.getInt("RESPONSE_CODE");
            if( response == 0) {
                ArrayList<String> responseList
                        = skuDetails.getStringArrayList("DETAILS_LIST");

                for( String thisResponse : responseList) {
                    JSONObject object = new JSONObject(thisResponse);
                    String sku = object.getString("productId");
                    String price = object.getString("price");
                    if( sku.equals("full_version_no_ads")) fullPrice = price;

                    Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(), "android.test.purchased", "inapp", "");

                    PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");

                    startIntentSenderForResult(pendingIntent.getIntentSender(), 1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
                }
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean isFullVersionUser() {
        return fullVersionUser;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if( mService != null ) {
            unbindService(mServiceConn);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == 1001) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

            if( resultCode == RESULT_OK ) {
                JSONObject jo = null;
                try {
                    jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
                    System.out.println("Thanks for buying Gem Lords");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("canceled");
            }
        }
    }
}