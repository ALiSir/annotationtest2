package com.example.administrator.annotationtest.annotationmain;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/9/22.
 */

public class EMAnnotationParser {
    public static final String METHOD_ONCLICK_NORMAL = "NORMAL";//普通点击

    public static void initLayoutView(Activity activity) {
        if (null == activity) {
            return;
        }
        Class<Activity> activityClass = (Class<Activity>) activity.getClass();
        if (isEMLayoutBiner(activityClass, EMLayoutBiner.class)) {
            EMLayoutBiner layout = activityClass.getAnnotation(EMLayoutBiner.class);
            activity.setContentView(layout.value());
        }

        View view = activity.getWindow().getDecorView();
        initViewBiner(activityClass.getDeclaredFields() //加Declared表示可以访问任何变量，没加表示只能访问共有的变量
                , view, activity);
        initOnClickBiner(activityClass.getDeclaredMethods(), view, activity);

    }

    private static void initOnClickBiner(Method[] methods, View view, Object object) {
        for (Method method : methods) {
            if (isEMLayoutBiner(method, EMOnClickBiner.class)) {
                EMOnClickBiner em = method.getAnnotation(EMOnClickBiner.class);
                if (em.string().equals(METHOD_ONCLICK_NORMAL)) {
                    int[] ids = em.value();
                    MyOnClickListener click = new MyOnClickListener(method,object);
                    for (int id : ids) {
                        view.findViewById(id).setOnClickListener(click);
                    }
                }
            }
        }
    }

    private static class MyOnClickListener implements View.OnClickListener {
        private Method method;
        private Object object;

        public MyOnClickListener(Method method, Object object) {
            this.method = method;
            this.object = object;
        }

        @Override
        public void onClick(View v) {
            try {
                method.setAccessible(true);
                method.invoke(object,v );
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initViewBiner(Field[] fields, View view, Object object) {
        View view1;
        for (Field field : fields) {
            if (isEMLayoutBiner(field, EMViewBiner.class)) {
                try {
                    EMViewBiner em = field.getAnnotation(EMViewBiner.class);
                    view1 = view.findViewById(em.value());
                    field.setAccessible(true);
                    field.set(object, view1);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static boolean isEMLayoutBiner(AnnotatedElement element, Class<? extends java.lang.annotation.Annotation> EMClass) {
        return element.isAnnotationPresent(EMClass);
    }

}
