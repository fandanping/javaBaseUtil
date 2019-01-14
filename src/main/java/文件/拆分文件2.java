package 文件;

import java.io.*;

/**
 * 拆分文件
 * 读取一个大文件，按照每是十万行拆分为一个文件
 * 每读一行放入一个文件，当大于100000行，另开辟一个文件
 */
public class 拆分文件2 {
    FileReader fr=null;
    FileWriter fw=null;
    BufferedReader br=null;
    BufferedWriter bw=null;
    public void read(){
        try {
            int row=1;
            int fileNo=1;
            fr=new FileReader("E:\\ext_word_cn - 副本.txt");
            br=new BufferedReader(fr);
            fw=new FileWriter("E:\\test1\\"+fileNo);
            bw=new BufferedWriter(fw);
            String  str="";
            while((str=br.readLine())!=null){
                     bw.write(str);
                     bw.newLine();
                    if(row %100000 ==0){
                        bw.flush();
                        fileNo++;
                        fw.close();
                        bw.close();
                        bw=new BufferedWriter(new FileWriter("E:\\test1\\"+fileNo));
                    }
                row++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fr.close();
                br.close();
                fw.close();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args){
        拆分文件2 test=new 拆分文件2();
        test.read();
    }
}
