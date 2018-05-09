package com.example.nuonuo.boilandroidsdk.activity;

import android.content.Context;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nuonuo.boilandroidsdk.MainActivity;
import com.example.nuonuo.boilandroidsdk.R;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 参考：
 * handler基本使用：http://www.jb51.net/article/43360.htm
 * handler线程间通信：https://blog.csdn.net/shaoenxiao/article/details/54561753?utm_source=itdadao&utm_medium=referral
 * Handler消息接收拦截：https://blog.csdn.net/dingjikerbo/article/details/50673550
 */
public class HanderTestActivity extends AppCompatActivity {
    public TextView tv_num;
    public              int i             = 0;
    public static final int UPDATE_NUMBER = 10000;
    private Context mContext;

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            //msg.what有默认值（不传是0）
            if (msg.what == UPDATE_NUMBER) {
                int i = (Integer) msg.obj;
                tv_num.setText(i + "");
            }

            Log.i("HanderTestActivity", "msg.arg1:" + msg.arg1);

            Bundle b           = msg.getData();
            String whether     = b.getString("whether");
            int    temperature = b.getInt("temperature");
            Log.i("HanderTestActivity", "whether= " + whether + " ,temperature= " + temperature);

        }
        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_hander_test);
        tv_num = findViewById(R.id.tv_number);
    }


    //======================================基本使用======================================
    public void add(View view) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                //1.创建Message方式1
                Message msg = new Message();
                //2.传递数据方式1
                msg.what = UPDATE_NUMBER;
                msg.obj = 10;
                //3.发送数据1
                handler.sendMessage(msg);

                //4.创建Message方式2
                Message msg2 = handler.obtainMessage();
                //5.传递数据方式2
                msg2.arg1 = 100;
                //6.传递数据方式3
                Bundle b = new Bundle();
                b.putString("whether", "晴天");
                b.putInt("temperature", 34);
                msg2.setData(b);
                //7.发送数据2-将msg发送到自己的handler中，这里指的是handler,调用该handler的HandleMessage方法来处理该mug
                msg2.sendToTarget();
            }
        }.start();
    }

    //======================================异步延迟循环发送&取消======================================
    public void postDelayed(View view) {
        //将runable接口立刻送到主线程队列中
        handler.post(update_thread);
    }
    public void canselPost(View view) {
        //将接口从主线程队列中移除
        handler.removeCallbacks(update_thread);
    }

    Runnable update_thread = new Runnable() {
        public void run() {
            Log.d("HanderTestActivity", "hello");
            //延时1s后又将线程加入到线程队列中
            handler.postDelayed(update_thread, 1000);

        }
    };
    //======================================主线程与子线程通信======================================

    public void looperTest() {
        MyThread thread = new MyThread();
        thread.start();//千万别忘记开启这个线程
        //下面是主线程发送消息
        Handler mHandler = new Handler(thread.looper) {
            public void handleMessage(android.os.Message msg) {
                Log.d("当前子线程是----->", Thread.currentThread() + "");
            }
            ;
        };
        mHandler.sendEmptyMessage(1);
    }
    //创建子线程
    class MyThread extends Thread {
        private Looper looper;//取出该子线程的Looper
        public void run() {
            Looper.prepare();//创建该子线程的Looper
            looper = Looper.myLooper();//取出该子线程的Looper
            Looper.loop();//只要调用了该方法才能不断循环取出消息
        }
    }

    //======================================HandlerThread的使用======================================
    /**
     *
     */
    public void handerThread(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HandlerThread handler_thread = new HandlerThread("handler_thread");
                handler_thread.start();
                //MyHandler类是自己继承的一个类，这里采用hand_thread的Looper来初始化它
                MyHandler my_handler = new MyHandler(handler_thread.getLooper());
                //获得一个消息msg
                Message msg = my_handler.obtainMessage();

                //采用Bundle保存数据，Bundle中存放的是键值对的map，只是它的键值类型和数据类型比较固定而已
                Bundle b = new Bundle();
                b.putString("whether", "晴天");
                b.putInt("temperature", 34);
                msg.setData(b);
                //将msg发送到自己的handler中，这里指的是my_handler,调用该handler的HandleMessage方法来处理该mug
                msg.sendToTarget();
            }
        }).start();


    }

    class MyHandler extends Handler {
        //空的构造函数
        public MyHandler() {
        }
        //以Looper类型参数传递的函数，Looper为消息泵，不断循环的从消息队列中得到消息并处理，因此
        //每个消息队列都有一个Looper，因为Looper是已经封装好了的消息队列和消息循环的类
        public MyHandler(Looper looper) {
            //调用父类的构造函数
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            System.out.println("Handler_ID---->" + Thread.currentThread().getId());
            System.out.println("Handler_Name---->" + Thread.currentThread().getId());
            //将消息中的bundle数据取出来
            Bundle b           = msg.getData();
            String whether     = b.getString("whether");
            int    temperature = b.getInt("temperature");
            Log.i("HanderTestActivity", "whether= " + whether + " ,temperature= " + temperature);
        }

    }


    //======================================子线程与子线程间通信======================================


    //======================================作用示例======================================
    /**
     * 主要是将行为运行放到主线程(view会检查当前线程)，变量是线程间共享且不检查线程，即传数据是次要
     */

    public void affect(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                i = 88;
                test2();

            }
        }).start();
    }

    public void affectVar(View view) {
        test();
    }

    public void test2() {
        Log.i("HanderTestActivity", "in hello");
        Log.i("HanderTestActivity", "handler_id---->" + Thread.currentThread().getId());
        Log.i("HanderTestActivity", "handler_name---->" + Thread.currentThread().getName());
    }

    public void test() {
        Log.d("HanderTestActivity", "i:" + i);
        Log.i("HanderTestActivity", "handler_id---->" + Thread.currentThread().getId());
        Log.i("HanderTestActivity", "handler_name---->" + Thread.currentThread().getName());
    }


    //======================================Handler消息接收拦截======================================


    //======================================view的post======================================


    //======================================Activity的runOnUiThread======================================


    public void runOnUiThread() {
        //创建一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //延迟两秒
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "haha", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).start();
    }


    //======================================handler与evenbus和rxbus的对比======================================
    /**
     * https://www.cnblogs.com/whoislcj/p/5590615.html
     */

}

