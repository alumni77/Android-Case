package com.example.qqdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.qqdemo.bean.BeanData;
import com.example.qqdemo.bean.LocationData;
import com.google.gson.Gson;
import com.example.qqdemo.R;
import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Unit;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;

import com.qweather.sdk.view.QWeather;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView tv_weather, tv_location, tv_temperature, tv_humi;
    Button btn_research;
    public BeanData.NowEntity result;
    public String content;
    public String temp;
    public String humi, weather;
    public String location;
    public String location_content, province, city, CN_code, city2;
    private ArrayList<String> weather_info = new ArrayList<>();
    EditText ed_research;


    // *************** 异步消息传输机制 *********************//
    // 是在主线程中开启了handleMessage,故可以进行UI线程的修改
    public static final int UPDATE_TEXT = 1;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == UPDATE_TEXT) {

                ArrayList<String> weather_rev = (ArrayList<String>) (msg.obj);
                Log.d(TAG, "handleMessage: " + weather_rev.get(0));

                tv_location.setText(weather_rev.get(0));
                tv_temperature.setText(weather_rev.get(1));
                tv_humi.setText(weather_rev.get(2));
                tv_weather.setText(weather_rev.get(3));
                weather_rev.clear();

            }
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        TextView textView = findViewById(R.id.textdata);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        textView.setText("登录数据如下\n");
        textView.append("用户名: " + userName + "\n密码: " + password);

        HeConfig.init("HE2407121501281322", "65782e84d0bc4d6ea883dc74d5579765");
        HeConfig.switchToDevService();

        tv_weather = findViewById(R.id.weather);
        tv_location = findViewById(R.id.location);
        tv_temperature = findViewById(R.id.temperature);
        tv_humi = findViewById(R.id.humi);
        btn_research = findViewById(R.id.btn_search);
        ed_research = findViewById(R.id.research);

        btn_research.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = ed_research.getText().toString().trim();
                Log.d(TAG, "onClick: 成功输入" + location);
                getLocation(location);
            }
        });
    }


    // **************** 下面的网络请求是在子线程中进行操作的 ***********************//
    public void getWether(String location) {
        QWeather.getWeatherNow(MainActivity.this, location, Lang.ZH_HANS, Unit.METRIC,
                new QWeather.OnResultWeatherNowListener() {

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "getWeather onError: " + e);
                    }

                    @Override
                    public void onSuccess(WeatherNowBean weatherNowBean) {
                        Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(weatherNowBean));
                        //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                        if (Code.OK == weatherNowBean.getCode()) {
                            WeatherNowBean.NowBaseBean now = weatherNowBean.getNow();
                            content = JSON.toJSONString(weatherNowBean);
                            BeanData result_bean = JSONObject.parseObject(content, BeanData.class);
                            result = result_bean.getNow();
                            temp = result.getTemp();
                            humi = result.getHumidity();
                            weather = result.getText();
                            weather_info.add(temp);
                            weather_info.add(humi);
                            weather_info.add(weather);

                            // ***************
                            Message message = new Message();
                            message.what = UPDATE_TEXT;
                            message.obj = weather_info;
                            handler.sendMessage(message);

                        } else {
                            //在此查看返回数据失败的原因
                            Code code = weatherNowBean.getCode();
                            Log.i(TAG, "failed code: " + code);
                        }
                    }
                });
    }

    public void getLocation(String location) {
        QWeather.getGeoCityLookup(MainActivity.this, location, new QWeather.OnResultGeoListener() {
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "getWeather onError: " + e);
                Toast.makeText(MainActivity.this, "您查询的地区不存在", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(GeoBean geoBean) {
                Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(geoBean));
                location_content = JSON.toJSONString(geoBean);
                LocationData result_location = JSONObject.parseObject(location_content, LocationData.class);
                province = result_location.getLocationBean().get(0).getAdm1();
                city = result_location.getLocationBean().get(0).getAdm2();
                city2 = result_location.getLocationBean().get(0).getName();
                CN_code = result_location.getLocationBean().get(0).getId();
                Log.d(TAG, "onSuccess: " + CN_code);
                weather_info.clear();
                weather_info.add(province + city + city2);
                getWether("CN" + CN_code);
            }
        });
    }

}

