package com.example.rickandmortyapplication.DATA

import androidx.room.TypeConverter
import com.example.rickandmortyapplication.POJO.Location

class TypeConverters {

    @TypeConverter
    fun fromLocation(location: Location): String? {
        return location.name
    }

    @TypeConverter
    fun toLocation(s: String?): Location {
        return Location(s)
    }

    @TypeConverter
    fun fromStringList(l:  List<String>?): String {
        var result = ""
        if (l!=null) {
            for (url in l) {
                result = (result + ";;" + url).trim()
            }
        }
        return result
    }

    @TypeConverter
    fun toStringList(s: String): List<String>? {
        if (s=="") {
            return null
        } else {
            return s.split(";;")
        }
    }

}