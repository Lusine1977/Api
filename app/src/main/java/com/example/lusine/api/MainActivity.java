package com.example.lusine.api;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ImageView gifView;

    private TextView textView;
    private Button reloadBtn;
    private Retrofit retrofit;


    private CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        reloadBtn = findViewById(R.id.reloadBtn);
        gifView= findViewById(R.id.gifView);

        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("loading...");


        reloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YesNoService service = retrofit.create(YesNoService.class);
                pd.show();
                service.getAnswer()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<YesOrNO>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                compositeDisposable.add(d);

                            }

                            @Override
                            public void onSuccess(YesOrNO yesOrNO) {
                                textView.setText(yesOrNO.getAnswer());
                                pd.dismiss();
                                Glide.with(gifView)
                                        .asGif()
                                        .load(Uri.parse(yesOrNO.getImage()))
                                        .into(gifView);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(MainActivity.this, "mi ban sxal e ", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                        });
            }

        });
        compositeDisposable = new CompositeDisposable();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://yesno.wtf/api/")
                .addConverterFactory(GsonConverterFactory.create())//java-i object sarqi
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
findViewById(R.id.weatherBtn).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent= new Intent(MainActivity.this,WeatherActivity.class);
        startActivity(intent);
    }
});

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}
