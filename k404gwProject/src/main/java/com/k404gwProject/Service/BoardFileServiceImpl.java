package com.k404gwProject.Service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.k404gwProject.Entity.QnaBoardFile;
import com.k404gwProject.Repository.QnaBoardFileRepository;

@Service
@Transactional
public class BoardFileServiceImpl implements BoardFileService {
	
	@Autowired
	FileService fileService;
	
	@Autowired
	QnaBoardFileRepository qnaBoardFileRepository;
	
	public void saveItem (QnaBoardFile qnaBoardFile, MultipartFile file, HttpServletRequest request) {
		
		String oriFileName = "";
		String fileName = "";
		String fileUrl = "";
		
		// 파일이 있다면
		if (file != null) {
			oriFileName = file.getOriginalFilename();
			fileName = fileService.getUuidFileName(oriFileName);
			fileUrl = fileService.getRootPath(oriFileName, request);
			
	}
		qnaBoardFile.updateFile(oriFileName, fileName, fileUrl);
		qnaBoardFileRepository.save(qnaBoardFile);
}
}