package itext_html2pdf;

import com.itextpdf.text.Font;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;

	/**  
	 * 重写 字符设置方法，解决中文乱码问题  
	 *   
	 */  
	public  class MyFontsProvider extends XMLWorkerFontProvider {  

		public MyFontsProvider(){  
			super(null, null);  
		}  

		@Override  
		public Font getFont(final String fontname, String encoding, float size, final int style) {  
			String fntname = fontname;  
			if (fntname == null) {  
				// fntname = "宋体";  
				fntname = "C:/Windows/Fonts/simsun.ttc";  
			}  
			if (size == 0) {  
				size = 4;  
			}  
			return super.getFont(fntname, encoding, size, style);  
		}  
	}  