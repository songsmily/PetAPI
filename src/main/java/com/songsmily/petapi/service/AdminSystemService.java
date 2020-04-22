package com.songsmily.petapi.service;

import com.songsmily.petapi.dto.BasePage;
import com.songsmily.petapi.dto.SelectPageParams;
import com.songsmily.petapi.entity.AdminUser;

import java.util.List;
import java.util.Map;

public interface AdminSystemService {
    BasePage getUserList(SelectPageParams params);

    BasePage getAdminList(SelectPageParams params);

    boolean disableAdmin(String id);

    boolean enableAdmin(String id);

    boolean deleteAdmin(String id);

    boolean checkUsername(String username);

    boolean addAdmin(AdminUser adminUser);

    List<Map> getUserNameList(Integer accountType);

    Map<String, Object> getSystemAllInfo();

}
