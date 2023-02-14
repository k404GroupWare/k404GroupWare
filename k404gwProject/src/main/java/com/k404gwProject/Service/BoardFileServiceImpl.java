package com.k404gwProject.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.k404gwProject.Dto.QnaBoardFileDto;
import com.k404gwProject.Entity.Board;
import com.k404gwProject.Entity.QnaBoardFile;
import com.k404gwProject.Repository.QnaBoardFileRepository;

@Service
@Transactional
public class BoardFileServiceImpl implements BoardFileService {
	
	@Autowired
	FileService fileService;
	
	@Autowired
	QnaBoardFileRepository qnaBoardFileRepository;
	
	public List<QnaBoardFileDto> getBoardFile (Board qnaNo) {
		List<QnaBoardFile> qnaBoardFile = qnaBoardFileRepository.findByQnaNoOrderByIdAsc(qnaNo);
		List<QnaBoardFileDto> boardFileDtlTest = new ArrayList<QnaBoardFileDto>();
		QnaBoardFileDto boardFileDtl = new QnaBoardFileDto();
		for (QnaBoardFile boardFile : qnaBoardFile) {
			List<QnaBoardFileDto> boardFileDtlTest2 = new ArrayList<QnaBoardFileDto>();
			boardFileDtlTest2 = boardFileDtl.toQnaBoardFileDto(boardFile);
			boardFileDtlTest=boardFileDtlTest2;
			System.out.println("boardFileDtlTest 의 갯수 : " + boardFileDtlTest.size());
		}
		return boardFileDtlTest;
	}
}