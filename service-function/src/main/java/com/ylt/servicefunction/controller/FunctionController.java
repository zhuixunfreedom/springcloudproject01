package com.ylt.servicefunction.controller;

import com.ylt.servicefunction.bean.PersonBean;
import com.ylt.servicefunction.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


@RestController
@RequestMapping("/functions")
public class FunctionController {

    private static Map<String,String> map = new HashMap<>();


    private AsyncDel asyncDel;
    @Autowired
    public void setAsyncDel(AsyncDel asyncDel) {
        this.asyncDel = asyncDel;
    }

    @RequestMapping(value = "/sms", method = RequestMethod.POST)
    public String sms(@RequestParam(name = "phone") String phone,
                         @RequestParam(name = "code") String code) {
        String s;
        try {
            s = map.get(phone);
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
        if ( s.equals(code)) return "success";
        else return "failure";
    }
    @RequestMapping(value = "/getcode", method = RequestMethod.POST)
    public String code(@RequestParam(name = "phone") String phone) {
        if (!isChinaMobile(phone)) return "failure";
        String code = getCode();
        map.put(phone,code);
        asyncDel.delMap(map,phone);
        try {
            SendSms.send(phone,code);
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
        return "success";
    }
    private static String imageCode = "";
    @RequestMapping(value = "/getImageCode", method = RequestMethod.POST)
    public String getImgCode() {
        VerifyUtil.Validate randomCode = VerifyUtil.getRandomCode();
        imageCode = randomCode.getValue();
        return randomCode.getBase64Str();
    }
    @RequestMapping(value = "/verificationImageCode", method = RequestMethod.POST)
    public String verificationImgCode(@RequestParam("imgCode") String imgCode) {
//        System.out.println(imageCode);
//        System.out.println(imgCode);
        if (imageCode.equals(imgCode)) return "success";
        else return "failure";
    }

    private SendEmail sendEmail;
    @Autowired
    public void setSendEmail(SendEmail sendEmail) {
        this.sendEmail = sendEmail;
    }
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    public String sendEmail(@RequestParam("email") String email,
                            @RequestParam("emailHeader") String emailHeader,
                            @RequestParam("emailBody") String emailBody,
                            @RequestParam("htmlStatus") String emailStatus) {
        try {
            sendEmail.sendComplex(email,emailHeader,emailBody,Boolean.valueOf(emailStatus));
        } catch (MessagingException e) {
            e.printStackTrace();
            return "failure";
        }
        return "success";
    }




    private PersonBean personMsg;

    public void setPersonMsg(PersonBean personMsg) {
        this.personMsg = personMsg;
    }

    @Async
    @RequestMapping(value = "/uploadIdCard", method = RequestMethod.POST)
    public String upload(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("idCard");
        assert file != null;
        String fileName = file.getOriginalFilename();
        String path = new File("").getCanonicalPath()+
                "\\service-function\\src\\main\\resources\\static\\" +
                fileName;
        File newFile = new File(path);
        //通过MultipartFile的方法直接写文件
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
            return "failure";
        }
        personMsg = IDCardVerify.getIDCardmsg(newFile);
        return "success";
    }
    @RequestMapping(value = "/getIdCardMsg", method = RequestMethod.POST)
    public PersonBean getIdCardMsg(){
        return personMsg;
    }

    @Async
    @RequestMapping(value = "/verificationIDCard", method = RequestMethod.POST)
    public String verificationIDCard(@RequestParam("name") String name,
                                     @RequestParam("id") String id) throws Exception {

        String authentication = IDCardVerify.Authentication(name, id);
        System.out.println(authentication);
        return authentication;
    }








    /**
     * @return 随机生成的6位短信验证码
     */
    private static String getCode() {
        StringBuilder sb = new StringBuilder("0");
        for (int i = 1; i < 6; i++) {
            int num = new Random().nextInt(10);
            sb.append(String.valueOf(num));
        }
        return sb.toString();
    }


    /**
     *
     * @param mobile 手机号
     * @return
     * @throws PatternSyntaxException
     * 正则表达式判断是否是手机号
     */
    public static boolean isChinaMobile(String mobile) throws PatternSyntaxException {
        if(mobile == null){
            return false;
        }
        if(mobile.length() != 11){
            return false;
        }

        /**
         * "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9                            中的任意一位,
         * [^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
         */
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }


}