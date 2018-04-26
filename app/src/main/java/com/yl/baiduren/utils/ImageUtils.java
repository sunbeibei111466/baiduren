package com.yl.baiduren.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Android_apple on 2018/1/30.
 */

public class ImageUtils {


    public static List<File> comperssImage(List<PhotoInfo> photoInfoList) {

        List<File> fileList = null;
        if (photoInfoList.size() != 0) {
            fileList=new ArrayList<>();
            for (int i = 0; i < photoInfoList.size(); i++) {
                File file = ImageUtils.scal(Uri.parse(photoInfoList.get(i).getPhotoPath()),i);
                fileList.add(file);
                LUtils.e("-------压缩后-------" + file.length() / 1024);
                LUtils.e("-------压缩后---file----" + file.getPath());
            }
            return fileList;
        }

        return null;
    }

    /**
     * 压缩图片
     *
     * @param fileUri
     * @return
     */
    public static File scal(Uri fileUri,int i) {
        String path = fileUri.getPath();
        File outputFile = new File(path);
        long fileSize = outputFile.length();
        final long fileMaxSize = 200 * 1024;
        if (fileSize >= fileMaxSize) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int height = options.outHeight;
            int width = options.outWidth;
            double scale = Math.sqrt((float) fileSize / fileMaxSize);
            options.outHeight = (int) (height / scale);
            options.outWidth = (int) (width / scale);
            options.inSampleSize = (int) (scale + 0.5);
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            outputFile = new File(createImageFile(i).getPath());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(outputFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            } else {
                File tempFile = outputFile;
                outputFile = new File(createImageFile(i).getPath());
                copyFileUsingFileChannels(tempFile, outputFile);
            }

        }
        return outputFile;
    }

    public static Uri createImageFile(int i) {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp+i;
        File storageDir = new File(Constant.COMPERSS_IMAGE);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = null;
        try {
            image = new File(storageDir, imageFileName + ".jpg");
            image.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Uri.fromFile(image);
    }

    private static void copyFileUsingFileChannels(File source, File dest) {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            try {
                inputChannel = new FileInputStream(source).getChannel();
                outputChannel = new FileOutputStream(dest).getChannel();
                outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } finally {
            try {
                if (inputChannel != null && outputChannel != null) {
                    inputChannel.close();
                    outputChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 获或报告存储路径
     * @return
     */
    public static Uri getReportUrl(String reportName){
        String timeStamp = new SimpleDateFormat("MMdd_HHmmss").format(new Date());
        String fileName = reportName + timeStamp;
        File storageDir = new File(Constant.REPORT);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
            LUtils.e("------文件夹不存在-----");
        }else {
            LUtils.e("------文件夹存在-----");
        }
        File file=null;
        try {
            LUtils.e("-----timeStamp--fileName--",timeStamp+"------"+fileName);
            file = new File(storageDir, fileName + ".pdf");
            file.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(file);
    }
}
