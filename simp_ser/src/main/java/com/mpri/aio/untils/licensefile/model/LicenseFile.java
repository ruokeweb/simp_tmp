package com.mpri.aio.untils.licensefile.model;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "licensefile")
public class LicenseFile{
	@Id
	private String id;
	private String name; // 文件名称
	private String contentType; // 文件类型
	private long size;
	private Date uploadDate;
	private Binary content; // 文件内容
	private String path; // 文件路径

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Binary getContent() {
		return content;
	}

	public void setContent(Binary content) {
		this.content = content;
	}

	public LicenseFile() {
	}

	public LicenseFile(String name, String contentType, long size, Binary content) {
		this.name = name;
		this.contentType = contentType;
		this.size = size;
		this.uploadDate = new Date();
		this.content = content;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		LicenseFile fileInfo = (LicenseFile) object;
		return java.util.Objects.equals(size, fileInfo.size) && java.util.Objects.equals(name, fileInfo.name)
				&& java.util.Objects.equals(contentType, fileInfo.contentType)
				&& java.util.Objects.equals(uploadDate, fileInfo.uploadDate)
				&& java.util.Objects.equals(id, fileInfo.id);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(name, contentType, size, uploadDate, id);
	}

	@Override
	public String toString() {
		return "File{" + "name='" + name + '\'' + ", contentType='" + contentType + '\'' + ", size=" + size
				+ ", uploadDate=" + uploadDate + ", id='" + id + '\'' + '}';
	}

}
