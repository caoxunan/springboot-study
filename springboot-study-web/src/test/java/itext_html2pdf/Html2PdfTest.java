package itext_html2pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

public class Html2PdfTest {

	public static void main(String[] args) {
		// 检验系统存在的字体文件（非必要）
		String chineseFont = getChineseFont();
		System.out.println("chineseFont:" + chineseFont);
		
		// 根据模板引擎生成html文件
		Html2PdfTest pdfTest = new Html2PdfTest();
		int num = 30;// 投资人数
		int repaymentNum = 20;// 还款期数
		String vmPath = "template/loan_protocol.vm";
		String htmlPath = "src/main/template/loan_protocol.html";
		String pdfPath = "src/main/template/loan_protocol.pdf";
		
		pdfTest.generateHtml(num, repaymentNum, vmPath, htmlPath);
		
		try {
			// draw(pdfPath);
			draw(htmlPath, pdfPath);
			System.out.println("success!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("fail!");
		}

	}
	
	private void generateHtml(int num, int repaymentNum, String src, String target) {
		try {
			// 初始化模板引擎
			VelocityEngine ve = new VelocityEngine();
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			ve.init();
			// 获取模板文件
			Template t = ve.getTemplate(src, "UTF-8");
			// 设置变量
			VelocityContext context = new VelocityContext();

			context.put("applyNo", "CD-180322-0002-A09");
			context.put("applyUser", "陈某某");
			context.put("applyAddress", "浦东新区-峨山路 505 号");
			context.put("upperMoney", "壹万两仟元");
			context.put("lowerMoney", "12000.00");
			context.put("loanUse", "个人使用");
			context.put("periods", "6");
			context.put("startYear", "2018");
			context.put("startMonth", "03");
			context.put("startDay", "24");
			context.put("endYear", "2018");
			context.put("endMonth", "09");
			context.put("endDay", "24");
			context.put("signYear", "2018");
			context.put("signMonth", "03");
			context.put("signDay", "24");
			context.put("yearRate", "4.6");
			context.put("loanType", "等额本息");
			context.put("signer1", "20180323_002");
			context.put("overdueRate1", "5.6");
			context.put("overdueRate2", "5.4");

			List<Map<String, String>> lends = new ArrayList<>();
			for (int i = 1; i <= num; i++) {
				Map<String, String> map = new HashMap<>();
				map.put("lender", "lender"+i);
				map.put("customerId",  "customerId"+i);
				map.put("lendMoney",  "lendMoney"+i);
				map.put("Signer2",  "20180323_20"+i);
				lends.add(map);
			}
			context.put("lends", lends);

			List<Map<String, String>> payments = new ArrayList<>();
			for (int i = 1; i <= repaymentNum; i++) {
				Map<String, String> map = new HashMap<>();
				map.put("paymentPeriod", "lender"+i);
				map.put("paymentMoney",  "customerId"+i);
				map.put("paymentDay",  "lendMoney"+i);
				payments.add(map);
			}
			context.put("payments", payments);
			// 输出
			File file = new File(target);
			File parent = file.getParentFile();
			// 如果pdf保存路径不存在，则创建路径
			if (!parent.exists()) {
				parent.mkdirs();
			}
			Writer writer = new FileWriter(file);
			t.merge(context,writer);
			writer.flush();
			writer.close(); 

			System.out.println("success!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("fail!");
		}

	}
	/**
	 * 绘制一个PDF文件
	 * @param target    生成地址
	 */
	public static void draw(String target) throws IOException, DocumentException {
		Document document = new Document(PageSize.A4,20,20,20,20);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(target));
		document.open();
		// 设置文档属性
		document.addCreationDate();
		document.addCreator("http://www.demodashi.com/");
		document.addTitle("Geek Pdf Demo");
		document.addSubject("PDF生成的Demo");
		// 设置中文字体和字体样式
		BaseFont chineseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font font = new Font(chineseFont, 10, Font.NORMAL);
		Font font1 = new Font(chineseFont, 8, Font.NORMAL);
		// 设置编号
		Paragraph paragraph = new Paragraph("编号：A08485743-1", font1);
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);
		// 创建表格
		PdfPTable pdfTable = new PdfPTable(2);
		float[] widths = {0.06f, 0.06f};
		pdfTable.setWidths(widths);
		//设置表格占PDF文档100%宽度
		pdfTable.setWidthPercentage(100);
		//水平方向表格控件居中
		pdfTable.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
		PdfPCell Cell = new PdfPCell();
		Cell.setColspan(2);
		Cell.setPhrase(new Paragraph("支付宝还款电子回单", font));
		Cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		Cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		pdfTable.addCell(Cell);
		PdfPCell Cell1 = new PdfPCell();
		Cell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		Cell1.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		Cell1.setPhrase(new Paragraph("支付宝还款电子回单", font));
		pdfTable.addCell(Cell1);
		Cell1.setPhrase(new Paragraph("11", font));
		pdfTable.addCell(Cell1);
		document.add(pdfTable);
		document.close();
		writer.close();
	}

	public static void draw(String src,String target)throws IOException, DocumentException{
		// 创建pdf文档
		Document document = new Document(PageSize.A4,20,20,20,20);
		// 创建临时文件
		String tempname = System.currentTimeMillis()+".pdf";
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(tempname));
		document.open();

		// 使用我们的字体提供器，并将其设置为unicode字体样式  
		MyFontsProvider fontProvider = new MyFontsProvider();  
		fontProvider.addFontSubstitute("lowagie", "garamond");  
		fontProvider.setUseUnicode(true);  
		CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);  
		HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);  
		htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());  
		XMLWorkerHelper.getInstance().getDefaultCssResolver(true);  

		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(src), null, Charset.forName("UTF-8"), fontProvider);
		
		// 方式一：这种添加图片的方法只能向最后一页pdf中添加图片
		/*
			Image img = Image.getInstance("src/test/java/testfile/continue.jpg");
			img.setAbsolutePosition(
					(PageSize.POSTCARD.getWidth() - img.getScaledWidth()) / 2,
					(PageSize.POSTCARD.getHeight() - img.getScaledHeight()) / 2);
	
			img.setAbsolutePosition(0, 0);
			document.add(img);
		*/
		// 关闭流（pdf文档生成）
		document.close();

		// 读取上面生成的临时pdf文件
		PdfReader reader = new PdfReader(tempname);
		PdfStamper stamp = new PdfStamper(reader,new FileOutputStream(target));  
		// 关联引用图片
		String imgPath = "src/main/resources/template/stamp.png";
		Image img = Image.getInstance(imgPath);  // 因为使用图章，所以使用png格式  
		// 进行相关设置
		img.setAlignment(Image.LEFT | Image.TEXTWRAP);  
		img.setBorderWidth(10);  
		// 指定图片位置（绝对位置，左下角为(0,0)点）
		img.setAbsolutePosition(100, 170);  
		// overCount 与underCount 浮于文字上下方  获得指定页码
		PdfContentByte over = stamp.getUnderContent(8); 
		over.addImage(img);  
		stamp.close();  
		reader.close();
		// 删除临时文件
		File file = new File(tempname);
		if (null != file && file.exists()) {
			file.delete();
		}
		
	}

	/** 
	 * 获取中文字体位置 
	 * @return 
	 * @author xxj 2017年4月28日 
	 */  
	private static String getChineseFont(){  

		//宋体（对应css中的 属性 font-family: SimSun; /*宋体*/）  
		String font ="C:/Windows/Fonts/simsun.ttc";  
		//判断系统类型，加载字体文件  
		java.util.Properties prop = System.getProperties();  
		String osName = prop.getProperty("os.name").toLowerCase();  
		System.out.println(osName);  
		if (osName.indexOf("linux")>-1) {  
			font="/usr/share/fonts/simsun.ttc";  
		}  
		if(!new File(font).exists()){  
			throw new RuntimeException("字体文件不存在,影响导出pdf中文显示！"+font);  
		}  
		return font;  
	}  

}
