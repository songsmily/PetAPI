package com.songsmily.petapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.songsmily.petapi.dto.CodeMsg;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.service.UserService;
import com.songsmily.petapi.utils.VerifyCodeUtils;
import com.zhenzi.sms.ZhenziSmsClient;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Random;

@Controller
@RequestMapping("/regist")
@RestController
public class RegistController {
    @Value("${message.appUrl}")
    private String appUrl;
    @Value("${message.appId}")
    private String appId;
    @Value("${message.appSecret}")
    private String appSecret;
    @Value("${aliyun.oss.url}")
    private String ossUrl;

    @Autowired
    UserService userService;

    @RequestMapping("doRegist")
    public Result doRegist(@RequestBody User user){
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        user.setAccountType(0);
        user.setStatus(0);
        user.setAvatarUrl(ossUrl + "images/newuser.png");
        user.setPassword(new Md5Hash(user.getPassword(),null,3).toString());
        user.setAccountId(user.getPhone());
        user.setBio("签名是一种态度");
        if (userService.save(user)){
            return new Result(CodeMsg.SUCCESS);

        }else{
            return new Result(CodeMsg.SERVERERROR);
        }
    }
    /**
     * 发送短信验证码
     * @param memPhone
     * @param httpSession
     * @return
     */
    @RequestMapping("/sendCode")
    public Result sendCode(String memPhone, HttpSession httpSession){
        String code = String.valueOf(new Random().nextInt(999999));
        ZhenziSmsClient client = new ZhenziSmsClient(appUrl, appId, appSecret);
        JSONObject json = null;
        json = new JSONObject();
        json.put("memPhone",memPhone);
        json.put("code",code);
        json.put("createTime",System.currentTimeMillis());
        System.err.println("验证码为: "  + code);
        // 将认证码存入SESSION
        httpSession.setAttribute("MessageCode",json);
        return new Result(CodeMsg.SUCCESS);
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("message", "尊敬的宠物之家用户，您的验证码为：" + code + "，该验证码5分钟内有效，请勿泄露于他人。");
//        params.put("number", memPhone);
//        try {
//            JSONObject json = null;
//            String result = client.send(params);
//            json = JSONObject.parseObject(result);
//            if (json.getIntValue("code") != 0){
//                return new Result(CodeMsg.MESSAGESENDERROR);
//            }
//            json = new JSONObject();
//            json.put("memPhone",memPhone);
//            json.put("code",code);
//            json.put("createTime",System.currentTimeMillis());
//            // 将认证码存入SESSION
//            httpSession.setAttribute("MessageCode",json);
//        } catch (Exception e) {
//            return new Result(CodeMsg.SERVERERROR);
//        }
//        return new Result(CodeMsg.SUCCESS);
    }
    @RequestMapping("checkMessageCode")
    public Result checkMessageCode(Integer messageCode,HttpSession httpSession){
        if (httpSession.getAttribute("MessageCode") == null){
            return new Result(CodeMsg.CHECKERROR);
        }
        JSONObject json = (JSONObject) httpSession.getAttribute("MessageCode");
        if (messageCode == json.getIntValue("code")){
            return new Result(CodeMsg.SUCCESS);
        }else{
            return new Result(CodeMsg.CHECKERROR);
        }
    }


    @RequestMapping("checkImgYzm")
    public Result checkImgYzm(String imgYzm,HttpSession httpSession){
        System.err.println(httpSession.getAttribute("verCode"));
        System.err.println(imgYzm);
        if (imgYzm.equals(httpSession.getAttribute("verCode"))){
            return new Result(CodeMsg.SUCCESS);
        }else{
            return new Result(CodeMsg.CHECKERROR);
        }
    }
    @RequestMapping(value = "yzm")
    public void yzm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        // 存入会话session
        HttpSession session = request.getSession();
        // 删除以前的
        session.removeAttribute("verCode");
        session.removeAttribute("codeTime");
        session.setAttribute("verCode", verifyCode.toLowerCase());		//生成session
        session.setAttribute("codeTime", LocalDateTime.now());
        // 生成图片
        int w = 130, h = 40;
        OutputStream out = response.getOutputStream();
        VerifyCodeUtils.outputImage(w, h, out, verifyCode);

    }
    @RequestMapping("checkPhone")
    public Result checkPhone(String phone) throws InterruptedException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone",phone);
        if (userService.getOne(wrapper) == null){
            return new Result(CodeMsg.SUCCESS);
        }else{
            return new Result(CodeMsg.SERVERERROR);
        }
    }
    @RequestMapping("checkNickname")
    public Result checkNickname(String nickname) throws InterruptedException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name",nickname);
        if (userService.getOne(wrapper) == null){
            return new Result(CodeMsg.SUCCESS);
        }else{
            return new Result(CodeMsg.SERVERERROR);
        }
    }


}
