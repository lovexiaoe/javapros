package net.zhaoyu.javapros.j2se.threads.sychronized.listener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteDownloader extends  Thread{
    private InputStream in;
    private OutputStream out;
    private CopyOnWriteArrayList<ProgressListener> listeners;

    public CopyOnWriteDownloader(URL url, String outputFilename) throws IOException {
        in = url.openConnection().getInputStream();
        out = new FileOutputStream(outputFilename);
        listeners = new CopyOnWriteArrayList<>();
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
     *
     * 这里我们使用了CopyOnWriteArrayList,当list发生改变时，就会复制一个新的List，任何已经存在的Iterator引用原来的list。
     * 这样就避免了引起死锁的危险。
     */
    private synchronized void updateProgress(int n) {
        for (ProgressListener listener : listeners)
            listener.onProgress(n);
    }

    public void run() {
        int n, total = 0;
        byte[] buffer = new byte[1024];
        try {
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
                total += n;
                updateProgress(total);
            }
            out.flush();
        } catch (IOException e) {
        }
    }
    interface ProgressListener{
        void onProgress(int n);
    }
}
