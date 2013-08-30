package com.waterfun.beautygril;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.waterfun.ZHFFightLandlord.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 13-8-17.
 */
public class Update extends Activity
{
    private static boolean sdcardAvailable;
    private static boolean sdcardAvailabilityDetected;
    private static ProgressDialog progressDialog;
    int totalSize = 0;
    File downloadFile=null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatedownload);
        progressDialog = new ProgressDialog(Update.this);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        Thread thread = new Thread()
        {
            public void run()
            {
                download();
            }
        };
        thread.start();
    }

    public void download()
    {
        FileOutputStream fos = null;
        try
        {
            // URL url = new URL("http://112.124.13.160:2013/123.apk");
            String apkurl=getIntent().getStringExtra("apkurl");
            URL url = new URL(apkurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // conn.setConnectTimeout(3000);
            InputStream iStream = conn.getInputStream();
            totalSize = conn.getContentLength();
            int downloadedSize = 0;

            if (totalSize < 1 || iStream == null)
            {
                Message m = new Message();
                m.what = -1;// error
                handler.sendMessage(m);
            }
            progressDialog.setMax(totalSize);
            System.err.println("totalsize" + totalSize);
            File path = Environment.getExternalStorageDirectory();
            String filePath = path.getPath() + "/MainActivity.apk";
            downloadFile = new File(filePath);
            if (downloadFile.exists())
            {
                boolean b = downloadFile.delete();
                System.out.print(b);
            }
            downloadFile = new File(filePath);
            fos = new FileOutputStream(downloadFile.getPath());
            byte bytes[] = new byte[1024];
            int len = -1;

            while ((len = iStream.read(bytes)) != -1)
            {
                Message m = new Message();
                Bundle bundle = new Bundle();
                fos.write(bytes, 0, len);
                downloadedSize += len;
                m.what = 0;// 鍥炴姤杩涘害
                bundle.putInt("progress", downloadedSize);
                m.setData(bundle);
                handler.sendMessage(m);
            }
            fos.flush();
            Message m = new Message();
            m.what = 1;// 瀹屾垚
            handler.sendMessage(m);
        }
        catch (MalformedURLException e)
        {
            ShowAlert("下载文件失败");
        }
        catch (IOException e)
        {
            ShowAlert("写入文件失败");
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (Exception e2)
            {
                // TODO: handle exception
            }
        }
    }

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case -1:
                    ShowAlert("下载失败");
                    Update.this.finish();
                    break;
                case 0:
                    Bundle bundle = msg.getData();
                    //System.err.println("progress" + bundle.getInt("progress"));
                    progressDialog.setProgress(bundle.getInt("progress"));
                    break;
                case 1:
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    String filePath =downloadFile.getPath();
                    i.setDataAndType(Uri.parse("file://" + filePath),"application/vnd.android.package-archive");
                    Update.this.startActivity(i);
                    Update.this.finish();
                    break;

            }
        }
    };

    private void ShowAlert(String msg)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(Update.this);
        alert.setMessage(msg);
        alert.setTitle("提示");
        alert.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                System.exit(0);
            }
        });
        alert.create().show();
    }
}
