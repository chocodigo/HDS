package com.smtown.smhds.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * <pre>
 *	MailUtil.java - 메일 전송기능을 위한 클래스
 * </pre>
 *
 * @author	최해림
 * @since	2019.06.25
 * @version	1.0
 *
 * <pre>
 * == Modification Information ==
 * Date		Modifier		Comment
 * ====================================================
 * 2019.06.25	최해림		Initial Created.
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */
public class MailUtil {

    private final MailSender sender;  //메일 발송 기능을 위한 sender 객체

    @Autowired
    public MailUtil(MailSender sender){
        this.sender = sender;
    }

    /*
     * 메일 전송 함수
     * @param   from - 보내는 사람
     *          to - 받는 사람
     *          subject - 제목
     *          mailMsg - 내용
     * @return
     * @exception
     */
    public void sendMail(String from,String to,String subject,String mailMsg){
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setFrom(from);          //보내는 사람 설정
        msg.setTo(to);              //받는 사람 설정
        msg.setSubject(subject);    //제목 설정
        msg.setText(mailMsg);       //메 내용 설정

        this.sender.send(msg);
    }
}
