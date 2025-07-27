package com.example.camundaservice.service;

import com.example.camundaservice.model.UserInfo;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.identity.Group;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyIdentityService {

    private final IdentityService identityService;

    public MyIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }

    public User login(UserInfo userInfo) throws Exception {
        // 1. ایجاد کاربر اگر وجود ندارد
        User user = identityService.createUserQuery().userId(userInfo.getUsername()).singleResult();
        if (user == null)
            throw new Exception("user not found");
        return user;
    }

    public User syncUser(UserInfo userInfo) {
        User user = identityService.createUserQuery().userId(userInfo.getUsername()).singleResult();
        // 1. ایجاد کاربر اگر وجود ندارد
        if (user == null) {
            user = identityService.newUser(userInfo.getUsername());
            user.setFirstName(userInfo.getFirstName());
            user.setLastName(userInfo.getLastName());
            user.setPassword(userInfo.getPassword());
            identityService.saveUser(user);
        }

        // 2. بررسی گروه‌ها و عضویت
        for (String groupId : userInfo.getGroups()) {
            Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
            /*
            if (group == null) {
                group = identityService.newGroup(groupId);
                group.setName(groupId);
                group.setType("WORKFLOW");
                identityService.saveGroup(group);
            }*/

            // بررسی عضویت کاربر در گروه: با یک Query ترکیبی
            List<User> groupUsers = identityService.createUserQuery()
                .memberOfGroup(groupId)
                .userId(userInfo.getUsername())
                .list();

            boolean isMember = !groupUsers.isEmpty();

            if (!isMember) {
                identityService.createMembership(userInfo.getUsername(), groupId);
            }
        }

        return user;
    }

    public List<String> getAllGroups() {
         return identityService
            .createGroupQuery()
            .list()
                .stream().map(Group::getId)
                    .toList();
    }

    public List<User> getUsers() {
        return identityService
                .createUserQuery()
                .list();
    }
}
