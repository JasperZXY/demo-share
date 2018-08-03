package org.ruanwei.demo.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

public class BeanUtils {

    public static void print(Object obj) {
        Class cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                Object r = f.get(obj);
                System.out.printf("%s = %s\t", f.getName(), r);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }

    public static void print(Object obj, String msg) {
        System.out.printf("%s\n", msg);
        Class cls = obj.getClass();
        System.out.printf("[%s]:", cls.getSimpleName());
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                Object r = f.get(obj);
                System.out.printf("%s = %s\t", f.getName(), r);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }

    public static <T> T copy(Object orig, Class<T> c) {
        try {
            T desc = c.newInstance();
            PropertyUtils.copyProperties(desc, orig);
            return desc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object clone(Object orig) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        return copy(orig, orig.getClass());
    }


    /**
     * 获取类下所有的字段，包括父类
     * @param clazz
     * @return
     */
    public static List<Field> getAllField(Class clazz) {
        List<Field> list = new ArrayList<>();
        Class cla = clazz;
        while (true) {
            Field[] fields = cla.getDeclaredFields();
            if (fields.length > 0) {
                list.addAll(0, Arrays.asList(fields));
            }
            cla = cla.getSuperclass();
            if (cla == Object.class) {
                break;
            }
        }
        return list;
    }

}
