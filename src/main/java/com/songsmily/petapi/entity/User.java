package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.crazycake.shiro.AuthCachePrincipal;

import java.io.Serializable;

/**
 * (User)表实体类
 *
 * @author SongSmily
 * @since 2019-12-18 06:53:52
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends Model<User> implements Serializable, AuthCachePrincipal {

    private static final long serialVersionUID = 4297464181093070302L;

    //唯一标识
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //账户id
    private String accountId;
    //用户名
    private String name;
    //电话号码
    private String phone;
    //真实姓名
    private String realName;
    //地区
    private String location;
    //具体地址
    private String addr;
    //邮箱
    private String email;
    //密码
    private String password;
    //创建时间
    private Long gmtCreate;
    //修改时间
    private Long gmtModified;
    //头像地址
    private String avatarUrl;
    //账户类型 0 注册账户  1 github注册账户  2 qq注册账户
    private Integer accountType;
    //账户状态
    private Integer status;
    //个性签名
    private String bio;
    //信息版本
    private Integer version;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", accountId='" + accountId + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", realName='" + realName + '\'' +
                ", location='" + location + '\'' +
                ", addr='" + addr + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", accountType=" + accountType +
                ", status=" + status +
                ", bio='" + bio + '\'' +
                ", version=" + version +
                '}';
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    @Override
    public String getAuthCacheKey() {
        return null;
    }
    }