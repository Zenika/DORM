package com.zenika.dorm.core.security.role;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormReadWriteSecurityRole implements DormSecurityRole {

    @Override
    public boolean canRead() {
        return true;
    }

    @Override
    public boolean canWrite() {
        return true;
    }

    @Override
    public boolean canUpdate() {
        return false;
    }
}
