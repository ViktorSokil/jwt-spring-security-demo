package com.sokil.service.impl;


import com.sokil.entity.Role;
import com.sokil.repository.RoleRepository;
import com.sokil.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleRepository roleDao;

    @Override
    public void saveRole(Role role) {
        String roleInDB = roleDao.findByRole(role.getRole()).getRole();
        if (!role.getRole().equals(roleInDB)){
            roleDao.save(role);
        }
    }
}
