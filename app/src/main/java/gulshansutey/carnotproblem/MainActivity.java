package gulshansutey.carnotproblem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.android.volley.Request;
import com.android.volley.VolleyError;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import gulshansutey.carnotproblem.adapters.ItemInfoGridADP;
import gulshansutey.carnotproblem.models.DbModel;
import gulshansutey.carnotproblem.models.InfoModel;
import gulshansutey.carnotproblem.utils.MyApplication;
import gulshansutey.carnotproblem.utils.MyStringRequest;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<String> urls = new ArrayList<>();
    private List<InfoModel> infoModelList = new ArrayList<>();
    private ItemInfoGridADP itemInfoGridADP;
    private boolean isBtnEnable;
    private TextView tv_current_timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rv_items_grid = findViewById(R.id.rv_items_grid);
        rv_items_grid.setLayoutManager(new GridLayoutManager(MainActivity.this, 2, LinearLayoutManager.VERTICAL, false));
        urls.add("https://jsonplaceholder.typicode.com/comments");
        urls.add("https://jsonplaceholder.typicode.com/photos");
        urls.add("https://jsonplaceholder.typicode.com/todos");
        urls.add("https://jsonplaceholder.typicode.com/posts");

        //Adding 4 empty items for empty fields
        if (infoModelList.isEmpty() || infoModelList.size() < 4) {
            System.out.println("infoModelList.size() = " + infoModelList.size());
            for (int i = 0; i < 4; i++) {
                infoModelList.add(new InfoModel());
            }
        }
        itemInfoGridADP = new ItemInfoGridADP(MainActivity.this, infoModelList);
        rv_items_grid.setAdapter(itemInfoGridADP);
        tv_current_timestamp = findViewById(R.id.tv_current_timestamp);

        //Observable for a 5 second delay
        Observable.just(true).delay(5000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        isBtnEnable = true;
                        tv_current_timestamp.setText(String.valueOf(getTimeStamp()));

                           /*Calling all the urls simultaneously after a 5 second delay*/
                        for (String s : urls) {
                            counter(s).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
                        }
                        Toast.makeText(MainActivity.this, "Hi there", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     * Returns current timestamp
     */
    private long getTimeStamp() {
        return new Timestamp(System.currentTimeMillis()).getTime();
    }


    /**
     * Saving timestamps in database
     */
    public void SaveTimeStamps(String tag, long start, long end) {

        DbModel dbModel = new DbModel();
        dbModel.tag = tag;
        dbModel.startTimeStamp = start;
        dbModel.endTimeStamp = end;
        ActiveAndroid.beginTransaction();
        long saveStart = getTimeStamp();
        dbModel.saveStartTimeStamp = saveStart;
        dbModel.save();
        long saveEnd = getTimeStamp();
        dbModel.saveEndTimeStamp = saveEnd;
        dbModel.save();
        try {
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
        InfoModel infoModel = new InfoModel();
        infoModel.setEndMillis(String.valueOf(end));
        infoModel.setStartMillis(String.valueOf(start));
        infoModel.setSaveEndMillis(String.valueOf(saveEnd));
        infoModel.setSaveStartMillis(String.valueOf(saveStart));
        infoModel.setUrl(tag);
        populateItems(infoModel);

    }

    /**
     * Observable to perform volley request
     */
    private Observable<String> counter(final String url) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<String> e) throws Exception {
                final long startMillis = getTimeStamp();
                MyStringRequest stringRequest = new MyStringRequest(Request.Method.GET, url, new MyStringRequest.VolleyResponse() {
                    @Override
                    public void onResponse(String response, String tag) {
                        long endMillis = getTimeStamp();
                        SaveTimeStamps(tag, startMillis, endMillis);
                        e.onNext(tag);
                    }

                    @Override
                    public void onError(VolleyError error, String tag) {
                        System.out.println("error = " + error);
                    }
                });
                MyApplication.getInstance().addToRequestQueue(stringRequest.getStringRequest());
            }
        });

    }


    /**
     * Populating grid adapter
     */
    private void populateItems(InfoModel infoModel) {
        if (infoModel.getUrl().equals(urls.get(0))) {
            infoModelList.set(0, infoModel);
        } else if (infoModel.getUrl().equals(urls.get(1))) {
            infoModelList.set(1, infoModel);
        } else if (infoModel.getUrl().equals(urls.get(2))) {
            infoModelList.set(2, infoModel);
        } else if (infoModel.getUrl().equals(urls.get(3))) {
            infoModelList.set(3, infoModel);
        }
        itemInfoGridADP.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (isBtnEnable) {
            switch (view.getId()) {
                case R.id.btn_url_1:
                    counter(urls.get(0)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
                    break;
                case R.id.btn_url_2:
                    counter(urls.get(1)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
                    break;
                case R.id.btn_url_3:
                    counter(urls.get(2)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
                    break;
                case R.id.btn_url_4:
                    counter(urls.get(3)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
                    break;
            }
        }

    }
}
