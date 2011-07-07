package com.zenika.dorm.core.modelnew;

/**
 * Extension point on the model to add specific metadatas
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormOrigin {

    String getQualifier();

    String getOrigin();
}
