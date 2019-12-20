package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.List;

/**
 * 小区名称表(Plot)表实体类
 *
 * @author SongSmily
 * @since 2019-12-18 19:19:41
 */
@SuppressWarnings("serial")
public class Plot extends Model<Plot> {
    //小区ID
    private Integer plotId;
    //小区名
    private String plotName;

    @TableField(exist = false)
    private List<Build> builds;

    public List<Build> getBuilds() {
        return builds;
    }

    public void setBuilds(List<Build> builds) {
        this.builds = builds;
    }

    @Override
    public String toString() {
        return "Plot{" +
                "plotId=" + plotId +
                ", plotName='" + plotName + '\'' +
                ", builds=" + builds +
                '}';
    }

    public Integer getPlotId() {
        return plotId;
    }

    public void setPlotId(Integer plotId) {
        this.plotId = plotId;
    }

    public String getPlotName() {
        return plotName;
    }

    public void setPlotName(String plotName) {
        this.plotName = plotName;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.plotId;
    }
    }