package chapter.android.aweme.ss.com.homework;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作业2：一个抖音笔试题：统计页面所有view的个数
 * Tips：ViewGroup有两个API
 * {@link android.view.ViewGroup #getChildAt(int) #getChildCount()}
 * 用一个TextView展示出来
 */
public class Exercises2 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relativelayout);
        getAllView(this);
    }

    private void getAllView(Activity act){
        TextView mTitle = findViewById(R.id.tv_center);
        String countStr = String.valueOf(getAllChildViews(act.getWindow().getDecorView()).size());
        mTitle.setText(countStr);
    }

    private List<View> getAllChildViews(View view) {
        List<View> allChildren = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewChild = vp.getChildAt(i);
                allChildren.add(viewChild);
                allChildren.addAll(getAllChildViews(viewChild));
            }
        }
        return allChildren;
    }
}
