package com.zenika.dorm.core.dao.mongo;

import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface MongoDB {

    Mongo getInstance();

    String getDatabase();

    Morphia getMorphia();
}
