package com.lida.road.entity;

/***
 * 附件管理
 * 
 * @author linnt
 * @date 2015年1月11日
 */
public class AffixFile implements java.io.Serializable {

	private static final long serialVersionUID = 7623858759734661747L;
	public static String SYSTEM_UPLOAD_DIR = "/base_upload";
	/** id */
	private String id;
	/** 业务记录的id */
	private String bizId;
	/** 文件相对路径 */
	private String path;
	/** 文件名 */
	private String fileName;
	/** 起始桩号 */
	private long fileSize = 0;
	/** 结束桩号 */
	private String uploadUser;
	private String uploadTime;

	public AffixFile() {
	}

	public AffixFile(String path) {
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getUploadUser() {
		return uploadUser;
	}

	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getEncodingFileName() {
		return StrTool.transChs2Iso(fileName);
	}
}
