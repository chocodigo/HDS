package com.smtown.smhds.software;

import java.util.List;

import com.smtown.smhds.util.PrintPage;

/**
 * <pre>
 *	SoftwareMapper.java - FAQ 매퍼
 * </pre>
 *
 * @author	최해림
 * @since	2019.07.23
 * @version	1.0
 *
 * <pre>
 * == Modification Information ==
 * Date		Modifier		Comment
 * ====================================================
 * 2019.07.23	최해림		Initial Created.
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */

public interface SoftwareMapper {

    public int softwareCount(PrintPage printPage) throws Exception;  //게시글 갯수

    public List<SoftwareVO> softwareList(PrintPage printPage) throws Exception; //게시글 목록

    public String softwareSelectIdxx() throws Exception; //게시글 주키 생성

    public int softwareInsert(SoftwareVO softwareVO) throws Exception;      //게시글 등록

    public SoftwareVO softwareDetail(String idxx_numb) throws Exception;    //게시글 상세

    public int softwareDelete(String idxx_numb) throws Exception;       //게시글 삭제

    public int softwareUpdate(SoftwareVO softwareVO) throws Exception;  //게시글 수정
}
