package com.system.security.services;


import com.system.models.Role;
import com.system.repository.RoleRepository;
import com.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;



    public Role getRoleByName(String roleName) {
        return roleRepository.findByRole(roleName);
    }


}
