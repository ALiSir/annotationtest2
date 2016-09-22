package com.example.administrator.annotationtest;

import android.app.Activity;
import android.os.Bundle;

import com.example.administrator.annotationtest.annotationmain.EMAnnotationParser;

/**
 * Created by Administrator on 2016/9/22.
 */

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EMAnnotationParser.initLayoutView(this);

    }
}
