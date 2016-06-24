package rx.android.samples;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity {

    @Bind(R.id.refresh)
    protected SwipeRefreshLayout refreshLayout;

    @Bind(R.id.recyclerview)
    protected RecyclerView recyclerView;

    private RecycleAdapter adapter;

    private Subscription subscription;


    Observer<List<ZhuangbiImage>> observer = new Observer<List<ZhuangbiImage>>() {
        @Override
        public void onCompleted() {
        }
        @Override
        public void onError(Throwable e) {
            refreshLayout.setRefreshing(false);
            Toast.makeText(getBaseContext(), "加载失败//网络问题或者检查你的url地址是否正确", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(List<ZhuangbiImage> zhuangbiImages) {
            refreshLayout.setRefreshing(false);
            adapter.setImages(zhuangbiImages);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        adapter = new RecycleAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        refreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
      //  refreshLayout.setEnabled(false);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    adapter.setImages(null);
                    subscription = NetWork.getZservice()
                            .getSearch("装逼")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(observer);
            }
        });
    }

     class RecycleAdapter extends RecyclerView.Adapter{
        List<ZhuangbiImage> images;
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
            return new DebounceViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            DebounceViewHolder debounceViewHolder = (DebounceViewHolder) holder;
            ZhuangbiImage image = images.get(position);
            Glide.with(holder.itemView.getContext()).load(image.image_url).into(debounceViewHolder.imageIv);
            debounceViewHolder.descriptionTv.setText(image.description);
        }
        public void setImages(List<ZhuangbiImage> images) {
            this.images = images;
            notifyDataSetChanged();
        }
        @Override
        public int getItemCount() {
              return images == null ? 0 : images.size();
        }
         class DebounceViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.imageIv)
            ImageView imageIv;
            @Bind(R.id.descriptionTv)
            TextView descriptionTv;
            public DebounceViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

    }
    //网络权限检查
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
