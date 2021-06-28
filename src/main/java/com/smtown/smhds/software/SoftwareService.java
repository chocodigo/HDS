package com.smtown.smhds.software;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smtown.smhds.util.PrintPage;

/**
 * <pre>
 *	SoftwareService.java - 자료실 게시판 서비스
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

@Service
public class SoftwareService {
    /*
     * 자료실 게시판 매퍼(Mapper) 클래스
     */
    @Autowired
    SoftwareMapper softwareMapper;

    //게시글 갯수
    public int softwareCountService(PrintPage printPage) throws Exception{
        return softwareMapper.softwareCount(printPage);
    }

    //게시글 목록
    public List<SoftwareVO> softwareListService(PrintPage printPage) throws Exception{
        return softwareMapper.softwareList(printPage);
    }

    //게시글 주키 생성 로직
    public String softwareSelectIdxxService() throws Exception{
        return softwareMapper.softwareSelectIdxx();
    }

    //게시글 등록
    public int softwareInsertService(SoftwareVO softwareVO) throws Exception{
        return softwareMapper.softwareInsert(softwareVO);
    }

    //게시글 상세
    public SoftwareVO softwareDetailService(String idxx_numb) throws Exception{
        return softwareMapper.softwareDetail(idxx_numb);
    }

    //게시글 삭제
    public int softwareDeleteService(String idxx_numb) throws Exception{
        return softwareMapper.softwareDelete(idxx_numb);
    }

    //게시글 수정
    public int softwareUpdateService(SoftwareVO softwareVO) throws Exception{
        return softwareMapper.softwareUpdate(softwareVO);
    }
}
