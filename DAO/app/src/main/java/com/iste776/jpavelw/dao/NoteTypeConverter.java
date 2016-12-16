package com.iste776.jpavelw.dao;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by jpavelw on 11/10/16.
 */

public class NoteTypeConverter implements PropertyConverter<NoteType, String>{
    @Override
    public NoteType convertToEntityProperty(String databaseValue) {
        return NoteType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(NoteType entityProperty) {
        return entityProperty.name();
    }
}
