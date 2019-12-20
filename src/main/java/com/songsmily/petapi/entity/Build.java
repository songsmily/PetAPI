package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * 楼栋名称表(Build)表实体类
 *
 * @author SongSmily
 * @since 2019-12-18 19:19:41
 */
@SuppressWarnings("serial")
public class Build extends Model<Build> {
    //楼栋ID
    private Integer build_id;
    //小区ID(外键)
    private Integer plot_id;
    //楼栋名称
    private String build_name;

    @Override
    public String toString() {
        return "Build{" +
                "build_id=" + build_id +
                ", plot_id=" + plot_id +
                ", build_name='" + build_name + '\'' +
                '}';
    }

    public Integer getBuildId() {
        return build_id;
    }

    public void setBuildId(Integer build_id) {
        this.build_id = build_id;
    }

    public Integer getPlotId() {
        return plot_id;
    }

    public void setPlotId(Integer plot_id) {
        this.plot_id = plot_id;
    }

    public String getBuildName() {
        return build_name;
    }

    public void setBuildName(String build_name) {
        this.build_name = build_name;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.build_id;
    }
    }