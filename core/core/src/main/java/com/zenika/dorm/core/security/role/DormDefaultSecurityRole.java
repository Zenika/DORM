package com.zenika.dorm.core.security.role;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormDefaultSecurityRole implements DormSecurityRole {

    @Override
    public boolean canOverride() {
        return false;
    }
}
