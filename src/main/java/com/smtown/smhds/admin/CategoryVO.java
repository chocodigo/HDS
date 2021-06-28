package com.smtown.smhds.admin;

public class CategoryVO {
    private String comm_code;   //카테고리 코드
    private String code_name;   //카테고리 이름
    private String admi_mail;   //카테고리 담당자 메일

    public String getComm_code() {
        return comm_code;
    }

    public void setComm_code(String comm_code) {
        this.comm_code = comm_code;
    }

    public String getCode_name() {
        return code_name;
    }

    public void setCode_name(String code_name) {
        this.code_name = code_name;
    }

    public String getAdmi_mail() {
        return admi_mail;
    }

    public void setAdmi_mail(String admi_mail) {
        this.admi_mail = admi_mail;
    }
}
