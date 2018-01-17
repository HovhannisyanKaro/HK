package com.hk.get_methods_from_class;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hk.R;
import com.hk.main.utils.LogUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Main2Activity extends AppCompatActivity {

    private static final String LOG = "HK_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Simple simpleOld = new Simple("Karo", 25, "Hovhannisyan", "Lan", "Narek", true);
        d("INFO_____OLD", simpleOld.toString());
        Simple simpleNew = new Simple(null, null, null, null, "Vahe", false);

        try {
            Class c = Simple.class;
            Method[] m = c.getDeclaredMethods();
            for (int i = 0; i < m.length; i++) {
                String methodName =  m[i].getName();
                String methodNameWithoutGet;

                if (methodName.startsWith("get")){
                    methodNameWithoutGet = methodName.replace("get", "");
                }else{
                    methodNameWithoutGet = methodName.replace("is", "");
                }
                if (methodName.startsWith("get")  || methodName.startsWith("is")) {
                    Object o = m[i].invoke(simpleNew);
                    Class<?> returnedTpe = m[i].getReturnType();
                    if (o != null) {
                        Method method = simpleOld.getClass().getMethod("set" + methodNameWithoutGet, returnedTpe);
                        method.invoke(simpleOld, m[i].invoke(simpleNew));
                    }
                }
                if (i == (m.length - 1))
                d("INFO_UPDATED", simpleOld.toString());

            }
        } catch (Throwable e) {
            e(e.toString());
        }
    }

    private void d(String msg) {
        LogUtils.d(msg);
    }

    private void d(String TAG, String msg) {
        LogUtils.d(TAG, msg);
    }

    private void e(String msg) {
        LogUtils.e(msg);

    }
}
