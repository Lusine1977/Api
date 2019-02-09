package com.example.lusine.api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {

    private TextView cityTextView;
    private TextView weatherTextView;
    private TextView dateTextView;
    private ImageView weatherImageView;

    private CompositeDisposable compositeDisposable;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        cityTextView = findViewById(R.id.cityTextView);
        weatherTextView= findViewById(R.id.weatherTextView);
        dateTextView= findViewById(R.id.dataTextView);
        weatherImageView= findViewById(R.id.weatherImageView);



        compositeDisposable = new CompositeDisposable();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://yesno.wtf/api/")
                .addConverterFactory(GsonConverterFactory.create())//java-i object sarqi
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}
