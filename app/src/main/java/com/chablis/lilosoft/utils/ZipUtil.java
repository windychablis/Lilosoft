package com.chablis.lilosoft.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipUtil {

    private static final int BUFF_SIZE = 1024 * 1024; // 1M Byte

    /**
     * 解压缩一个文件
     *
     * @param zipFile
     *            压缩文件
     * @param folderPath
     *            解压缩的目标目录
     * @throws IOException
     *             当解压缩过程出错时抛出
     */
    public static void upZipFile(File zipFile, String folderPath)
            throws ZipException, IOException {
        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdirs();
        }else if( !desDir.isDirectory() && desDir.canWrite() ){
            desDir.delete();
            desDir.mkdirs();
        }
        ZipFile zf = new ZipFile(zipFile);
        InputStream in = null;
        OutputStream out = null;
        try {
            for (Enumeration<?> entries = zf.entries(); entries
                    .hasMoreElements();) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                in = zf.getInputStream(entry);
                String str = folderPath + File.separator + "img";
                File desFile = new File(str);
                if (!desFile.exists()) {
                    File fileParentDir = desFile.getParentFile();
                    if (!fileParentDir.exists()) {
                        fileParentDir.mkdirs();
                    }
                    desFile.createNewFile();
                }
                out = new FileOutputStream(desFile);
                byte buffer[] = new byte[BUFF_SIZE];
                int realLength;
                while ((realLength = in.read(buffer)) > 0) {
                    out.write(buffer, 0, realLength);
                }

            }
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        }
    }

    public static void unzipFiles(File file,String destDir)
            throws FileNotFoundException,IOException {
        //压缩文件
        File srcZipFile=file;
        //基本目录
        if(!destDir.endsWith("/")){
            destDir+="/";
        }
        String prefixion=destDir;

        //压缩输入流
        ZipInputStream zipInput=new ZipInputStream(new FileInputStream(srcZipFile));
        //压缩文件入口
        ZipEntry currentZipEntry=null;
        //循环获取压缩文件及目录
        while((currentZipEntry=zipInput.getNextEntry())!=null){
            //获取文件名或文件夹名
            String fileName=currentZipEntry.getName();
            //Log.v("filename",fileName);
            //构成File对象
            File tempFile=new File(prefixion+fileName);
            //父目录是否存在
            if(!tempFile.getParentFile().exists()){
                //不存在就建立此目录
                tempFile.getParentFile().mkdir();
            }
            //如果是目录，文件名的末尾应该有“/"
            if(currentZipEntry.isDirectory()){
                //如果此目录不在，就建立目录。
                if(!tempFile.exists()){
                    tempFile.mkdir();
                }
                //是目录，就不需要进行后续操作，返回到下一次循环即可。
                continue;
            }
            //如果是文件
            if(!tempFile.exists()){
                //不存在就重新建立此文件。当文件不存在的时候，不建立文件就无法解压缩。
                tempFile.createNewFile();
            }
            //输出解压的文件
            FileOutputStream tempOutputStream=new FileOutputStream(tempFile);

            //获取压缩文件的数据
            byte[] buffer=new byte[1024];
            int hasRead=0;
            //循环读取文件数据
            while((hasRead=zipInput.read(buffer))>0){
                tempOutputStream.write(buffer,0,hasRead);
            }
            tempOutputStream.flush();
            tempOutputStream.close();
        }
        zipInput.close();
    }

    public static void unZipFiles(File zipFile,String destDir)throws IOException{
        if(!destDir.endsWith("/")){
            destDir+="/";
        }
        File pathFile = new File(destDir);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile);
        for(Enumeration entries = zip.entries();entries.hasMoreElements();){
            ZipEntry entry = (ZipEntry)entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            String outPath = (destDir+zipEntryName).replaceAll("\\*", "/");;
            //判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if(!file.exists()){
                file.mkdirs();
            }
            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if(new File(outPath).isDirectory()){
                continue;
            }
            //输出文件路径信息
            System.out.println(outPath);

            OutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while((len=in.read(buf1))>0){
                out.write(buf1,0,len);
            }
            in.close();
            out.close();
        }
    }


}
