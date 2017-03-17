package com.chablis.lilosoft.utils;

import com.chablis.lilosoft.model.MapAddress;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorList implements Comparator {


    @Override
    public int compare(Object o1, Object o2) {
        String s1=((MapAddress)o1).getArea_name();
        String s2=((MapAddress)o2).getArea_name();
        return Collator.getInstance().compare(s1, s2);
    }

    public static List sort(List list) {
        ComparatorList comparatorList = new ComparatorList();
        Collections.sort(list, comparatorList);
        return list;
    }
}
