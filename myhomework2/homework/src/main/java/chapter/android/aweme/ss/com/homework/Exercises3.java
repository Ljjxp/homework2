package chapter.android.aweme.ss.com.homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.InputStream;
import java.util.List;

import chapter.android.aweme.ss.com.homework.model.Message;
import chapter.android.aweme.ss.com.homework.model.PullParser;

import static android.graphics.Color.rgb;

/**
 * 大作业:实现一个抖音消息页面,所需资源已放在res下面
 */


public class Exercises3 extends AppCompatActivity implements myAdapter.ListItemClickListener {

    private static final int NUM_LIST_ITEMS = 100;

    private RecyclerView myListView;
    private myAdapter mAdapter;
    private RelativeLayout mTitle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        myListView = findViewById(R.id.rv_list);
        mTitle = findViewById(R.id.header);
        mTitle.setBackgroundColor(rgb(22,24,35));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myListView.setLayoutManager(layoutManager);

        myListView.setHasFixedSize(true);

        try {
            InputStream assetInput = getAssets().open("data.xml");
            List<Message> messages = PullParser.pull2xml(assetInput);
            for (Message message : messages) {
                mAdapter = new myAdapter(NUM_LIST_ITEMS, this, messages);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        myListView.setAdapter(mAdapter);
        myListView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            // 最后一个完全可见项的位置
            private int lastCompletelyVisibleItemPosition;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (visibleItemCount > 0 && lastCompletelyVisibleItemPosition >= totalItemCount - 1) {
                        Toast.makeText(Exercises3.this, "已滑动到底部!,触发loadMore", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                }
                Log.d("me", "onScrolled: lastVisiblePosition=" + lastCompletelyVisibleItemPosition);
            }
        });
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        startActivity(new Intent(this, UserAc.class));
    }
}
