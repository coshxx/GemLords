package de.cosh.gemlords;

import android.app.PendingIntent;
import android.content.*;
import android.graphics.Color;
import android.os.*;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.android.vending.billing.IInAppBillingService;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AndroidApplication implements IActivityRequestHandler {
    private final int SHOW_PURCHASE_WINDOW = 2;
    private final int SHOW_ADS = 1;
    private final int HIDE_ADS = 0;
    protected AdView adView;
    protected View gameView;
    IInAppBillingService mService;
    private boolean fullVersionUser;

    private String PRODUCT_ID_NO_ADS = "no_ads";
    //private String PRODUCT_ID_NO_ADS = "android.test.purchased";

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_ADS: {
                    if (fullVersionUser)
                        break;
                    adView.setVisibility(View.VISIBLE);
                    break;
                }
                case HIDE_ADS: {
                    adView.setVisibility(View.GONE);
                    break;
                }
            }
        }
    };
    private boolean googleConnectionOrCacheEstablished;
    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            googleConnectionOrCacheEstablished = true;
            mService = IInAppBillingService.Stub.asInterface(service);
            try {
                Bundle ownedItems = mService.getPurchases(3, getPackageName(), "inapp", null);
                int response = ownedItems.getInt("RESPONSE_CODE");
                if (response == 0) {
                    ArrayList<String> ownedSkus =
                            ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                    ArrayList<String> purchToken =
                            ownedItems.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                    for (int i = 0; i < ownedSkus.size(); i++) {
                        String thisItem = ownedSkus.get(i);
                        String thisToken = purchToken.get(i);
                        JSONObject jo = new JSONObject(thisToken);
                        String token = jo.getString("purchaseToken");
                        if (thisItem.equals(PRODUCT_ID_NO_ADS)) {
                            //mService.consumePurchase(3, getPackageName(), token);
                            fullVersionUser = true;
                            showAds(false);
                        }
                    }
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        googleConnectionOrCacheEstablished = false;
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        gameView = initializeForView(new GemLord(this), true);
        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-7121879894953636/5591882705");
        adView.setAdSize(AdSize.BANNER);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("EFAC6CABE4081A83F75C76A7CDD574BA")
                .build();

        LinearLayout.LayoutParams adParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams viewParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

        layout.addView(adView, adParams);
        layout.addView(gameView, viewParams);
        setContentView(layout);

        adView.setVisibility(View.GONE);
        adView.setBackgroundColor(Color.TRANSPARENT);
        adView.loadAd(request);

        bindService(new Intent("com.android.vending.billing.InAppBillingService.BIND"),
                mServiceConn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void showAds(boolean show) {
        handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
    }

    @Override
    public void showRemoveAdsInAppPurchaseWindow() {
        handler.sendEmptyMessage(SHOW_PURCHASE_WINDOW);

        ArrayList<String> skuList = new ArrayList<String>();
        skuList.add(PRODUCT_ID_NO_ADS);
        Bundle querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);

        try {
            Bundle skuDetails = mService.getSkuDetails(3, getPackageName(), "inapp", querySkus);
            int response = skuDetails.getInt("RESPONSE_CODE");
            if (response == 0) {
                ArrayList<String> responseList
                        = skuDetails.getStringArrayList("DETAILS_LIST");

                for (String thisResponse : responseList) {
                    JSONObject object = new JSONObject(thisResponse);
                    String sku = object.getString("productId");
                    String price = object.getString("price");
                    Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(), sku, "inapp", "");
                    PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
                    if (pendingIntent == null)
                        throw new IntentSender.SendIntentException("pendingIntent is null");
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
    public boolean googleConnectionOrCacheEstablished() {
        return googleConnectionOrCacheEstablished;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(mServiceConn);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

            if (resultCode == RESULT_OK) {
                JSONObject jo = null;
                try {
                    jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
                    if (sku.equals(PRODUCT_ID_NO_ADS)) {
                        fullVersionUser = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}