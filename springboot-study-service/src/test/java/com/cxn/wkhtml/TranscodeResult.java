package com.cxn.wkhtml;

/**
 * 转码结果
 * @author caoxunan
 *
 */
public class TranscodeResult {
	// 文件存放目录
	private String dirPath;
	// 报错原因
	private String reason;
	// 转码结果
	private WkhtmltoxEnum wk;
	
	public String getDirPath() {
		return dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	public WkhtmltoxEnum getWk() {
		return wk;
	}

	public void setWk(WkhtmltoxEnum wk) {
		this.wk = wk;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "TranscodeResult [dirPath=" + dirPath + ", reason=" + reason + ", wk=" + wk + "]";
	}
	
}
