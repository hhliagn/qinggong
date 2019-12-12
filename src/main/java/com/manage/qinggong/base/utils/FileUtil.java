package com.manage.qinggong.base.utils;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class FileUtil {
	/** 自定义文件在编辑器中存放的地址 */
	//public static final String FILEPATH="D:\\workspace_eclipse2\\hospitalSystem\\src\\main\\webapp\\files\\";
	//public static final String FILEPATH="E:\\hospitalSystem\\src\\main\\webapp\\files\\";
	public static final String FILEPATH="D:\\hospitalSystem\\src\\main\\webapp\\files\\";
	
	/** 生产环境中图片的存放的地址 */
	public static final String FILEPATH2="D:\\Program Files\\apache-tomcat-7.0.53\\wtpwebapps\\hospitalSystem\\files\\";
	//public static final String FILEPATH2="E:\\apache-tomcat-7.0.40\\wtpwebapps\\hospitalSystem\\files\\";
	//public static final String FILEPATH2="D:\\apache-tomcat-7.0.40\\wtpwebapps\\hospitalSystem\\files\\";
//	static{
//		File file=new File("");
//		String path="";
//		try {
//			path=file.getCanonicalPath();
//			System.out.println("path="+path);
//		} catch (IOException e) {
//			System.err.println("file path create failed");
//			e.printStackTrace();
//		}finally {//设置自定义路径:缺省为当前项目的二维码的存放路径
//			FILEPATH=path+"\\src\\main\\webapp\\files\\";
//		}
//		
//	}
	
	/**
     * TODO 
     * @param dir 
     */
    public static void mkdirs(String dir) {
        File file = new File(dir);
        if (file.isDirectory())
            return;
        else
            file.mkdirs();
    }
    
    /**
     * 将data64url格式的文件数据生成指定路径的文件的工具方法
     * @param data64url
     * @param fileName
     * @param type 
     * @param req
     * @return [0]文件访问路径;[1]文件的存放地址
     * @throws IOException
     */
    public static String[] createFile(String data64url,String fileName,String type) throws IOException{
    	if(data64url==null||"".equals(data64url)||fileName==null||"".equals(fileName)){//文件数据为空时的处理
    		return null;
    	}
    	if(type==null||"".equals(type)){//如果类型为传入参数,则按"QRCode"处理即可
    		type="QRCode";
    	}
    	//String url=req.getRequestURL().toString();
		//String[] strs=url.split("/");
		//文件的访问路径
		//String fileUrl=strs[0]+"//"+strs[2]+"/"+strs[3]+"/file/"+type+"/"+fileName;
		String fileUrl="/files/"+type+"/"+fileName;
		System.out.println("fileUrl="+fileUrl);
		//文件在编辑器上的存放地址
		String filePath=FILEPATH+type+"\\"+fileName;
		System.out.println("filePath="+filePath);
		//文件在生产环境下的存放地址
		String filePath2=FILEPATH2+type+"\\"+fileName;
    	
		//处理文件:将data64url数据转成文件对象
		//1.在指定的位置创建指定文件全限定名的文件
		File pictureFile=new File(filePath);
		if(!pictureFile.exists()){
			try {
				pictureFile.createNewFile();
				//System.out.println(musicBoolean);
			} catch (IOException e) {
				System.err.println("创建背景图片文件失败");
				e.printStackTrace();
			}
		}else{
			//修改,则直接覆盖即可
		}
		//创建生产环境下的文件
		File pictureFile2=new File(filePath2);
		if(!pictureFile2.exists()){
			try {
				pictureFile2.createNewFile();
				//System.out.println(musicBoolean);
			} catch (IOException e) {
				System.err.println("创建背景图片文件失败");
				e.printStackTrace();
			}
		}else{
			//修改,则直接覆盖即可
		}
		
		//2.将传来的data64url数据通过相应的io流放入指定的文件中
		byte[] imageData=DatatypeConverter.parseBase64Binary(data64url.substring(data64url.indexOf(",")+1));
		BufferedImage bufferedImage=ImageIO.read(new ByteArrayInputStream(imageData));
		String[] s=fileName.split("\\.");
//		for (String string : s) {
//			System.out.println(string);
//		}
		//s[1]为文件的后缀名,用于确定文件的类型
		ImageIO.write(bufferedImage, s[1], new File(filePath));
		ImageIO.write(bufferedImage, s[1], new File(filePath2));
    	
		String[] fileParams={fileUrl,filePath};
    	return fileParams;
    }
    
    
    
    
}