package com.example.nuonuo.boilandroidsdk.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nuonuo.boilandroidsdk.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 参考：
 * AsyncTask原理及不足：https://blog.csdn.net/google_huchun/article/details/65630850
 * Android中AsyncTask使用详解：https://blog.csdn.net/iispring/article/details/50639090
 * 取消任务：https://www.cnblogs.com/hxb2016/p/6143648.html
 */
public class AsyncTaskActivity extends AppCompatActivity {
    private static       int ID         = 0;
    private static final int TASK_COUNT = 9;
    private static ExecutorService SINGLE_TASK_EXECUTOR;
    private static ExecutorService LIMITED_TASK_EXECUTOR;
    private static ExecutorService FULL_TASK_EXECUTOR;


    //======================================自定义线程池======================================


    static {
        SINGLE_TASK_EXECUTOR = (ExecutorService) Executors.newSingleThreadExecutor();
        LIMITED_TASK_EXECUTOR = (ExecutorService) Executors.newFixedThreadPool(7);
        FULL_TASK_EXECUTOR = (ExecutorService) Executors.newCachedThreadPool();
    }

    ;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_async_task);
        String title = "AsyncTask of API " + Build.VERSION.SDK_INT;
        setTitle(title);
        final ListView taskList = (ListView) findViewById(R.id.task_list);
        taskList.setAdapter(new AsyncTaskAdapter(getApplication(), TASK_COUNT));
    }

    @Override
    protected void onPause() {
        //如果异步任务不为空 并且状态是 运行时  ，就把他取消这个加载任务
//        if(task !=null && task.getStatus() == AsyncTask.Status.RUNNING){
//            task.cancel(true);
//
//        }
        super.onPause();
    }

    private class AsyncTaskAdapter extends BaseAdapter {
        private Context        mContext;
        private LayoutInflater layoutInflater;
        private int            mTaskCount;
        List<SimpleAsyncTask> mTaskList;

        public AsyncTaskAdapter(Context context, int taskCount) {
            mContext = context;
            layoutInflater = LayoutInflater.from(mContext);
            mTaskCount = taskCount;
            mTaskList = new ArrayList<SimpleAsyncTask>(taskCount);
        }

        @Override
        public int getCount() {
            return mTaskCount;
        }

        @Override
        public Object getItem(int position) {
            return mTaskList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.asynctask_demo_item, null);
                SimpleAsyncTask task = new SimpleAsyncTask((TaskItem) convertView);
                /*
                 * It only supports five tasks at most. More tasks will be scheduled only after
                 * first five finish. In all, the pool size of AsyncTask is 5, at any time it only
                 * has 5 threads running.
                 */
//                task.execute();
                // use AsyncTask#SERIAL_EXECUTOR is the same to #execute();
//                task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                // use AsyncTask#THREAD_POOL_EXECUTOR is the same to older version #execute() (less than API 11)
                // but different from newer version of #execute()
//                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                // one by one, same to newer version of #execute()
//                task.executeOnExecutor(SINGLE_TASK_EXECUTOR);
                // execute tasks at some limit which can be customized
//                task.executeOnExecutor(LIMITED_TASK_EXECUTOR);
                // no limit to thread pool size, all tasks run simultaneously
                task.executeOnExecutor(FULL_TASK_EXECUTOR);


                mTaskList.add(task);
            }
            return convertView;
        }
    }

//    private class Hello extends AsyncTask<String,Integer,Boolean>{
//
//        @Override
//        protected Boolean doInBackground(String... strings) {
//            return null;
//        }
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//        }
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//        }
//        @Override
//        protected void onCancelled(Boolean aBoolean) {
//            super.onCancelled(aBoolean);
//        }
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
//    }

    private class SimpleAsyncTask extends AsyncTask<Void, Integer, Void> {
        //泛型参数给下面三个重要回调
        private TaskItem mTaskItem;
        private String   mName;

        public SimpleAsyncTask(TaskItem item) {
            mTaskItem = item;
            mName = "Task #" + String.valueOf(++ID);
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (isCancelled()) {
                return null;
            }
            //子线程中运行
            int prog = 1;
            while (prog < 101) {
                SystemClock.sleep(100);
                //手动更新进度
                publishProgress(prog);
                prog++;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //执行完毕回调(如果是取消不会回调该方法，会回调onCancelled方法)，在主线程中

        }

        @Override
        protected void onPreExecute() {
            //doInBackground前回调
            mTaskItem.setTitle(mName);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if(isCancelled()){
                return;
            }
            mTaskItem.setProgress(values[0]);
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();

        }
    }
}

class TaskItem extends LinearLayout {
    private TextView    mTitle;
    private ProgressBar mProgress;

    public TaskItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TaskItem(Context context) {
        super(context);
    }

    public void setTitle(String title) {
        if (mTitle == null) {
            mTitle = (TextView) findViewById(R.id.task_name);
        }
        mTitle.setText(title);
    }

    public void setProgress(int prog) {
        if (mProgress == null) {
            mProgress = (ProgressBar) findViewById(R.id.task_progress);
        }
        mProgress.setProgress(prog);
    }
}