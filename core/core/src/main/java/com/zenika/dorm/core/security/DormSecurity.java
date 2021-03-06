package com.zenika.dorm.core.security;

import com.google.inject.Singleton;
import com.zenika.dorm.core.security.role.DormNoRightsSecurityRole;
import com.zenika.dorm.core.security.role.DormSecurityRole;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Singleton
public class DormSecurity {

    private DormSecurityRole role;

    public DormSecurity() {
        role = new DormNoRightsSecurityRole();
    }

    public DormSecurityRole getRole() {
        return role;
    }

    public void setRole(DormSecurityRole role) {
        this.role = role;
    }
}
