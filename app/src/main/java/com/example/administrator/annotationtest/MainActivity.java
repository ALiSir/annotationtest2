package com.example.administrator.annotationtest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.annotationtest.annotationmain.EMLayoutBiner;
import com.example.administrator.annotationtest.annotationmain.EMOnClickBiner;
import com.example.administrator.annotationtest.annotationmain.EMViewBiner;

import static com.example.administrator.annotationtest.annotationmain.EMAnnotationParser.METHOD_ONCLICK_NORMAL;

@EMLayoutBiner(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @EMViewBiner(R.id.button)
    private Button btn;
    @EMViewBiner(R.id.textView)
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView.setText("Annotation实例化该对象");
    }

    @EMOnClickBiner(value = {R.id.button},string = METHOD_ONCLICK_NORMAL)
    private void click(View v){
        switch (v.getId()){
            case R.id.button:
                textView.setText("Annotation点击事件");
                break;
            default:
                break;
        }
    }

}
