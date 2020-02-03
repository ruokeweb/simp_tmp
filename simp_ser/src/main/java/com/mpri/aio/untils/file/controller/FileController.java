package com.mpri.aio.untils.file.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.utils.MD5Util;
import com.mpri.aio.untils.file.model.File;
import com.mpri.aio.untils.file.service.FileService;

import net.coobird.thumbnailator.Thumbnails;

@CrossOrigin // 允许所有域名访问
@RestController
@RequestMapping("file")
public class FileController {

	@Autowired
	private FileService fileService;
	
	
	/**
	 * 上传接口
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping("upload")
	public RestResponse<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
		File returnFile = null;
		try {
			//获取上传文件基础参数
			String type=file.getContentType();
			byte[] byt = file.getBytes();
			long size=file.getSize();
			String md5=MD5Util.getMD5(file.getInputStream());
			//识别常用图片类型并压缩
			if(type.equals("image/jpeg")||type.equals("image/gif")) {
				
				InputStream ins = file.getInputStream();
				BufferedImage image = Thumbnails.of(ins).scale(1f).outputFormat("jpeg").outputQuality(1f).asBufferedImage();
		        ByteArrayOutputStream os = new ByteArrayOutputStream();
		        ImageIO.write(image, "jpeg", os);
		        InputStream is = new ByteArrayInputStream(os.toByteArray());
		        size=is.available();
		        md5=MD5Util.getMD5(is);
		        byt = os.toByteArray();
			}
			//转储为mongo对象
			File f = new File(file.getOriginalFilename(), type, size,new Binary(byt));
			f.setMd5(md5);
			returnFile = fileService.saveFile(f);
			return RestResponse.getInstance(ExceptionResult.REQUEST_SUCCESS, "上传成功", returnFile.getId());

		} catch (IOException | NoSuchAlgorithmException ex) {
			// ex.printStackTrace();
			return RestResponse.getInstance(ExceptionResult.SYS_ERROR, "上传失败", "");
		}

	}
	
	/**
	 * 在线显示文件
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/view/{id}")
	public ResponseEntity<Object> serveFileOnline(@PathVariable String id) {
		Optional<File> file = fileService.getFileById(id);

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
	 * 在线显示压缩文件
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/viewThumb/{id}")
	public ResponseEntity<Object> serverThumbImage(@PathVariable String id) {

		Optional<File> file = fileService.getFileById(id);

		if (file.isPresent()) {
			byte[] data=file.get().getContent().getData();
			//图片压缩缩略图
			if(file.get().getContentType().equals("image/jpeg")||file.get().getContentType().equals("image/gif")||file.get().getContentType().equals("image/png")) {
				InputStream ins = new ByteArrayInputStream(data); 
				
				try {
					BufferedImage image = Thumbnails.of(ins).scale(1f).asBufferedImage();
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					ImageIO.write(image, "jpeg", os);
					data=os.toByteArray();
				} catch (IOException e) {
//					e.printStackTrace();
				}
				
			}
			
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + file.get().getName() + "\"")
					.header(HttpHeaders.CONTENT_TYPE, file.get().getContentType())
					.header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
					.body(data);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
		}

	}
	
	
	/**
	 * 分页查询文件
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@GetMapping("files/{pageIndex}/{pageSize}")
	public List<File> listFilesByPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
		return fileService.listFilesByPage(pageIndex, pageSize);
	}

	/**
	 * 获取文件片信息
	 * 
	 * @param id
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@GetMapping("files/{id}")
	public ResponseEntity<Object> serveFile(@PathVariable String id) throws UnsupportedEncodingException {

		Optional<File> file = fileService.getFileById(id);

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
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("up")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		try {
			File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(),
					new Binary(file.getBytes()));
			f.setMd5(MD5Util.getMD5(file.getInputStream()));
			fileService.saveFile(f);
		} catch (IOException | NoSuchAlgorithmException ex) {
			// ex.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "Your " + file.getOriginalFilename() + " is wrong!");
			return "error";
			//return "redirect:/";
		}

		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");
		return "maybe success";
		//return "redirect:/";
	}

	
	/**
	 * 上传
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("fileUpload")
	public RestResponse<File> fileUpload(@RequestParam("file") MultipartFile file) {
		System.err.println("执行上传");
		try {
			File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(),
					new Binary(file.getBytes()));
			f.setMd5(MD5Util.getMD5(file.getInputStream()));
			File resFile = fileService.saveFile(f);
			File res = new File();
			res.setId(resFile.getId());
			res.setName(resFile.getName());
			return new RestResponse<File>(ExceptionResult.REQUEST_SUCCESS, "上传成功！", res);
		} catch (IOException | NoSuchAlgorithmException ex) {
			File erro = new File();
			return new RestResponse<File>(ExceptionResult.REQUEST_SUCCESS, "上传成功！", erro);
		}
	}	
	


	/**
	 * 删除文件
	 * 
	 * @param id
	 * @return
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
}
