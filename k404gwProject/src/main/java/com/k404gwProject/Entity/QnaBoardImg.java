package com.k404gwProject.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="qna_board_img")
@Getter @Setter
public class QnaBoardImg extends BaseEntity {
	@Id
	@Column(name="qna_img_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="qna_img_name")
    private String imgName; //이미지 파일명
	
	@Column(name="qna_img_oriName")
    private String oriImgName; //원본 이미지 파일명
	
	@Column(name="qna_img_url")
    private String imgUrl; //이미지 조회 경로
//	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="qnaNo")
//	private Board qnaNo;
}
