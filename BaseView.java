package com.example.administrator.androidapp.page;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import junit.framework.Assert;

/**
 * Created by Administrator on 2015/7/24.
 */
public class BaseView extends View {

    public BaseView(Context context) {
        super(context);
    }

    /**
     * 安全地返回子元素，如果没有找到，抛出异常，终止程序
     * @param id
     * @return
     */
    public View findViewByIdSafe(int id) {
        View ret = this.findViewById(id);
        Assert.assertTrue(ret!=null);
        return ret;
    }

    public void setTextView(int[] ids, String[] values) {
        Assert.assertTrue(ids != null && values != null);
        Assert.assertTrue(ids.length == values.length);
        for(int i=0;i<ids.length;i++){
            TextView tv = (TextView)findViewById(ids[i]);
            Assert.assertTrue(values.toString(),tv!=null);
            tv.setText(values[i]);
        }
    }

    public void setTextViewVisible(int id, int value) {
        TextView tv = (TextView)findViewById(id);
        Assert.assertTrue(tv!=null);
        tv.setVisibility(value);
    }

    public void setImageViewVisible(int id, int value) {
        ImageView imageView = (ImageView)findViewById(id);
        Assert.assertTrue(imageView!=null);
        imageView.setVisibility(value);
    }

}
