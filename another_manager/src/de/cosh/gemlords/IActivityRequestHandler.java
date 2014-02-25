package de.cosh.gemlords;

/**
 * Created by cosh on 25.02.14.
 */
public interface IActivityRequestHandler {
    public void showAds(boolean show);
    public void showInAppPurchases();

    public boolean isFullVersionUser();
}
