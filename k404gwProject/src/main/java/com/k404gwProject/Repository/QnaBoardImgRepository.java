package com.k404gwProject.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.k404gwProject.Entity.QnaBoardImg;

@Repository
public interface QnaBoardImgRepository extends JpaRepository<QnaBoardImg, Long>{
//	List<QnaBoardImg> findByQnaNoOrderByIdAsc(Long qnaNo);
}
