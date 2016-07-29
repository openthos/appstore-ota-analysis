package Download_File;


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


    //  private	int file_id ;
//  private String file_name;
    private int download_flag = 0 ;

    private download_file Download_file ;

    public download_file_queue_item(download_file Download_file){
        //直接从用户获取
        this.Download_file = Download_file ;
    }
    public int get_filesize(){
        return (int) Download_file.get_filesize();
    }
    public void start(){
        download_flag = State.downloading ;
        Download_file.download_start();
    }
    public void stop(){
        Download_file.download_start();
    }
    public long get_downloaded_size(){
        return Download_file.get_downloaded_size();
    }
    public int get_state(){
        return download_flag;
    }
    public void set_state(int state){
        download_flag = state;
    }
    public String get_filename(){
        return   Download_file.get_filename();
    }
}










