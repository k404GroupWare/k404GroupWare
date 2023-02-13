package com.k404gwProject.Dto;

import com.k404gwProject.Entity.QnaBoardFile;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QnaBoardFileDto {
	private Long id;
	
	private String fileName;
	
	private String oriFileName;
	
	private String fileUrl; 
	
	public static QnaBoardFileDto toQnaBoardFileDto(QnaBoardFile qnaBoardFile) {
		QnaBoardFileDto qnaBoardFileDto = new QnaBoardFileDto();
		qnaBoardFileDto.setFileName(qnaBoardFile.getFileName());
		qnaBoardFileDto.setFileUrl(qnaBoardFile.getFileUrl());
		qnaBoardFileDto.setOriFileName(qnaBoardFile.getOriFileName());
		return qnaBoardFileDto;
	}
}
