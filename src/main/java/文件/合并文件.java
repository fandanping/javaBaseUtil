package 文件;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 合并文件
 */
public class 合并文件 {
    public boolean  unionFile(String outfile,String dictionary)throws IOException {
        boolean result=false;
        List<File> fileList=getFiles(dictionary);
        File fout=new File(outfile);
        FileWriter fw=new FileWriter(fout);
        BufferedWriter bw=new BufferedWriter(fw);
        int row=0;
        int filerow=0;
        for(File f:fileList){
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line="";
            while ((line=br.readLine()) != null) {
                row++;
                filerow++;
                bw.write(line);
                bw.newLine();
            }
            System.out.println("文件："+filerow);
            filerow=0;
            bw.flush();
            fr.close();
            br.close();
        }
        System.out.println("总共多少行"+row);
        fw.close();
        bw.close();
        result=true;
        return result;
    }

    /**
     * 获取要合并的所有文件
     * @param path
     * @return
     */
    public static List<File> getFiles(String path){
        File root = new File(path);
        List<File> files = new ArrayList<File>();
        if(!root.isDirectory()){
            files.add(root);
        }else{
            File[] subFiles = root.listFiles();
            for(File f : subFiles){
                files.addAll(getFiles(f.getAbsolutePath()));
            }
        }
        return files;
    }
    public static void main(String[] args) throws IOException {
        合并文件 test=new 合并文件();
        test.unionFile("D:\\markFile\\out.txt","D:\\markFile");
    }
}
