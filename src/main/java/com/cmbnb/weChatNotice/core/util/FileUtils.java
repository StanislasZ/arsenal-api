package com.cmbnb.weChatNotice.core.util;


import com.cmbnb.weChatNotice.core.base.CommonJsonException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

@Service
public class FileUtils {

    private static final int buffer = 2048;

    public File saveFile(byte[] data, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            file = new File(fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonJsonException("zip文件写入失败");
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }


    public String unzip(String path) {
        int count = -1;
        String savepath = "";
        File file = null;
        InputStream is = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        savepath = path.substring(0, path.lastIndexOf(".")) + File.separator; //保存解压文件目录
        System.out.println("savepath = " + savepath);    //   为/opt/1628132352794\
        new File(savepath).mkdir(); //创建保存目录
        ZipFile zipFile = null;
        String dirpath = "";
        try
        {
            zipFile = new ZipFile(path,"gbk"); //解决中文乱码问题
            Enumeration<?> entries = zipFile.getEntries();
            while(entries.hasMoreElements()) {
                byte buf[] = new byte[buffer];
                ZipEntry entry = (ZipEntry)entries.nextElement();
                String filename = entry.getName();
                System.out.println("entry.name = " + entry.getName());
                boolean ismkdir = false;
                if (filename.lastIndexOf("/") != -1){ //检查此文件是否带有文件夹
                    ismkdir = true;
                }
                filename = savepath + filename;
                if(entry.isDirectory()){ //如果是文件夹先创建
                    file = new File(filename);
                    file.mkdirs();
                    continue;
                }
                file = new File(filename);
                if(!file.exists()){ //如果是目录先创建
                    if(ismkdir){
                        new File(filename.substring(0, filename.lastIndexOf("/"))).mkdirs(); //目录先创建
                    }
                }
                file.createNewFile(); //创建文件
                is = zipFile.getInputStream(entry);
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos, buffer);
                while((count = is.read(buf)) > -1)
                {
                    bos.write(buf, 0, count);
                }
                bos.flush();
                bos.close();
                fos.close();
                is.close();
            }
            zipFile.close();

            //获取最后一级文件夹
            System.out.println("循环结束后, file = " + file.toString());
            String fullPath = file.toString();
            dirpath = fullPath.substring(0, fullPath.lastIndexOf(File.separator)) + File.separator;
            System.out.println("dirpaht = " + dirpath);

        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            try{
                if(bos != null){
                    bos.close();
                }
                if(fos != null) {
                    fos.close();
                }
                if(is != null){
                    is.close();
                }
                if(zipFile != null){
                    zipFile.close();
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return dirpath;
    }


    public String getStrFromTxt(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }


    public boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }


    public String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    /**
     * 重命名
     * @param oldname
     * @param newname
     * @return
     */
    public boolean rename(String oldname, String newname) {

        File old = new File(oldname);
        File now = new File(newname);
        return rename(old, now);
    }

    public boolean rename(File old, File now) {
        now.delete();
        boolean rlt = old.renameTo(now);
        if (!rlt) {
            System.out.println(old.getName() + " 重命名成 " + now.getName() + "  失败");
        }
        return rlt;
    }



    public static void main(String[] args) {
        String path = "/opt/1628132352794.zip";
        new FileUtils().unzip(path);
    }
}
