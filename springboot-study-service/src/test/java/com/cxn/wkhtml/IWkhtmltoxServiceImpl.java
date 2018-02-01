package com.cxn.wkhtml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class IWkhtmltoxServiceImpl implements IWkhtmltoxService {

	private static Log logger = LogFactory.getLog("System");
	// wkhtmltox在系统中的路径
	private static String wkhtmltox1 = ResourceBundle.getBundle("init")
			.getString("wkhtmltox-windows");
	private static String wkhtmltox2 = ResourceBundle.getBundle("init")
			.getString("wkhtmltox-linux");
	private static String destPath1 = ResourceBundle.getBundle("init")
			.getString("destPath-windows");
	private static String destPath2 = ResourceBundle.getBundle("init")
			.getString("destPath-linux");
	private static String pathRead = ResourceBundle.getBundle("init")
			.getString("www.picture.path.read");
	private static final String os = System.getProperty("os.name");
	private static String wkhtmltox = null;
	private static String dest = null;

	// flag=false代表window环境，flag=true代表linux环境
	private static boolean flag = false;
	static {
		if (os.toLowerCase().startsWith("win")) {
			wkhtmltox = wkhtmltox1;
			dest = destPath1;
		} else {
			wkhtmltox = wkhtmltox2;
			dest = destPath2;
			flag = true;
		}


	}

private  String  uuidDirName = null;
	@Override
	public TranscodeResult convertToX(Map<String, Object> map,
			WkhtmltoxEnum wkEnum) {
		return convertToX(map, wkEnum, false);
	}

	private TranscodeResult convert(Map<String, Object> map,
			WkhtmltoxEnum wkEnum) {

		// 转化类型：pdf 或 image
		logger.info("falg>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + flag + "");
		String type = wkEnum.getType();
		if (!flag) {
			type += ".exe";
		}
		TranscodeResult result = new TranscodeResult();
		WkhtmltoxEnum wk = null;

		String dirPath = dest + uuidDirName + File.separator + "html";
		logger.info(dirPath);
		File file = new File(dirPath);
		logger.info("file的绝对路径:" + file.getAbsolutePath());
		StringBuffer sb = new StringBuffer("");
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			for (File f : listFiles) {
				logger.info("f---->" + f.getAbsolutePath());
				if (f.isFile() && f.getName().contains(".html")) {
					// 1.指定源文件(html)路径
					String srcPath = f.getAbsolutePath();
					// 2.指定目标文件路径
					String destPath = null;
					String realPath = null;//域名访问路径
					if (WkhtmltoxEnum.PDF.getType().equals(wkEnum.getType())) {
						destPath = dest
								+ uuidDirName
								+ File.separator
								+ "pdf"
								+ File.separator
								+ f.getName().substring(0,
										f.getName().lastIndexOf(".") + 1)
								+ "pdf";
						realPath = pathRead+"/cashloan/"
								+ uuidDirName
								+ File.separator
								+ "pdf"
								+ File.separator
								+ f.getName().substring(0,
										f.getName().lastIndexOf(".") + 1)
								+ "pdf";
					} else {
						destPath = dest
								+ uuidDirName
								+ File.separator
								+ "jpg"
								+ File.separator
								+ f.getName().substring(0,
										f.getName().lastIndexOf(".") + 1)
								+ "jpg";
						realPath = pathRead+"/cashloan/"
								+ uuidDirName
								+ File.separator
								+ "jpg"
								+ File.separator
								+ f.getName().substring(0,
										f.getName().lastIndexOf(".") + 1)
								+ "jpg";
					}
					File destFile = new File(destPath);
					File parent = destFile.getParentFile();
					// 如果pdf保存路径不存在，则创建路径
					if (!parent.exists()) {
						parent.mkdirs();
					}
					// 3.拼接执行命令
					StringBuffer command = new StringBuffer();
					command.append(wkhtmltox + type);
					// 添加额外参数
					if (WkhtmltoxEnum.PDF.equals(wkEnum)) {
						// command.append(" --page-width 300 ");//裁剪像素宽度
						// append(" --page-height 400 ");//裁剪像素宽度
						// command.append(" --dpi 200"); //dpi设置
						// command.append(" --page-size A3");
						// command.append(" --header-line");//页眉下面的线
						// command.append(" --footer-line");//页脚上面的线
						// command.append(" --footer-right 页脚文本");
						// command.append(" --header-spacing 0");
						// command.append(" --footer-spacing 0");
						// command.append(" --disable-smart-shrinking");//禁止使用WebKit的智能战略收缩
						// command.append(" --margin-bottom 0 "); //设置页面下边距
						// (default 10mm)
						// command.append(" --margin-left 0"); //将左边页边距
						// (default 10mm)
						// command.append(" --margin-right 0"); //设置页面右边距
						// (default 10mm)
						// command.append(" --margin-top 0 "); //设置页面上边距
						// (default 10mm)
					} else if (WkhtmltoxEnum.JPG.equals(wkEnum)) {
						command.append("  --width 840 ");// 裁剪像素宽度
						// command.append("  --height 1188 ");//裁剪像素宽度
						command.append("  --quality 40 ");// 调整图片质量
					}
					command.append(" ");
					command.append(srcPath);
					command.append(" ");
					command.append(destPath);
					logger.info("命令command：>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
							+ command.toString());
					// 4.执行命令
					Process proc = null;
					try {
						proc = Runtime.getRuntime().exec(command.toString());
						proc.waitFor();
						wk = WkhtmltoxEnum.SUCCESS;
						sb.append(realPath + ";");
					} catch (Exception e) {
						logger.info("执行 wkhtmlto" + type + " 出现异常。");
						wk = WkhtmltoxEnum.FAIL;
						result.setDirPath("");
						// e.printStackTrace();
					} finally {
						logger.info("finally块执行了。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
						if (proc != null) {
							proc.destroy();
						}
					}

				}// end if
			}// end for loop
		}
		if (sb.length()>=0) {
			sb.setLength(sb.length() - 1);
		}
		result.setDirPath(sb.toString());
		result.setWk(wk);
		return result;
	}

	public static void main(String[] args) {
		Map<String, Object> param = new HashMap<String, Object>();
		 param.put("lender", "王朝"); // 甲方： 出借人
		param.put("borrower", "马汉"); // 乙方：借款人
		param.put("contractNo", "201711241441");// 合同编号
		param.put("goldUserName", "mahan"); // 金米袋用户名
		param.put("idCard", "232301189203033366");// 身份证号码
		param.put("contractMoney", "100000.00");// 合同金额
		param.put("loanPeriod", "24");// 借款期限
		param.put("repaymentPrincipalInterest", "100000.22");// 月偿还本息金额
		param.put("repaymentPlatformFees", "300.00");// 月付平台居间费
		param.put("annualRate", "5");// 年利率
		param.put("totalMonthlyRepayment", "5000.00");// 月还款合计金额
		param.put("imageFlag", true);

		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		for (int i = 0; i < 100; i++) {
			Map<String, String> map1 = new HashMap<String, String>();
			// 填充列表内容
			map1.put("userName", "userName" + i);// 金米袋用户名
			map1.put("loanPrincipal", "1000" + i);// 借款本金
			map1.put("loanLife", "loanLife" + i); // 借款期限
			map1.put("rate", "rate" + i);// 年利率
			map1.put("principalInterest", "100." + i);// 每月应收本息
			list.add(map1);
		}
		param.put("list", list);
		// new IWkhtmltoxServiceImpl().convertToX(param, WkhtmltoxEnum.PDF);
		// TranscodeResult result = new
		// IWkhtmltoxServiceImpl().convertToX(param, WkhtmltoxEnum.JPG);
		// System.out.println(new Gson().toJson(result));
		/*
		 * System.out.println(result.getReason());
		 * System.out.println(result.getDirPath());
		 */

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("borrower", "甄子丹");// 借款人
		map2.put("idCard", "232301199205025262");// 身份证号
		map2.put("loanPrincipal", "100000.00");// 借款本金
		map2.put("loanLife", "24");// 借款期限
		map2.put("startDate", "2017年11月20日");// 借款期限的开始日期
		map2.put("endDate", "2018年11月20日");// 借款期限的结束日期
		map2.put("startTime", "aaa");// 每期开始时间
		map2.put("endTime", "aaa");// 每期结束时间
		map2.put("perTotal", "100000.00");// 总计
		map2.put("perPrincipal", "10000.00"); // 本金imageUrl
		map2.put("perInterest", "360.00");// 利息
		map2.put("lastRepaymentDay", "24");// 最后还款日

		map2.put("total", "100000.00");// 合计中的总计
		map2.put("totalPrincipal", "10000.00");// 合计本金
		map2.put("totalInterest", "360.00");// 合计利息
		map2.put("totalRepaymentDay", "24");// 合计最后还款日
		long s = System.currentTimeMillis();
		TranscodeResult result1 = new IWkhtmltoxServiceImpl().convertToX(param,
				WkhtmltoxEnum.JPG);
		long e = System.currentTimeMillis();
		System.out.println((e-s));
		System.out.println(result1.getWk().getType());
		System.out.println(result1.getReason());
		System.out.println(result1.getDirPath());

	}

	/**
	 * 取出map中的参数，结合velocity模板引擎文件，生成html，存放在指定路径下
	 * 
	 * @param map
	 */
	private WkhtmltoxEnum generateHtml(Map<String, Object> param,
			boolean excelFlag) {

		// 初始化模板引擎
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		Writer writer = null;
		WkhtmltoxEnum wk = null;
		try {
			ve.init();
			if (excelFlag) {
				// 获取模板文件
				Template t = null;
				try {
					t = ve.getTemplate("template/excel.vm", "UTF-8");
				} catch (Exception e) {
					throw new RuntimeException("选择模板文件" + "template/excel.vm"
							+ "不存在！");
				}
				// 设置变量
				VelocityContext context = new VelocityContext();
				Set<Entry<String, Object>> entrySet = param.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					context.put(entry.getKey(), entry.getValue());
				}
				// 将生成的html存放到指定路径：linux:/tmp/prefix/uuidDirName/html/pagei.html
				// windows: D:\\prefix\\uuidDirName\\html\\xxx.html
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + dest);
				String destPath = dest + uuidDirName + File.separator + "html"
						+ File.separator + "excel.html";
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + destPath);
				File file = new File(destPath);
				File parent = file.getParentFile();
				// 如果pdf保存路径不存在，则创建路径
				if (!parent.exists()) {
					parent.mkdirs();
				}
				writer = new FileWriter(file);
				t.merge(context, writer);
				writer.flush();
			} else {
					// 获取模板文件
					Template t = null;
					try {
						t = ve.getTemplate("template/loanAgreement.vm", "UTF-8");
					} catch (Exception e) {
						logger.error("选择模板文件" + "template/loanAgreement.vm"
								+ "不存在！");
					}
					// 设置变量
					VelocityContext context = new VelocityContext();
					Set<Entry<String, Object>> entrySet = param.entrySet();
					for (Entry<String, Object> entry : entrySet) {
						context.put(entry.getKey(), entry.getValue());
					}
					// 将生成的html存放到指定路径：linux:/tmp/prefix/uuidDirName/html/pagei.html
					// windows: D:\\prefix\\uuidDirName\\html\\xxx.html
					String destPath = dest + uuidDirName + File.separator
							+ "docx" + File.separator + "loanAgreement.docx";
					File file = new File(destPath);
					File parent = file.getParentFile();
					// 如果pdf保存路径不存在，则创建路径
					if (!parent.exists()) {
						parent.mkdirs();
					}
					writer = new FileWriter(file);
					t.merge(context, writer);
					writer.flush();

			}// end if-else
			wk = WkhtmltoxEnum.SUCCESS;

		} catch (Exception e) {
			logger.error("生成html错误，错误信息为：" + e.getMessage());
			throw new RuntimeException("生成html错误，错误信息为：" + e.getMessage());
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				logger.error("关闭字符流错误，错误信息为：" + e.getMessage());
				e.printStackTrace();
			}
		}

		return wk;
	}

	@Override
	public TranscodeResult convertToX(Map<String, Object> map,
			WkhtmltoxEnum wkEnum, boolean excelFlag) {
		// uuidDirName = FileUtils.generateUUIDName(dest);
		uuidDirName = UUID.randomUUID().toString();
		System.out.println(uuidDirName);
		TranscodeResult result = new TranscodeResult();
		try {
			// 参数校验
			if (WkhtmltoxEnum.FAIL.equals(wkEnum)
					|| WkhtmltoxEnum.SUCCESS.equals(wkEnum)) {

				throw new RuntimeException("方法转换类型的参数错误！");
			}
			// 根据模板引擎 + 参数map 生成html文件
			WkhtmltoxEnum resultEnum = generateHtml(map, excelFlag);

			if (WkhtmltoxEnum.FAIL.equals(resultEnum)) {
				// 抛出异常，等待处理
				throw new RuntimeException("生成html文件发生异常，详细错误请查看日志。");
			}
			result.setDirPath("");
			result.setWk(WkhtmltoxEnum.SUCCESS);
			// 执行转换功能
			result = convert(map, wkEnum);
			WkhtmltoxEnum convertEnum = result.getWk();
			if (WkhtmltoxEnum.FAIL.equals(convertEnum)) {
				// 抛出异常，等待处理
				throw new RuntimeException("转换" + wkEnum.getType()
						+ "文件发生异常，详细错误请查看日志。");
			}
		} catch (Exception e) {
			result.setReason(e.getMessage());
			result.setWk(WkhtmltoxEnum.FAIL);
		}
		return result;

	}

}