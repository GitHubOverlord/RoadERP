//package com.lida.road.utils;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//public class ByteToFile {
//	public boolean save(byte[] bytes,String fileName){
//		try {    
//                
//            File filedir = new File("服务器绝对路径文件夹常量");    
//            if (!filedir.exists())    
//                filedir.mkdirs();  
//            File file = new File(filedir, "文件名.后缀名");    
//            OutputStream os = new FileOutputStream(file);  
//                
//            byte[] byteStr = new byte[1024];    
//            int len = 0;    
//            while ((len = is.read(byteStr)) > 0) {    
//                os.write(byteStr,0,len);    
//            }    
//            is.close();    
//                
//            os.flush();    
//            os.close();    
//        } catch (FileNotFoundException e) {    
//            // TODO Auto-generated catch block    
//            e.printStackTrace();    
//        } catch (IOException e) {    
//            // TODO Auto-generated catch block    
//            e.printStackTrace();    
//        }    
//	}
//}
