package 文件;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
public class 去除文件中的重复行 {
/*
    public  static void main(String[] args) {
        long start =System.currentTimeMillis();
        // 需要处理数据的文件位置
        FileReader fr = null;
        BufferedReader br = null;
        FileWriter fw= null;
        BufferedWriter bw =null;
        try {
            fr = new FileReader(new File("E:\\test.txt"));
            br = new BufferedReader(fr);
            Map<String, String> map = new HashMap<String, String>();
            Map<String, String> mapChongfu = new HashMap<String, String>();
            String readLine = null;
            int i = 0;

            while ((readLine = br.readLine()) != null) {
                // 每次读取一行数据，与 map 进行比较，如果该行数据 map 中没有，就保存到 map 集合中
                if (!map.containsValue(readLine)) {
                    map.put("key" + i, readLine);
                    i++;
                }else{
                    mapChongfu.put("key" + i, readLine);
                }
            }
            fw=new FileWriter("E:\\test1.txt");
            bw =new BufferedWriter(fw);
            for (int j = 0; j < map.size(); j++) {
                bw.write(map.get("key"+j));
                bw.newLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
              *//*  fr.close();
                br.close();
                fw.close();
                bw.close();*//*
            } *//*catch (IOException e) {
                e.printStackTrace();
            }*//*
        }
        long end =System.currentTimeMillis();
        System.out.println(end-start+"毫秒");
    }*/
}
