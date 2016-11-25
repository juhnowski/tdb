package ru.simplgroupp.webapp.terrorist.service;

import ru.simplgroupp.webapp.terrorist.model.UserEntity;

import javax.ejb.Local;

/**
 * 10.08.2015
 * 14:52
 */

@Local
public interface UserService {
    UserEntity getUserByLoginAndPassword(String login, String password);
}
