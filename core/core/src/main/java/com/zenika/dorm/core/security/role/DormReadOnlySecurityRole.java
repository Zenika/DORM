package com.zenika.dorm.core.security.role;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormReadOnlySecurityRole implements DormSecurityRole {

    @Override
    public boolean canRead() {
        return true;
    }

    @Override
    public boolean canWrite() {
        return false;
    }

    @Override
    public boolean canUpdate() {
        return false;
    }
}
