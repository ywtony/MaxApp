package com.bohui.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.bohui.R;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.io.File;


/**
 * 操作第三方jar包Imageloader的工具类
 *
 * @author tony
 */
public class ImageLoaderUtils {
    private ImageLoaderUtils() {
    }

    private static ImageLoaderUtils instance = null;

    public static synchronized ImageLoaderUtils getInstance() {
        if (instance == null) {
            instance = new ImageLoaderUtils();
        }
        return instance;
    }

    /**
     * 初始化ImageLoader
     *
     * @param
     */
    public void initImageLoader(Context context) {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setStaleCheckingEnabled(params, false);
        HttpConnectionParams.setConnectionTimeout(params, 10 * 1000);
        HttpConnectionParams.setSoTimeout(params, 10 * 1000);
        HttpConnectionParams.setSocketBufferSize(params, 8192);
        HttpClientParams.setRedirecting(params, false);
        HttpProtocolParams.setUserAgent(params, "some_randome_user_agent");
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory
                .getSocketFactory(), 443));

        ClientConnectionManager manager = new ThreadSafeClientConnManager(
                params, schemeRegistry);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPriority(3)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())

                .discCacheSize(100 * 1024 * 1024)
                //
                .discCacheFileCount(3000)
                // 缓存3百张图片
                .writeDebugLogs()
                .memoryCache(new WeakMemoryCache())

                /*.imageDownloader( new HttpClientImageDownloader(context, new
                DefaultHttpClient(manager, params)))*/

                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
//		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));不然会报
        ImageLoader.getInstance().init(config);
    }

    // @SuppressWarnings("deprecation")
    public DisplayImageOptions initOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .
                        cacheOnDisc(true).
                        considerExifParams(true)
//				.displayer(new RoundedBitmapDisplayer(0))
                .build();
        return options;
    }

    /**
     * 获取图片缓存的大小
     */
    public long getCache() {
        DiskCache diskCache = ImageLoader.getInstance().getDiskCache();
        File file = diskCache.getDirectory();
        long size = file.length();
        DevLog.e("size-------:" + size);
        return size;
    }

    /**
     * 清除图片缓存
     */
    public void clearCache() {
        ImageLoader.getInstance().clearDiskCache();
        ImageLoader.getInstance().clearMemoryCache();
    }
}
