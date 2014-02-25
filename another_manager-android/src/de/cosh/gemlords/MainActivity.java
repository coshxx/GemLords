package de.cosh.gemlords;

import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.ads.*;

public class MainActivity extends AndroidApplication implements IActivityRequestHandler {
    protected AdView adView;

    private final int SHOW_ADS = 1;
    private final int HIDE_ADS = 0;

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
}