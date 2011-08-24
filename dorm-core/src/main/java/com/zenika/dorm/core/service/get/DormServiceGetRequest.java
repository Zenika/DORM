package com.zenika.dorm.core.service.get;

import com.zenika.dorm.core.service.DormServiceProcess;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormServiceGetRequest extends DormServiceProcess {

    public DormServiceGetValues getValues();

    public boolean isRepositoryRequest();


}
