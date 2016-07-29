package com.example.android_app_stroe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Download_File.download_file_queue;
import com.example.Download_File.download_file_queue_item;
import com.example.Download_File.downloaded_array;
import com.example.Tool.Constant;
import com.example.Tool.State;
import com.example.Tool.open_file;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoading extends Activity {



   /* String url[] =  new String[20];
    int i = 0;*/
    String url;

    double tmp;

    ListView downloading_task, download_his;
    downloading_baseAdapter downloading_BaseAdapter;
    downloaded_baseAdapter Downloaded_baseAdapter;
    private TextView tv_download_his, tv_clean_all;
    long before_downloaded = 0;
    int temp_count = 0;
    ImageView search;
    change_adpter Chage_adapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle o) {

        super.onCreate(o);
        setContentView(R.layout.downloading);
        downloading_task = (ListView) findViewById(R.id.downloading_lv);
        download_his = (ListView) findViewById(R.id.downloaded_lv_his);
        tv_download_his = (TextView) findViewById(R.id.download_his_);
        tv_clean_all = (TextView) findViewById(R.id.clean_all);
        search = (ImageView) findViewById(R.id.daohang_search);
        search.setOnClickListener(new click());
        downloading_BaseAdapter = new downloading_baseAdapter(this);
        downloading_task.setAdapter(downloading_BaseAdapter);

        Downloaded_baseAdapter = new downloaded_baseAdapter(this);


        ListView_height.setListViewHeightBasedOnChildren(download_his);
        ListView_height.setListViewHeightBasedOnChildren(downloading_task);
        Chage_adapter = new change_adpter(handler);
        Chage_adapter.start();


        tv_clean_all.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                downloaded_array.delete_all();
            }

        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DownLoading Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.android_app_stroe/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DownLoading Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.android_app_stroe/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    class click implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(DownLoading.this, Search.class);
            startActivity(intent);
        }

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            downloaded_array.init_array(getApplicationContext());
            downloading_BaseAdapter.notifyDataSetChanged();
            ListView_height.setListViewHeightBasedOnChildren(downloading_task);
            Downloaded_baseAdapter.notifyDataSetChanged();
            download_his.setAdapter(Downloaded_baseAdapter);
            ListView_height.setListViewHeightBasedOnChildren(download_his);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        Chage_adapter.stop_();
    }

    class downloading_baseAdapter extends BaseAdapter {
        private LayoutInflater minflater;
        String sudu_str;

        public downloading_baseAdapter(Context context) {
            minflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return download_file_queue.get_ArrayList().size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            // TODO Auto-generated method stub
            viewHolder_download view_downloaded;
            if (arg1 == null) {
                arg1 = minflater.inflate(R.layout.download_item, null);
                view_downloaded = new viewHolder_download();
                view_downloaded.soft_down_name = (TextView) arg1.findViewById(R.id.soft_down_name);
                view_downloaded.soft_down_daxiao = (TextView) arg1.findViewById(R.id.soft_down_daxiao);
                view_downloaded.run_sudu = (TextView) arg1.findViewById(R.id.run_sudu);
                view_downloaded.jindu = (TextView) arg1.findViewById(R.id.jindu);//7.19
                view_downloaded.soft_down_img = (ImageView) arg1.findViewById(R.id.soft_down_img);
                view_downloaded.soft_down_operate = (TextView) arg1.findViewById(R.id.soft_down_operate);
                view_downloaded.pb_down = (ProgressBar) arg1.findViewById(R.id.pb_downloading);
                arg1.setTag(view_downloaded);
            }
            view_downloaded = (viewHolder_download) arg1.getTag();

            download_file_queue_item temp = download_file_queue.get_ArrayList()
                    .get(arg0);

             long temp_file_size = temp.get_filesize();
             long downloaded_size = temp.get_downloaded_size();



            String classify = temp.get_classify();
            int id = temp.get_fileid();

                if (classify.equals("Software")) {
                    url = Constant.urlpath + "Software/" +
                            id + "/touxiang.jpg";

                } else if (classify.equals("auther_software") || classify.equals("software")) {
                    url = Constant.urlpath + "Software/" +
                            id + "/touxiang.jpg";
                } else if (classify.equals("auther_game") || classify.equals("game")) {
                    url = Constant.urlpath + "Game/" +
                            id + "/touxiang.jpg";
                }

                Bitmap bitmap = getHttpBitmap(url);

                view_downloaded.soft_down_img.setImageBitmap(bitmap);


            long for_sudu = 0;
            if (temp.get_flag() == State.downloading) {
                view_downloaded.soft_down_operate.setTextSize(20);
                view_downloaded.soft_down_operate.setText("暂停");
                for_sudu = downloaded_size - before_downloaded;
                before_downloaded = downloaded_size;
                sudu_str = "下载中";
            } else if (temp.get_flag() == State.pause_download) {
                view_downloaded.soft_down_operate.setTextSize(20);
                view_downloaded.soft_down_operate.setText("下载");
                sudu_str = "已暂停ͣ";
            } else if (temp.get_flag() == State.wait_for_download) {
                sudu_str = "等待中";
                view_downloaded.soft_down_operate.setTextSize(17);
                view_downloaded.soft_down_operate.setText("等待中");
            }


           double pb = ((double) downloaded_size / (double) temp_file_size) * 100;
            view_downloaded.pb_down.setProgress((int) pb);


            click_continue temp_click = new click_continue(view_downloaded, temp);
            view_downloaded.soft_down_operate.setOnClickListener(temp_click);
            view_downloaded.soft_down_name.setText(temp.get_filename() + "");
            view_downloaded.soft_down_daxiao.setText(temp.get_filesize() / (1024 * 1024) + "M");
            view_downloaded.run_sudu.setText(sudu_str);
            view_downloaded.jindu.setText((int)pb+"%"); //7.19
            return arg1;
        }

    }






    public Bitmap getHttpBitmap(String url) {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;

    }







    class click_continue implements OnClickListener {

        viewHolder_download view;
        download_file_queue_item item;

        public click_continue(viewHolder_download view, download_file_queue_item item) {
            this.item = item;
            this.view = view;
        }

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            if (arg0 == view.soft_down_operate) {

                if (view.soft_down_operate.getText().toString().equals("下载")) {
                    item.set_flag(State.wait_for_download);
                    view.soft_down_operate.setText("暂停");
                } else if (view.soft_down_operate.getText().toString().equals("暂停ͣ")) {

                    if (item.get_flag() == 0) {
                        view.soft_down_operate.setText("下载");
                        item.stop();
                    }

                }
            }
        }

    }


    //*********************************************************************************************************

    class downloaded_baseAdapter extends BaseAdapter {
        private LayoutInflater minflater;

        public downloaded_baseAdapter(Context context) {
            minflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            int count = downloaded_array.get_downloaded().size();
            tv_download_his.setText("下载历史(" + count + ")");
            return count;
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int arg0, View arg1, ViewGroup arg2) {
            // TODO Auto-generated method stub
            viewHolder_download view_downloaded;
            view_downloaded = new viewHolder_download();
            if (arg1 == null) {
                arg1 = minflater.inflate(R.layout.download_item, null);
                view_downloaded = new viewHolder_download();
                view_downloaded.soft_down_name = (TextView) arg1.findViewById(R.id.soft_down_name);
                view_downloaded.soft_down_daxiao = (TextView) arg1.findViewById(R.id.soft_down_daxiao);
                view_downloaded.run_sudu = (TextView) arg1.findViewById(R.id.run_sudu);
                view_downloaded.jindu = (TextView) arg1.findViewById(R.id.jindu);//7.19


                view_downloaded.soft_down_img = (ImageView) arg1.findViewById(R.id.soft_down_img);


                view_downloaded.soft_down_operate = (TextView) arg1.findViewById(R.id.soft_down_operate);
                view_downloaded.pb_down = (ProgressBar) arg1.findViewById(R.id.pb_downloading);
                arg1.setTag(view_downloaded);
            }



         /*   view_downloaded = (viewHolder_download) arg1.getTag();
            download_file_queue_item temp = download_file_queue.get_ArrayList()
                    .get(arg0);

            String classify = temp.get_classify();
            int id = temp.get_fileid();
            if (classify.equals("Software")) {
                url = Constant.urlpath + "Software/" +
                        id + "/touxiang.jpg";

            } else if (classify.equals("auther_software") || classify.equals("software")) {
                url = Constant.urlpath + "Software/" +
                        id + "/touxiang.jpg";
            } else if (classify.equals("auther_game") || classify.equals("game")) {
                url = Constant.urlpath + "Game/" +
                        id + "/touxiang.jpg";
            }*/

/*
            Bitmap bitmap = getHttpBitmap(url[i]);
            i++;
            view_downloaded.soft_down_img.setImageBitmap(bitmap);*/


            view_downloaded = (viewHolder_download) arg1.getTag();
            view_downloaded.soft_down_name.setText(downloaded_array.get_downloaded().get(arg0)[2]);
            Float size = Float.parseFloat(downloaded_array.get_downloaded().get(arg0)[3]) / (1024 * 1024);
            size = ((float) Math.round((float) (size * 100)) / 100);
            view_downloaded.soft_down_daxiao.setText(size + "M");
            view_downloaded.soft_down_operate.setText("安装");
            view_downloaded.pb_down.setVisibility(View.GONE);
            view_downloaded.run_sudu.setVisibility(View.GONE);
            view_downloaded.jindu.setVisibility(View.GONE);//7.19

            view_downloaded.soft_down_operate.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    File sdCardDir = null;
                    File destDir = null;
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        sdCardDir = Environment.getExternalStorageDirectory();
                        destDir = new File(sdCardDir + "/app_store/" + downloaded_array.get_downloaded().get(arg0)[2]
                                + downloaded_array.get_downloaded().get(arg0)[0]);
                        System.out.println(destDir + "我是路径");
                        Intent intent = open_file.openFile(destDir.toString());
                        startActivity(intent);
                    } else {
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), "sdcard 呢?", Toast.LENGTH_LONG).show();
                        Looper.loop();
                        return;
                    }
                }

            });
            return arg1;






        }

    }


    class viewHolder_download {
        TextView soft_down_name, soft_down_daxiao, run_sudu, soft_down_operate,jindu;
        ImageView soft_down_img;
        ProgressBar pb_down;
    }

    class change_adpter extends Thread {
        boolean flag = true;
        Handler handler;

        public change_adpter(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            while (flag) {
                Message msg = Message.obtain();
                handler.sendMessage(msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        public void stop_() {
            flag = false;
        }
    }
}













