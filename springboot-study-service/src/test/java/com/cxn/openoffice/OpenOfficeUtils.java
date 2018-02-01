package com.cxn.openoffice;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * 将文件转换为图片工具类
 */
public class OpenOfficeUtils {
	public static final DecimalFormat format = new DecimalFormat("00");
	private static OpenOfficeConnection startOpenOffice(){
//		String openOfficePath="D:\\MyProgram\\LibreOffice5\\program\\soffice.exe" ;
//		//        //opt/openoffice4/program/soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard & 
//		//        //ip=127.0.0.1
//		//        //port=8100
//		//        //OpenOffice的安装目录，linux环境下需要手动启动openoffice服务
//		String OpenOffice_HOME = openOfficePath;
//		String OpenOffice_IP = "192.168.21.66";// PropertiesUtil.getOpenOfficeParam("ip");
//		int OpenOffice_Port = 8100;//Integer.parseInt(PropertiesUtil.getOpenOfficeParam("port"));
//		// 启动OpenOffice的服务
//		String command = OpenOffice_HOME+ " -headless -accept=\"socket,host="+OpenOffice_IP+",port="+OpenOffice_Port+";urp;\"";
//		try {
//			Process pro = Runtime.getRuntime().exec(command);
//			pro.waitFor();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		//创建连接
		String OpenOffice_IP = "192.168.21.66";
		int OpenOffice_Port = 8100;
		OpenOfficeConnection connection = new SocketOpenOfficeConnection(OpenOffice_IP, OpenOffice_Port); 
		return connection;
	}

	public static String doc2Pdf(String docPath, String pdfPath) throws ConnectException {
		File inputFile = new File(docPath);
		File outputFile = new File(pdfPath);
		OpenOfficeConnection connection = startOpenOffice();
		connection.connect();
		DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
		// converter.convert(inputFile, outputFile);
		DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();   
		// DocumentFormat txt = formatReg.getFormatByFileExtension("xhtml") ;
		DocumentFormat txt = formatReg.getFormatByFileExtension("odt") ;
		DocumentFormat pdf = formatReg.getFormatByFileExtension("pdf") ;
		converter.convert(inputFile, txt, outputFile, pdf);
		connection.disconnect();
		return pdfPath;
	}

	/**
	 * 把ppt word excel等文件生成图片文件
	 * @param docPath 文件路径
	 * @param imgDirPath 图片保存文件夹
	 * @param fileName 文件名称点的前部分
	 */
	public static List<String> doc2Imags(String docPath, String imgDirPath,String fileName){
		String pdfPath =String.format("%s%s.pdf",  FilenameUtils.getFullPath(docPath), FilenameUtils.getBaseName(docPath));
		List<String> pathList = new ArrayList<String>();
		try {
			doc2Pdf(docPath, pdfPath);
			pathList = pdf2Imgs(pdfPath, imgDirPath,fileName);
			File pdf =  new File(pdfPath);
			if(pdf.isFile()){
				pdf.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pathList;
	}
	/**
	 * 将pdf转换成图片
	 * 
	 * @param pdfPath
	 * @param imagePath
	 * @return 返回转换后图片的名字
	 * @throws Exception
	 */
	public static List<String> pdf2Imgs(String pdfPath, String imgDirPath,String fileName) throws Exception {

		Document document = new MyDocument();
		// Document document = new Document();
		document.setFile(pdfPath);

		float scale = 1f;//放大倍数
		float rotation = 0f;//旋转角度

		List<String> imgNames = new ArrayList<String>();
		int pageNum = document.getNumberOfPages();
		File imgDir = new File(imgDirPath);
		if (!imgDir.exists()) {
			imgDir.mkdirs();
		}
		for (int i = 0; i < pageNum; i++) {
			String j = format.format(i+1);
			BufferedImage image = (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN,
					Page.BOUNDARY_CROPBOX, rotation, scale);
			RenderedImage rendImage = image;
			try {
				String filePath = imgDirPath + File.separator +fileName+j + ".jpg";
				File file = new File(filePath);
				ImageIO.write(rendImage, "jpg", file);
				imgNames.add(FilenameUtils.getName(filePath));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			image.flush();
		}
		document.dispose();
		return imgNames;
	}

	public static void main(String[] args) {
		//String docPath = "d:/94_storage安装.doc";
		//file:///D:/prefix/efd8fb68da134b87bcbf6f352f0516d1/docx/loanAgreement.docx
		//file:///D:/prefix/efd8fb68da134b87bcbf6f352f0516d1/docx/loanAgreement.docx
		String docPath = "D:\\DB-bak\\金米袋技术支持----wkhtmltox\\loanAgreement2003.doc";
		// String docPath = "D:/prefix/efd8fb68da134b87bcbf6f352f0516d1/docx/loanAgreement.docx";
		String pdfPath = "D:/prefix/pdf/";
		long s = System.currentTimeMillis();
		List<String> pathDir = doc2Imags(docPath, pdfPath,"page");
		System.out.println(pathDir);
		long e = System.currentTimeMillis();
		System.out.println((e-s)/1000);
	}
}