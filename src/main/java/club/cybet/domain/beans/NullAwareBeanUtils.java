package club.cybet.domain.beans;

import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

/**
 * kkz-api (club.cybet.domain.beans)
 * Created by: cybet
 * On: 05 Jun, 2018 6/5/18 4:30 PM
 **/
public class NullAwareBeanUtils extends BeanUtilsBean{

    @Override
    public void copyProperty(Object dest, String name, Object value)
            throws IllegalAccessException, InvocationTargetException {
        if(value==null)return;
        if(name.equals("id"))return;
        if(name.equals("uid"))return;
        if(name.equals("active"))return;
        if(name.equals("created"))return;
        if(name.equals("updated"))return;
        if(name.equals("createdById"))return;
        if(name.equals("updatedById"))return;
        super.copyProperty(dest, name, value);
    }

}