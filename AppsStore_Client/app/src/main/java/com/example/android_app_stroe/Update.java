package com.example.android_app_stroe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.Download_File.download_file;
import com.example.Download_File.download_file_queue;
import com.example.JSON.Software;
import com.example.sql.Sql_Lite;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/**
 * Created by yoko on 2016/6/30.
 */
public class Update extends Activity {

    public void onCreate(Bundle o) {
        super.onCreate(o);

        setContentView(R.layout.update);
        Button check_update = (Button) findViewById(R.id.check_update);
        check_update.setOnClickListener(new View.OnClickListener() {
            String key = getIntent().getStringExtra("key");
            Object obj = getIntent().getSerializableExtra("obj");
            String name = getIntent().getStringExtra("soft_name");


            public String exec(String cmd) {
                try {
                    if (cmd != null)
                    {
                        Runtime rt = Runtime.getRuntime();
                        Process process = rt.exec("su");//Root权限
                        //Process process = rt.exec("sh");//模拟器测试权限
                        DataOutputStream dos = new DataOutputStream(process.getOutputStream());
                        dos.writeBytes(cmd + "\n");
                        dos.flush();
                        dos.writeBytes("exit\n");
                        dos.flush();
                        InputStream myin = process.getInputStream();
                        InputStreamReader is = new InputStreamReader(myin);
                       // char[] buffer = new char[OUTPUT_BUFFER_SIZE];
                        char[] buffer = new char[1024];
                        int bytes_read = is.read(buffer);
                        StringBuffer aOutputBuffer = new StringBuffer();
                        while (bytes_read > 0) {
                            //info.setText(aOutputBuffer.toString());
                            aOutputBuffer.append(buffer, 0, bytes_read);

                            bytes_read = is.read(buffer);
                        }
                        return aOutputBuffer.toString();
                    } else {
                        System.out.println("退出");
                        return "请输入正确的命令";
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                    return "操作异常";
                }
            }

            @Override
            //
            // 调用software的下载方法，去下载系统,7.5
            public void onClick(View v) {

                download_file_queue.Add_item(
                        new download_file((100), ("system")
                        , getApplicationContext(), "software"));

                File sdCardDir = null;
                File destDir = null;
               if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                   try {

                      sdCardDir  = new File(Environment.getExternalStorageDirectory() + "/System_OS/");
                       if(!sdCardDir.exists()){//判断文件是否真正存在,如果不存在,创建一个;
                           sdCardDir.mkdirs();
                       }
                       File file = new File(sdCardDir, "update");
                       if(!file.exists()){//判断文件是否真正存在,如果不存在,创建一个;
                           file.createNewFile();
                       }
                        //第二个参数意义是说是否以append方式添加内容
                        BufferedWriter bw = new BufferedWriter(new FileWriter(file,false));
                        String info[] = {"system.iso","\n","1","\n"};
                       for(int i =0; i<info.length;i++){
                           bw.write(info[i]);
                           bw.flush();
                       }
                         bw.close();
                        System.out.println("写入成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                Sql_Lite slqTest =new Sql_Lite(Update.this);
                Log.d("update",slqTest.close+"");
              /* if(slqTest.close == true) {
                   Log.d("update","dialog出现了吗");
                   AlertDialog.Builder builder = new AlertDialog.Builder(Update.this);
                   builder.setMessage("确认重启吗？");

                   builder.setTitle("提示");

                   builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                           try
                           {
                               Runtime.getRuntime().exec("su");
                               Runtime.getRuntime().exec("reboot");
                           }
                           catch (IOException e)
                           {
                               e.printStackTrace();
                           }
                           Log.d("REBOOT","重启了");
                           Update.this.finish();
                       }
                   });

                   builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                       }
                   });

                   builder.create().show();
               }*/
            }

        });

    }
}

