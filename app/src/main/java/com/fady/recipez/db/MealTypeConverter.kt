package com.fady.recipez.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConvertor {

    @TypeConverter
    fun fromAnyToString(attribute:Any?): String{  //Inserting to table
        if(attribute == null)
            return ""
        return attribute as String
    }

    @TypeConverter
    fun fromStringToAny(attribute: String?):Any{  //Retrieve data from database
        if(attribute == null)
            return ""
        return attribute
    }
    }
