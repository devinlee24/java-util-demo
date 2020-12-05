package com.devinlee.javautildemo.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileUtils {

    /**
     * 从输入流中读取string
     * @param inputStream
     * @return
     */
    public static String getString(InputStream inputStream){
        String returnStr = "";
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            StringBuffer sb = new StringBuffer();
            String str = "";
            while ((str = reader.readLine()) != null)
            {
                sb.append(str).append("\n");
            }
            returnStr = sb.toString();
        }catch(Exception e){

        }
        return returnStr;
    }


    /**
     * 新建一个文件并写入内容
     *
     * @param String path     文件全路径
     * @param String fileName 文件名
     * @param String content  内容
     * @param int bufLen      设置缓冲区大小
     * @param boolean isWrite 是否追加写入文件
     * @return boolean
     * @throws IOException
     */
    public  static boolean newStaticFile(String path, String fileName, String content,
                                         int bufLen, boolean isWrite)  {

        if (path == null || path.equals("") || content == null
                || content.equals(""))
            return false;
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream fos = null;
        Writer out = null;
        try {
            fos = new FileOutputStream(path + File.separator + fileName,
                    isWrite);
            out = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"),
                    bufLen);
            out.write(content);
            out.flush();
            flag = true;
            //log.info(content);
        } catch (IOException e) {
            System.out.println("写入文件出错");
            flag = false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }


    /**
     * 复制一个目录及其子目录、文件到另外一个目录
     * @param src
     * @param dest
     * @throws IOException
     */
    public static void copyFolder(File src, File dest){
        try{
            if (src.isDirectory()) {
                if (!dest.exists()) {
                    dest.mkdirs();
                }
                String files[] = src.list();
                for (String file : files) {
                    File srcFile = new File(src, file);
                    File destFile = new File(dest, file);
                    // 递归复制
                    copyFolder(srcFile, destFile);
                }
            } else {
                InputStream in = new FileInputStream(src);
                OutputStream out = new FileOutputStream(dest);

                byte[] buffer = new byte[1024];

                int length;

                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                in.close();
                out.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
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

    /**
     * 复制单个文件
     *
     * @param oldPath
     *            String 原文件路径 如：c:/fqf.txt
     * @param newPath
     *            String 复制后路径 如：f:/
     * @return boolean
     */
    public static boolean copyFile(String oldPath, String newPath,
                                   String newFileName) throws IOException {

        boolean flag = false;
        if (oldPath == null || newPath == null || newPath.equals("")
                || oldPath.equals("")) {
            return flag;
        }
        InputStream inStream = null;
        FileOutputStream fs = null;
        try {
            int bytesum = 0;
            int byteread = 0;
            File file = null;
            file = new File(newPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(oldPath);
            if (file.exists()) { // 文件存在时
                inStream = new FileInputStream(oldPath); // 读入原文件
                if (newFileName == null || newFileName.equals("")) {
                    newFileName = file.getName();
                }
                fs = new FileOutputStream(newPath + File.separator
                        + newFileName);
                byte[] buffer = new byte[1024 * 8];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
//					System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                flag = true;
            }
        } catch (IOException e) {
            throw e;

        } finally {
            try {
                if (fs != null) {
                    fs.close();
                }
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
        return flag;
    }

    /**
     * 读取文本文件内容，以行的形式读取
     *
     * @param String
     *            filePathAndName 带有完整绝对路径的文件名
     * @return String 返回文本文件的内容
     */
    public static String readFileContent(String filePathAndName)
            throws IOException {
        return readFileContent(filePathAndName, null, null, 1024);
    }

    /**
     * 读取文本文件内容，以行的形式读取
     *
     * @param String
     *            filePathAndName 带有完整绝对路径的文件名
     * @param String
     *            encoding 文本文件打开的编码方式 例如 GBK,UTF-8
     * @param String
     *            sep 分隔符 例如：#，默认为/n;
     * @param int
     *            bufLen 设置缓冲区大小
     * @return String 返回文本文件的内容
     */
    public static String readFileContent(String filePathAndName,
                                         String encoding, String sep, int bufLen) throws IOException {
        if (filePathAndName == null || filePathAndName.equals("")) {
            return "";
        }
        if (sep == null || sep.equals("")) {
            sep = System.getProperty("line.separator");
        }
        if (!new File(filePathAndName).exists()) {
            return "";
        }
        StringBuffer str = new StringBuffer("");
        FileInputStream fs = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            fs = new FileInputStream(filePathAndName);
            if (encoding == null || encoding.trim().equals("")) {
                isr = new InputStreamReader(fs);
            } else {
                isr = new InputStreamReader(fs, encoding.trim());
            }
            br = new BufferedReader(isr, bufLen);

            String data = "";
            while ((data = br.readLine()) != null) {
                if(!str.toString().isEmpty()){
                    str.append(sep);
                }
                str.append(data);
            }

        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (br != null)
                    br.close();
                if (isr != null)
                    isr.close();
                if (fs != null)
                    fs.close();
            } catch (IOException e) {
                throw e;
            }

        }
        return str.toString();
    }
    /**
     * 新建一个文件并写入内容
     *
     * @param String
     *            path 文件全路径
     * @param String
     *            fileName 文件名
     * @param String
     *            content 内容
     * @return boolean
     * @throws IOException
     */
    public static boolean newFile(String path, String fileName, String content)
            throws IOException {

        return newFile(path, fileName, content, 1024, false);
    }
    /**
     * 新建一个文件并写入内容
     *
     * @param String
     *            path 文件全路径
     * @param String
     *            fileName 文件名
     * @param String
     *            content 内容
     * @param int
     *            bufLen 设置缓冲区大小
     * @param boolean
     *            isWrite 是否追加写入文件
     * @return boolean
     * @throws IOException
     */
    public static boolean newFile(String path, String fileName, String content,
                                  int bufLen, boolean isWrite) throws IOException {

        if (path == null || path.equals("") || content == null
                || content.equals(""))
            return false;
        boolean flag = false;
        FileWriter fw = null;
        BufferedWriter bw = null;

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();

        }
        try {
            fw = new FileWriter(path + File.separator + fileName, isWrite);
            bw = new BufferedWriter(fw, bufLen);
            bw.write(content);
            flag = true;
        } catch (IOException e) {
            System.out.println("写入文件出错");
            flag = false;
            throw e;
        } finally {
            if (bw != null) {
                bw.flush();
                bw.close();
            }
            if (fw != null)
                fw.close();
        }

        return flag;
    }


    /**
     * 复制二进制文件
     * @param filePath
     *
     * Created by devinlee on 2017.05.26
     */
    public static void copyFile(String filePath) throws Exception {
        //转换的原文件对象，新文件对象
        File oldfile = new File(filePath), newFile = null;
        //新文件名下标从2开始
        int fileIndex = 2;
        //用来储存路径的字符对象
        StringBuilder tempStringPath = new StringBuilder();
        //循环检查文件是否存在，直到可创建为止
        while (true) {
            tempStringPath.append(oldfile.getAbsolutePath());//原文件名(绝对路径)
            tempStringPath.insert(tempStringPath.lastIndexOf("."), "(" + fileIndex + ")");//生成新文件名
            newFile = new File(tempStringPath.toString());//初始化新文件对象
            //检查文件是否已存在
            if (!newFile.exists()) {
                newFile.createNewFile();//创建文件
                break;
            }
            tempStringPath.setLength(0);//清空已存在的文件路径
            fileIndex++;//文件已存在，下标增加1，尝试下一个路径，直到文件不存在为止
        }
        //原文件的字节输入流
        FileInputStream oldFileInputStream = new FileInputStream(oldfile);
        //新文件的字节输出流
        FileOutputStream newFileOutputStream = new FileOutputStream(newFile);

        byte[] buff = new byte[1024];//设置内存缓存区(提高读取效率)
        int length = 0;//记录每次读取返回的内容长度
        while ((length = oldFileInputStream.read(buff)) != -1){
            //将每次读取的字节内容写入新文件
            newFileOutputStream.write(buff);
        }
        newFileOutputStream.close();//最后记得关闭输出流
        oldFileInputStream.close();//最后记得关闭输入流
    }
}
