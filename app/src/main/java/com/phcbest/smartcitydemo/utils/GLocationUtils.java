package com.phcbest.smartcitydemo.utils;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.phcbest.smartcitydemo.base.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 经纬度获取
 *
 * @author peng
 * 创建日期：2020/9/27 14
 * 描述：
 */
public class GLocationUtils {


    private static String locationProvider;

    public interface CallBackLocation {
        /**
         * coolback
         */
        void callBack(Location location);
    }

    public static void getLatAndLog(final CallBackLocation callBackLocation) {
//       不支持网络定位，需要在空旷的地方定位
        final LocationManager locationManager = (LocationManager) MyApplication.getContext()
                .getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        //判断位置类型是什么
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(MyApplication.getContext(), "没有可用的位置提供器",
                    Toast.LENGTH_SHORT).show();
        }
        //获取位置
        if (ActivityCompat.checkSelfPermission(
                MyApplication.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MyApplication.getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MyApplication.getContext(), "您缺少权限",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        locationManager.requestLocationUpdates(locationProvider,
                3000,
                0,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        //解析地理位置
                        //拿到了第一次位置后就直接关掉
                        locationManager.removeUpdates(this);
                        Log.i("location", "backLocation: " + location.toString());
                        //回调返回location对象
                        callBackLocation.callBack(location);
                    }


                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });


    }

    //获取地址信息:城市、街道等信息
    public static List<Address> getAddress(Location location) {

        try {
            if (location != null) {
                Geocoder gc = new Geocoder(MyApplication.getContext(), Locale.getDefault());
                List<Address> result = gc.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 5);
                Toast.makeText(MyApplication.getContext(),
                        "获取地址信息：" + result.toString(), Toast.LENGTH_LONG).show();
                Log.v("TAG", "获取地址信息：" + result.toString());
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


}
