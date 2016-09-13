package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyClass {
    public static void main(String[] args) {
        Schema schema=new Schema(1,"phone1000.com.firemilitary.dao");
        Entity user=schema.addEntity("User");
        user.addIdProperty().autoincrement();
        user.addStringProperty("account").notNull();
        user.addStringProperty("password");
        user.addStringProperty("nickname");
        try {
            new DaoGenerator().generateAll(schema,"../FenghuoMilitary/app/src/main/java");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
