package com.tmax.eTest.Admin.util;

import com.tmax.eTest.Admin.sso.service.SsoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.FatalBeanException;

import java.util.HashSet;
import java.util.Set;

public class ColumnNullPropertiesHandler {
    private static final Logger logger = LoggerFactory.getLogger(ColumnNullPropertiesHandler.class);

    public static boolean copyNonNullProperties(Object src, Object target) {
        try{
            BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
            return true;
        }
        catch (FatalBeanException e){
            logger.debug("FatalBeanException occured");
            return false;
        }
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
