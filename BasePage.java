package com.example.administrator.androidapp.page;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Current;
import com.example.administrator.androidapp.msg.DateFactory;
import com.example.administrator.androidapp.tool.PicUtil;
import com.example.administrator.androidapp.tool.Utils;

import junit.framework.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Stack;

/**
 * Created by dengaoshan on 2015/7/25
 * 基础页面类
 * 包含所有页面公共的方法
 * 常用的控件，功能，都简化为一个函数调用，相当方便省事
 * 通过断言来保证传入错误的ID直接抛出异常
 */
public abstract class BasePage extends ActionBarActivity {

    private static final int TAKE_PICTURE = 1;

    private static Stack<Class> historyPages = new Stack<Class>(){};

    /**
     * 比系统的那个更安全的找到子元素的办法
     * 如果没有id，直接抛出异常使得程序终止
     * @param id
     * @return
     */
    public View findViewByIdSafe(int id) {
        View ret = getWindow().findViewById(id);
        Assert.assertTrue(ret!=null);
        return ret;
    }


    //为parent的Spinner添加内容 list中存放内容
    public void createActivityTypeSpinner(int id, String[] datas) {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, datas);

        Spinner type = (Spinner) findViewByIdSafe(id);
        ArrayAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                arg0.setVisibility(View.VISIBLE);
            }
        });
    }

    //弹出提示窗口
    public void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    /***************       UI控件操作   ***************/
    public void setTextView(int id, String value) {
        TextView tv = (TextView)findViewById(id);
        Assert.assertTrue(tv != null);
        tv.setText(value);
    }

    public String getTextView(int id){
        TextView text =  (TextView)findViewById(id);
        Assert.assertTrue(text != null);
        return text.getText().toString();
    }

    public void setEditText(int id, String value) {
        EditText tv = (EditText)findViewById(id);
        Assert.assertTrue(tv != null);
        tv.setText(value);
    }

    public String getEditText(int id){
        EditText text =  (EditText)findViewById(id);
        Assert.assertTrue(text != null);
        return text.getText().toString();
    }

    public void setRadioButton(int id,boolean status) {
        RadioButton btn = (RadioButton)findViewById(id);
        Assert.assertTrue(btn!=null);
        btn.setChecked(status);
    }

    public boolean getRadioButton(int id) {
        RadioButton btn = (RadioButton)findViewById(id);
        Assert.assertTrue(btn != null);
        return btn.isChecked();
    }


    public long getSpinnerSelected(int id){
        Spinner spinner = (Spinner)findViewById(id);
        Assert.assertTrue(spinner != null);
        return spinner.getSelectedItemId();
    }

    public void setButtonVisible(int id,int viewStatus) {
        Button btn = (Button)findViewById(id);
        Assert.assertTrue(btn!=null);
        btn.setVisibility(viewStatus);
    }

    //切换页面
    public void transPage(Class after){
        historyPages.push(this.getClass());
        Intent intent = new Intent();
        intent.setClass(this,after);
        this.startActivity(intent);
        this.finish();
    }

    //返回上一个页面
    public void backPage(){
        Class after = historyPages.pop();
        Assert.assertTrue(after != null);
        Intent intent = new Intent();
        intent.setClass(this, after);
        startActivity(intent);
        finish();
    }


    /**
     * 返回上级函数
     * 使用方法： 在UI的XML布局中，设置OnClick属性为“back_Click”
     * @param v
     */
    public void back_Click(View v) {
        backPage();
    }

    //按后退键盘返回上级
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            backPage();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
