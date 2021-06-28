package com.smtown.smhds.account;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
/**
 * <pre>
 *	Account.java - 계정VO
 * </pre>
 *
 * @author	최해림
 * @since	2019.05.21
 * @version	1.0
 *
 * <pre>
 * == Modification Information ==
 * Date		Modifier		Comment
 * ====================================================
 * 2019.05.21	최해림		Initial Created.
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */
public class Account implements UserDetails{
    private String user_name;	//사용자 ID
    private String pass_word;	//사용자 비밀번호
    private boolean enab_ledd;	//사용가능한 계정인지 여부
    private Collection<? extends GrantedAuthority> authorities;		//권한 목록
    private String phon_enum;	//사용자 번호
    private String user_mail;	//사용자 메일
    private String real_name;	//사용자 실제 이름
    private String dept_ment;	//사용자 부서
    private String comm_pany;	//사용자 회사
    private String posi_tion;	//사용자 직책

    public String getComm_pany() {
        return comm_pany;
    }

    public void setComm_pany(String comm_pany) {
        this.comm_pany = comm_pany;
    }

    public String getPosi_tion() {
        return posi_tion;
    }

    public void setPosi_tion(String posi_tion) {
        this.posi_tion = posi_tion;
    }

    public String getPhon_enum() {
		return phon_enum;
	}

	public void setPhon_enum(String phon_enum) {
		this.phon_enum = phon_enum;
	}

	public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getDept_ment() {
        return dept_ment;
    }

    public void setDept_ment(String dept_ment) {
        this.dept_ment = dept_ment;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setPass_word(String pass_word) {
        this.pass_word = pass_word;
    }

    public void setEnabl_ledd(boolean enab_ledd) {
        this.enab_ledd = enab_ledd;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return authorities;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return pass_word;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return user_name;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return enab_ledd;
    }

}
