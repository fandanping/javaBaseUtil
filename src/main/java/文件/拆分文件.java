package 文件;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 拆分文件
 * 读取一个大文件，按照每是十万行拆分为一个文件
 */
public class 拆分文件 {
    FileReader fr=null;
    BufferedReader br=null;
    FileWriter fileWriter =null;
    BufferedWriter bufWriter =null;
    List<String> list = new ArrayList<String>();
    /**
     * 读取文件 ，读取10万行放入List中，当凑够十万行就插入文件
     */
    public  void read(){
        try {
            fr=new FileReader("E:\\ext_word_cn - 副本.txt");
            br=new BufferedReader(fr);
            String str="";
            int row=1;
            while((str=br.readLine()) !=null){
                list.add(str);
                 if(row %100000 ==0){
                    this.write(row);
                    //清空list列表
                    list.clear();
                 }
                 row++;
            }
            //剩余的
            this.write(row);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw  new RuntimeException("file not find");
        } catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException("IO流异常--输入流异常");
        }finally{
            //关闭输入流操作
            try {
                fr.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取list内容写入文件
     * @param row
     */
    public void write(int row){
        try{
            fileWriter = new FileWriter(new File("E:\\test\\"+row));
            bufWriter = new BufferedWriter(fileWriter);
            for(int i=0;i<list.size();i++){
                bufWriter.write(list.get(i));
                bufWriter.newLine();
            }
            bufWriter.flush();
        }catch(IOException e){
            e.printStackTrace();
            throw  new RuntimeException("IO流异常--输出流异常");
        }finally{
            //关闭输出流操作
            try {
                bufWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args){
        拆分文件 demo=new 拆分文件();
        demo.read();
    }
}
