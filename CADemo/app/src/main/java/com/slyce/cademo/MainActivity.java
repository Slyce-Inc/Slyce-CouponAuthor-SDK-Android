package com.slyce.cademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.slyce.couponauthorsdk.Bucket;
import com.slyce.couponauthorsdk.Coupon;
import com.slyce.couponauthorsdk.CouponAuthorSDK;
import com.slyce.couponauthorsdk.CouponAuthorSDKListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    private CouponAuthorSDK sdk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCouponSDK();
        testCoupon();
    }

    private void initCouponSDK() {
        sdk = CouponAuthorSDK.getInstance(new CouponAuthorSDKListener() {
            @Override
            public void onBucketReceived(Bucket bucket) {
                Log.d(TAG, "Bucket with id " + bucket.getBucketId() + " received");
            }

            @Override
            public void onCouponReceived(Coupon coupon) {
                Log.d(TAG, "Coupon with id " + coupon.getCouponId() + " received");

                if (coupon.getCouponId() == 2504) {
                    coupon.trackCouponImpression();
                }
            }

            @Override
            public void onEmailCoupondsReceived(List<Coupon> coupons) {
                Log.d(TAG, "Coupons list received. Count: " + coupons.size());
            }

            @Override
            public void onBarcodeCouponReceived(Coupon coupon) {
                Log.d(TAG, "Barcode coupon with id " + coupon.getCouponId() + " received");
            }

            @Override
            public void onFail(String errorMessage) {
                Log.e(TAG, "Coupon SDK failed: " + errorMessage);
            }
        });

        sdk.init("aab3395f0e711b43a9603becd0836bf0"); // Test api key
    }

    private void testCoupon() {
        sdk.getBucketWithId(279);
        sdk.getCouponWithId(2504);
        sdk.getCouponsWithEmail("hadimrih@gmail.com");
        sdk.getCouponWithBarcodeValue("1110002073014", null);
        sdk.getCouponWithBarcodeValue("1110002073014", "hadimrih@gmail.com");
    }
}
