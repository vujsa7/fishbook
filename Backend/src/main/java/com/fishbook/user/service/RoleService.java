package com.fishbook.user.service;

import com.fishbook.user.model.Role;

public interface RoleService {
    Role findByName(String name);
}
