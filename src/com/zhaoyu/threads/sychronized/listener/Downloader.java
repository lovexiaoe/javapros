package com.zhaoyu.threads.sychronized.listener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * 程序一般使用监听器进行解耦，下面的例子是一个下载程序，下载进度会同步更新到监听对象。
 */
public class Downloader extends Thread {
    private InputStream in;
    private OutputStream out;
    private ArrayList<ProgressListener> listeners;

    public Downloader(URL url, String outputFilename) throws IOException {
        in = url.openConnection().getInputStream();
        out = new FileOutputStream(outputFilename);
        listeners = new ArrayList<>();
    }

    public synchronized void addListener(ProgressListener listener) {
        listeners.add(listener);
    }

    public synchronized void removeListener(ProgressListener listener) {
        listeners.remove(listener);
    }

    /**
     * 在一个同步方法中调用一个未知的外部方法，如果这个方法有可能获得另一个锁的化(如在onProgress调用addListener或者removeListener)，
     * 那么可能会引起死锁问题。
     */
    private synchronized void updateProgress(int n) {
        for (ProgressListener listener : listeners)
            listener.onProgress(n);
    }

    /**
     * 解决方法就是，复制一份需要操作的数据。这样既避免了在同步方法中调用未知的外部方法，又缩短了持有锁的时间。
     */
    private void updateProgress1(int n) {
        ArrayList<ProgressListener> listenersCopy;
        synchronized(this) {
            listenersCopy = (ArrayList<ProgressListener>)listeners.clone();
        }
        for (ProgressListener listener: listenersCopy)
            listener.onProgress(n);
    }

    public void run() {
        int n, total = 0;
        byte[] buffer = new byte[1024];
        try {
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
                total += n;
                updateProgress1(total);
            }
            out.flush();
        } catch (IOException e) {
        }
    }
    interface ProgressListener{
        void onProgress(int n);
    }
}