package com.cxn.swt;

import org.junit.Test;

import com.cxn.common.utils.PrintScreen4DJNativeSwingUtils;

public class SwtGenerateImgTest {

	@Test
	public void testDJNativeSwing(){
		
		// 1.有4个参数：图片保存路径，要截图的URL地址，裁剪图片的宽，裁剪图片的高
		// 2.经测试依然存在的问题：最好直接指定html的长和宽，然后再在代码逻辑里面去按照坐标去截取相应位置的图片。
		// 3.使用debug模式得到的echarts报表图片可以是完整的，但是正常执行的话可能报表显示的不完全，需要进行调试，然后再对应的位置让线程休眠一下，使其内容加载完成
		PrintScreen4DJNativeSwingUtils.printUrlScreen2jpg("d:/hello-world.png", "http://echarts.baidu.com/echarts2/doc/example/line1.html", 1903, 787);
		
	}
	
}
