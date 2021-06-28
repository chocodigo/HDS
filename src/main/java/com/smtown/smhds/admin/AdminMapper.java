package com.smtown.smhds.admin;

import java.util.List;

public interface AdminMapper {
    public List<CategoryVO> getCategoryList() throws Exception;     //카테고리 리스트 받아오기

    public int checkCommCode(String comm_code) throws Exception;    //카테고리 코드 중복체크

    public int insertCategory(CategoryVO categoryVO) throws Exception;      //카테고리 등록 쿼리

    public CategoryVO detailCategory(String comm_code) throws Exception;    //카테고리 상세

    public int updateCategory(CategoryVO categoryVO, String comm_code) throws Exception;     //카테고리 수정

    public int deleteCategory(String comm_code) throws Exception;       //카테고리 삭제
}
