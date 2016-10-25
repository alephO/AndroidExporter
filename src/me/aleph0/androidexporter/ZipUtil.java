package me.aleph0.androidexporter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ZipUtil {
    private String src;
    private String target;
    private List<String> fileList = new ArrayList<>();
    private List<String> relativeList = new ArrayList<>();
    private StringBuilder log = new StringBuilder();
    public ZipUtil(String src, String target){
        this.src = src;
        this.target =target;
    }
    public boolean zip(){
        addFile(new File(src), true, "");
        byte[] buffer = new byte[1024];
        FileOutputStream fos;
        ZipOutputStream zos = null;
        try{
            fos = new FileOutputStream(new File(target));
            zos = new ZipOutputStream(fos);
            FileInputStream in = null;
            for(int i = 0; i < fileList.size(); i++){
                ZipEntry zipEntry= new ZipEntry(relativeList.get(i));
                zos.putNextEntry(zipEntry);
                try {
                    in = new FileInputStream(fileList.get(i));
                    int len;
                    while ((len = in.read(buffer)) > 0)
                    {
                        zos.write(buffer, 0, len);
                    }
                }
                finally {
                    in.close();
                }
            }
            ZipEntry zipEntry = new ZipEntry(new File(src).getName()+File.separator+"etc"+File.separator+"log"+File.separator+"compress.log");
            zos.putNextEntry(zipEntry);
            byte[] data = log.toString().getBytes();
            zos.write(data, 0, data.length);
            zos.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    private void addFile(File file, boolean add, String pre){
        if(file.isFile()){
            if(add){
                String path = file.getAbsolutePath();
                fileList.add(path);
                relativeList.add(pre+file.getName());
                log.append("Append: ");
                log.append(path);
                log.append("\n");
            } else {
                log.append("Ignore: ");
                log.append(file.getAbsolutePath());
                log.append("\n");
            }
        } else {
            String[] lst = file.list();
            if(lst == null){
                return;
            }
            for(String s:lst){
                Boolean imp = add && (!s.equals("build"));
                addFile(new File(file,s),imp,pre+file.getName()+File.separator);
            }
        }
    }
}
