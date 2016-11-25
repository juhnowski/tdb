package ru.simplgroupp.webapp.terrorist.service;

import ru.simplgroupp.webapp.terrorist.model.UserEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * 10.08.2015
 * 15:01
 */

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UserServiceImpl implements UserService {
    @PersistenceContext(unitName = "TerrPU")
    private EntityManager manager;

    @Override
    public UserEntity getUserByLoginAndPassword(String login, String password) {
        TypedQuery<UserEntity> query = manager.createQuery("from UserEntity u where u.login = :login and u.password = :password", UserEntity.class);
        query.setParameter("login", login);
        query.setParameter("password", password);
        List<UserEntity> result = query.getResultList();
        return result.size() > 0 ? result.get(0) : null;
    }
}
