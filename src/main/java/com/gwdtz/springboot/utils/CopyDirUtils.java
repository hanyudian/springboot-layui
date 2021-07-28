package com.gwdtz.springboot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class CopyDirUtils {
    public static void copyDir(String sourcePath, String newPath) throws IOException {
        File file = new File(sourcePath);   //获取文件夹File对象
        String[] filePath = file.list();    //获取文件夹下所有内容的名称

        if (!(new File(newPath)).exists()) {  //判断要目标文件夹是否存在不存在则创建
            (new File(newPath)).mkdir();
        }

        for (int i = 0; i < filePath.length; i++) {  //循环遍历
            //判断是不是文件夹，是的话执行递归。file.separator 分隔符，如“/”
            if ((new File(sourcePath + File.separator + filePath[i])).isDirectory()) {
                copyDir(sourcePath  + File.separator + filePath[i], newPath  + File.separator + filePath[i]);
            }
            //判断是不是文件，是的话旧的文件拷至新的文件夹下
            if (new File(sourcePath  + File.separator + filePath[i]).isFile()) {
                copyFile(sourcePath + File.separator + filePath[i], newPath + File.separator + filePath[i]);
            }

        }
    }

    public static void copyFile(String oldPath, String newPath) throws IOException {
        File oldFile = new File(oldPath);//获取旧的文件File对象
        File file = new File(newPath);  //获取新的文件File对象并生成文件
        FileInputStream in = new FileInputStream(oldFile);  //
        FileOutputStream out = new FileOutputStream(file);

        byte[] buffer=new byte[2097152];
        int readByte = 0;
        //读取旧文件的流写入新文件里
        while((readByte = in.read(buffer)) != -1){
            out.write(buffer, 0, readByte);
        }

        in.close();
        out.close();
    }
}
