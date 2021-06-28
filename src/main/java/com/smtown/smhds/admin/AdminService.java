package com.smtown.smhds.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminMapper adminMapper;

    @Autowired
    public AdminService(AdminMapper adminMapper){
        this.adminMapper = adminMapper;
    }

    //카테고리 리스트 받아오는 서비스
    public List<CategoryVO> getCategoryListService() throws Exception{
        return adminMapper.getCategoryList();
    }

    //카테고리 코드 중복체크 하는 서비스
    public int checkCommCodeService(String comm_code) throws Exception{
        return adminMapper.checkCommCode(comm_code);
    }

    //카테고리 등록하는 서비스
    public int insertCategory(CategoryVO categoryVO) throws Exception{
        return adminMapper.insertCategory(categoryVO);
    }

    //카테고리 상세조회 서비스
    public CategoryVO detailCategoryService(String comm_code) throws Exception{
        return adminMapper.detailCategory(comm_code);
    }

    //카테고리 수정 서비스
    public int updateCategoryService(CategoryVO categoryVO,String comm_code) throws Exception{
        return adminMapper.updateCategory(categoryVO, comm_code);
    }

    //카테고리 삭제 서비스
    public int deleteCategory(String comm_code) throws Exception{
        return adminMapper.deleteCategory(comm_code);
    }
}
