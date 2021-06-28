package com.smtown.smhds.util;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;
/**
 * <pre>
 *	FileUtil.java - 파일 관련 클래스
 * </pre>
 *
 * @author	최해림
 * @since	2019.06.17
 * @version	1.0
 *
 * <pre>
 * == Modification Information ==
 * Date		Modifier		Comment
 * ====================================================
 * 2019.06.17	최해림		Initial Created.
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */
public class FileUtil {
	/*
	 * 파일 업로드
	 * @param idxx_numb - 게시글의 주키
	 *		  files - 첨부파일
	 * @return FileVO
	 * @exception Exception - 파일업로드시 발생한 예외
	 */
	public FileVO uploadFile(String idxx_numb, MultipartFile files) throws Exception{
		FileVO file = new FileVO();

		String fileName = files.getOriginalFilename();	//파일 원본 이름
		String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();	//파일 확장자
		File destinationFile;			//업로드할 파일객체
		String destinationFileName;		//rename한 파일명

		String OS = System.getProperty("os.name").toLowerCase();	//운영체제 이름 받아오기
		String fileUrl="";	//파일 경로 설정
		//권한을 줄 수 있음(해야함)
		if(OS.indexOf("win") >= 0) 		//운영체제가 윈도우일 경우
			fileUrl = "C:"+File.separator+"workspace"+File.separator+"smHDS"+File.separator+"src"+File.separator+"main"+File.separator+"webapp"+File.separator+"resources"+File.separator+"img"+File.separator;
		else if(OS.indexOf("mac") >= 0) {    //운영체제가 MAC일 경우
			fileUrl = "/Users/cocoa1149/upload/";
		}

		destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;		//중복된 파일명을 방지하기 위해 파일명 rename
		destinationFile = new File(fileUrl + destinationFileName);								//파일 객체 생성

		boolean isDirectoryCreated = destinationFile.getParentFile().exists() || destinationFile.getParentFile().mkdirs();		//업로드할 디렉토리가 생성돼있는지 확인 후 없으면 생성
		files.transferTo(destinationFile);		//업로드한 파일 데이터를 지정한 파일에 저장

		file.setIdxx_numb(idxx_numb);	//파일이 등록될 글의 idxx_numb 설정
		file.setFile_name(destinationFileName);		//파일이 등록될 글의 rename 파일명 설정
		file.setFile_orig(fileName);				//파일 이름 설정
		file.setFile_urll(fileUrl);					//파일 url 설정

		return file;
	}
}