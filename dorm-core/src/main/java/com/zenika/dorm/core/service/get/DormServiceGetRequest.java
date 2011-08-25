package com.zenika.dorm.core.service.get;

import com.zenika.dorm.core.service.DormServiceProcess;
import com.zenika.dorm.core.service.DormServiceRequest;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormServiceGetRequest extends DormServiceRequest {

    public DormServiceGetValues getValues();

    public boolean getTransitiveDependencies();

    public boolean isUniqueResultRequest();
}
