package com.devinlee.javautildemo.util;

import lombok.Data;
import org.junit.Test;

import java.io.*;

/**
 * 序列化与反序列化
 * <p>
 * 注意事项：
 * 1、序列化对象必须实现Serializable序列化接口。
 * 2、序列化对象里面的属性是对象的话也要实现序列化接口。
 * 3、类的对象序列化后，类的序列化ID不要轻易修改，不然反序列化会失败
 *
 * @author devinlee
 */
public class SerializationUtils {

    /**
     * 序列化(将对象序列化至指定的文件)
     *
     * @param t
     * @param pathname
     * @param <T>
     * @throws IOException
     */
    public static <T> void serialize(T t, String pathname) throws IOException {

        //ObjectOutputStream对象输出流，将对象存储到指定文件中，完成对对象的序列化操作
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(pathname)));
        oos.writeObject(t);
        oos.close();

        System.out.println(String.format("[%s]对象序列化成功！", t.getClass().getTypeName()));
    }

    /**
     * 反序列化(将文件反序列化成指定的对象)
     *
     * @param pathname
     * @param t
     * @param <T>
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> T deserialize(String pathname, Class<T> t) throws FileNotFoundException, IOException, ClassNotFoundException {

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(pathname)));
        System.out.println(String.format("[%s]对象反序列化成功！", t.getTypeName()));

        return (T) ois.readObject();
    }

    @Test
    public void test() throws Exception {

        //序列化测试
        SerializationUtils.serialize(new Person("devinlee", 24, "备注"), "/Users/devinlee/Person.txt");

        //反序列化测试
        Person p = SerializationUtils.deserialize("/Users/devinlee/Person.txt", Person.class);
        System.out.println(p.toString());
    }
}

@Data
class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private int age;

    /**
     * transient意味该属性不参与序列化
     */
    transient private String remark;

    public Person(String name, int age, String remark) {
        this.name = name;
        this.age = age;
        this.remark = remark;
    }
}
