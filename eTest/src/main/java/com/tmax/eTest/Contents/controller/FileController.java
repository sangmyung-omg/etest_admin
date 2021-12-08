package com.tmax.eTest.Contents.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.tmax.eTest.Contents.exception.ContentsException;
import com.tmax.eTest.Contents.exception.ErrorCode;
import com.tmax.eTest.Contents.util.CommonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FileController {

	@Value("${file.path}")
	private String DEFAULT_PATH;

	@Autowired
	private CommonUtils commonUtils;

	private static final String CONTETNS_PATH = "contents" + File.separator;

	private static final String VIDEO_PATH = "videos" + File.separator;

	public enum FileType {
		VIDEO
	}

	@GetMapping("/file/**")
	public void download(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		String originalFilename = request.getRequestURI().split(request.getContextPath() + "/file/")[1];
		String filename = URLDecoder.decode(originalFilename, "UTF-8");
		String filePath = DEFAULT_PATH + CONTETNS_PATH + filename;

		log.info("File Download: " + filePath);

		File downloadFile = new File(filePath);
		if (!downloadFile.exists())
			throw new ContentsException(ErrorCode.FILE_ERROR, filePath);

		int fileSize = (int) downloadFile.length();
		log.info("File Size: " + fileSize);

		String mimeType = request.getServletContext().getMimeType(filePath);
		if (commonUtils.stringNullCheck(mimeType))
			mimeType = "application/octet-stream";
		log.info("MimeType: " + mimeType);

		response.setContentType(mimeType);
		response.setContentLength(fileSize);
		// response.setHeader("Content-Disposition", "attachment; filename=" +
		// filename);

		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(downloadFile);
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			throw new ContentsException(ErrorCode.FILE_ERROR, filePath);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				log.error("File InputStream Close Error.");
			}
		}
	}

	@PostMapping("/file")
	public ResponseEntity<Object> upload(@Valid @RequestParam("type") String type,
			@Valid @RequestParam("file") MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			throw new ContentsException(ErrorCode.FILE_ERROR, "EMPTY");
		}

		String uploadPath = DEFAULT_PATH + CONTETNS_PATH;
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		String ret = "";
		if (type.equals(FileType.VIDEO.name())) {
			uploadPath += VIDEO_PATH + file.getOriginalFilename();
			ret = VIDEO_PATH + file.getOriginalFilename();
		}
		// else

		log.info(uploadPath);
		// File uploadFile = new File(uploadPath);
		// file.transferTo(uploadFile);
		File uploadFile = new File(uploadPath);
		try {
			if (uploadFile.createNewFile()) {
				try (FileOutputStream fos = new FileOutputStream(uploadFile)) {
					fos.write(file.getBytes());
				}
			}
		} catch (IOException e) {
			throw new ContentsException(ErrorCode.FILE_ERROR);
		}

		return new ResponseEntity<>(ret, HttpStatus.OK);
	}
}
