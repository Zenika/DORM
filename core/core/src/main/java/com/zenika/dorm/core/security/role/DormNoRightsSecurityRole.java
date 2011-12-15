package com.zenika.dorm.core.security.role;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormNoRightsSecurityRole implements DormSecurityRole {

    @Override
    public boolean canRead() {
        return false;
    }

    @Override
    public boolean canWrite() {
        return false;
    }

    @Override
    public boolean canOverride() {
        return false;
    }
}
