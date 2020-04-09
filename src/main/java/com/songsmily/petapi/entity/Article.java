package com.songsmily.petapi.entity;

import com.songsmily.petapi.utils.BeanUtils;
import org.apache.commons.lang3.BooleanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Article implements Serializable {
    private static final long serialVersionUID = -2440471074054288487L;

    private Long id;

    private String title;

    private String author;
    private Date issueTime;
    private Boolean istop;
    private Date created;
    private Date modified;
    private Long createUserId;
    private String createUserName;
    private Integer status; //0:待发布, 1:已发布,  2:已删除
    private String content;
    private Integer readCount;
    private Integer likeCount;
    private Integer commentCount;
    private String headImgUrl;  //标题图片
    private Boolean allowComment;
    private Boolean allowShare;
    private boolean userLike;   //用户是否已点赞，仅用于前端显示

    public Map<String, Object> toMap(){
        return BeanUtils.beanToMap(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String auth) {
        this.author = auth;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Boolean getIstop() {
        return istop;
    }

    public void setIstop(Boolean istop) {
        this.istop = istop;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Boolean getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(Boolean allowComment) {
        this.allowComment = allowComment;
    }

    public Boolean getAllowShare() {
        return allowShare;
    }

    public void setAllowShare(Boolean allowShare) {
        this.allowShare = allowShare;
    }

    public void setUserLike(boolean userLike) { this.userLike  = userLike; }

    public boolean getUserLike() { return this.userLike; }

    public static int compareByCreated(Article a, Article b){
        if(a.getCreated().after(b.getCreated()))
            return -1;
        else if(a.getCreated().before(b.getCreated()))
            return 1;
        return 0;
    }

    public static int compareByIssueTime(Article a, Article b){
        if(a.getIstop() == b.getIstop()){
            if(a.getIssueTime().after(b.getIssueTime()))
                return -1;
            else if(a.getIssueTime().before(b.getIssueTime()))
                return 1;
            return 0;
        }
        if(BooleanUtils.isTrue(a.getIstop()))
            return -1;
        return 1;
    }

}
