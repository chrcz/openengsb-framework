package org.openengsb.core.security.internal;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.openengsb.core.api.security.UserManagementException;
import org.openengsb.core.security.UserUtils;
import org.openengsb.core.security.model.Permission;
import org.openengsb.core.security.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.provisioning.GroupManager;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.sun.org.apache.xpath.internal.functions.FuncBoolean;

public class GroupManagerImpl implements GroupManager {

    private EntityManager entityManager;

    @Override
    public List<String> findAllGroups() {
        TypedQuery<String> query = entityManager.createNamedQuery("listAllRoles", String.class);
        return query.getResultList();
    }

    @Override
    public List<String> findUsersInGroup(String groupName) {
        TypedQuery<String> query = entityManager.createNamedQuery("listUsersWithRole", String.class);
        query.setParameter("groupname", groupName);
        return query.getResultList();
    }

    @Override
    public void createGroup(String groupName, List<GrantedAuthority> authorities) {
        Role role = new Role(groupName);
        Collection<Role> roles = UserUtils.getRolesFromSpringUser(authorities);
        role.setRoles(roles);
        Collection<Permission> permissions = UserUtils.getPermissionsFromSpringUser(authorities);
        role.setPermissions(permissions);
        entityManager.persist(role);
    }

    @Override
    public void deleteGroup(String groupName) {
        Role role = entityManager.find(Role.class, groupName);
        if (role == null) {
            throw new UserManagementException("group with name " + groupName + " not found");
        }
        entityManager.remove(role);
    }

    @Override
    public void renameGroup(String oldName, String newName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addUserToGroup(String username, String group) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeUserFromGroup(String username, String groupName) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<GrantedAuthority> findGroupAuthorities(String groupName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addGroupAuthority(String groupName, GrantedAuthority authority) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeGroupAuthority(String groupName, GrantedAuthority authority) {
        // TODO Auto-generated method stub

    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}