package com.mpri.aio.untils.licensefile.controller;

import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.untils.licensefile.model.LicenseFile;
import com.mpri.aio.untils.licensefile.service.LicenseFileService;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("licensefile")
public class LicenseFileController {

	@Autowired
	private LicenseFileService fileService;

	@Value("${file.uploadFolder}")
	private String uploadFolder;

	@Value("${clientlicense.schoolCode}")
	private String schoolCode;

	/**
	 * 上传接口
	 */
	@PostMapping("upload")
	public RestResponse<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
		LicenseFile returnFile = null;
		try {
			//获取上传文件基础参数
			String type=file.getContentType();
			byte[] byt = file.getBytes();
			long size=file.getSize();
			//转储为mongo对象
			LicenseFile f = new LicenseFile(file.getOriginalFilename(), type, size,new Binary(byt));
			returnFile = fileService.saveFile(f);
			return RestResponse.getInstance(ExceptionResult.REQUEST_SUCCESS, "上传成功", returnFile.getId());

		} catch (Exception ex) {
			return RestResponse.getInstance(ExceptionResult.SYS_ERROR, "上传失败", "");
		}

	}

	
	/**
	 * 分页查询文件
	 */
	@GetMapping("files/{pageIndex}/{pageSize}")
	public List<LicenseFile> listFilesByPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
		return fileService.listFilesByPage(pageIndex, pageSize);
	}

	/**
	 * 获取文件片信息
	 */
	@GetMapping("files/{id}")
	public ResponseEntity<Object> serveFile(@PathVariable String id) throws UnsupportedEncodingException {

		Optional<LicenseFile> file = fileService.getFileById(id);

		if (file.isPresent()) {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + new String(file.get().getName().getBytes("utf-8"),"ISO-8859-1"))
					.header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
					.header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
					.body(file.get().getContent().getData());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
		}

	}

	/**
	 * 上传
	 */
	@PostMapping("up")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		try {
			LicenseFile f = new LicenseFile(file.getOriginalFilename(), file.getContentType(), file.getSize(),
					new Binary(file.getBytes()));
			fileService.saveFile(f);
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("message", "Your " + file.getOriginalFilename() + " is wrong!");
			return "error";
		}
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");
		return "maybe success";
	}

	/**
	 * 在线显示文件
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/view/{id}")
	public ResponseEntity<Object> serveFileOnline(@PathVariable String id) {
		Optional<LicenseFile> file = fileService.getFileById(id);

		if (file.isPresent()) {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + file.get().getName() + "\"")
					.header(HttpHeaders.CONTENT_TYPE, file.get().getContentType())
					.header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
					.body(file.get().getContent().getData());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
		}

	}

	/**
	 * 删除文件
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteFile(@PathVariable String id) {

		try {
			fileService.removeFile(id);
			return ResponseEntity.status(HttpStatus.OK).body("DELETE Success!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	/**
	 * 客户端上传 公钥以及license文件
	 */
	@PostMapping("uploadLicense")
	public RestResponse<String> handleFileUploadLicense(@RequestParam("file") MultipartFile file) {

		String storePath = uploadFolder + "/licenseKeystoreClient/" + schoolCode ;
		String pubStore = storePath + "/" + file.getOriginalFilename();
		try {
			InputStream inputStream = file.getInputStream();
			judeDirExists(storePath);
			FileOutputStream fos = new FileOutputStream(pubStore);
			byte[] bytes = new byte[1024];
			int byteCount;
			while ((byteCount = inputStream.read(bytes)) != -1)
			{
				fos.write(bytes, 0, byteCount);
			}
			fos.close();
			inputStream.close();
			return RestResponse.getInstance(ExceptionResult.REQUEST_SUCCESS, "上传成功", "");
		} catch (Exception ex) {
			return RestResponse.getInstance(ExceptionResult.SYS_ERROR, "上传失败", "");
		}

	}

	/**
	 * 判断文件夹是否存在
	 */
	public static void judeDirExists(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			if (file.isDirectory()) {
				System.out.println("dir exists");
			} else {
				System.out.println("the same name file exists, can not create dir");
			}
		} else {
			System.out.println("dir not exists, create it ...");
			file.mkdirs();
		}

	}
}
