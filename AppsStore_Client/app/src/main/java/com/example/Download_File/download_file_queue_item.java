package com.example.Download_File;

import com.example.Tool.State;
/**
 * 队列中的元素对象
 * file_id 文件的id
 * file_name 文件名
 * download_flag 下载 标志  -1 等待下载  0 正在进行 1被暂停
 *
 *
 * @author Administrator
 *
 */
public class download_file_queue_item {




    private download_file Download_file ;

    public download_file_queue_item(download_file Download_file){
        //直接从用户获取
        this.Download_file = Download_file ;
    }



    public int get_filesize(){
        return (int) Download_file.get_filesize();
    }
    public void start(){
        Download_file.download_start();
    }
    public void stop(){
        set_flag(State.pause_download);
        Download_file.download_stop();


    }
    public long get_downloaded_size(){
        return Download_file.get_downloaded_size();
    }


    public String get_filename(){
        return   Download_file.get_filename();
    }
    public int get_fileid(){
        return Download_file.get_fileid();
    }
    public int get_flag(){
        return Download_file.get_flag();
    }
    public void set_flag(int flag ){
        Download_file.set_flag(flag);
    }
    public String get_classify (){
        return Download_file.get_classify();
    }
}











