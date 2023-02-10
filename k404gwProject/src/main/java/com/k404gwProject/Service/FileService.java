package com.k404gwProject.Service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {
	// byte[] fileData
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID(); // UUID로 업로드 파일이름 중복방지
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // substring을 통해 확장자만 출력
        System.out.println("extension: " +extension);
        String savedFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        System.out.println("uploadPath: " +uploadPath); // 경로 콘솔확인
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl); // byte 단위로 해당 경로 출력하기 위한 객체
        System.out.println("fileUploadFullUrl: " +fileUploadFullUrl);
        fos.write(fileData); // byte[] fileData 형의 배열 사용, 메모리상에 해당 경로 출력
        fos.close(); // 자원반납
        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);
        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }

}