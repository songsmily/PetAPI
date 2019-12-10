package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (User)表实体类
 *
 * @author SongSmily
 * @since 2019-12-06 16:47:11
 */
@SuppressWarnings("serial")
public class User extends Model<User> {
    //唯一标识
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //账户id
    private String accountId;
    //用户名
    private String name;
    //密码
    private String password;
    //token值
    private String token;
    //创建时间
    private Long gmtCreate;
    //修改时间
    private Long gmtModified;
    //头像地址
    private String avatarUrl;
    //账户类型
    private Integer accountType;
    
    private String bio;
    //住址
    private String location;

    //角色信息
    @TableField(exist = false)

    private Roles role;


    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", accountId='" + accountId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", accountType=" + accountType +
                ", bio='" + bio + '\'' +
                ", location='" + location + '\'' +
                ", role=" + role +
                '}';
    }

    public void setLocation(String location) {
        this.location = location;
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
    }