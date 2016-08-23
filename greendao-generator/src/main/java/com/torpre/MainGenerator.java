package com.torpre;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MainGenerator {

    public static void main(String[] args) throws Exception {

        //place where db folder will be created inside the project folder
        Schema schema = new Schema(1, "com.torpre.rentasillas.model");

        //Entity i.e. Class to be stored in the database // ie table 'order'
        Entity orders = schema.addEntity("Orders");
        orders.addIdProperty().autoincrement(); //It is the primary key for uniquely identifying a row
        orders.addIntProperty("rectangularTables");
        orders.addIntProperty("rectangularCloths");
        orders.addIntProperty("roundTables");
        orders.addIntProperty("roundCloths");
        orders.addIntProperty("pinkCoverCloths");
        orders.addIntProperty("blueCoverCloths");
        orders.addIntProperty("orangeCoverCloths");
        orders.addIntProperty("chairs");
        orders.addStringProperty("address").notNull(); //Not null is SQL constrain
        orders.addDateProperty("date").notNull();
        orders.addStringProperty("payment");
        orders.addByteProperty("status").notNull();

        Entity rentableObjects = schema.addEntity("RentableObjects");
        rentableObjects.addIdProperty().autoincrement();
        rentableObjects.addStringProperty("name").notNull();
        rentableObjects.addIntProperty("amount").notNull();

        Entity history = schema.addEntity("History");
        history.addIdProperty().autoincrement();
        history.addStringProperty("histiryTag").notNull();

        //add constraint in OrdersDao class y method createTable
        //CONSTRAINT ORDERS_STATUS_CK CHECK(STATUS >= 1 AND STAT)S <= 3))
        /*
        public static final byte STATUS_TO_BRING = 1;
        public static final byte STATUS_TO_COLLEGE = 2;
        */

        //Ejemplo de relaciones 1:1, notese que los Daos para cada entidad no generan el constraint foreign key
        /*Entity toBring = schema.addEntity("ToBring");
        toBring.addIdProperty().autoincrement();
        Property toBringFk = toBring.addLongProperty("idOrders").unique().getProperty();
        toBring.addToOne(orders, toBringFk);

        Entity toCollege = schema.addEntity("ToCollege");
        toCollege.addIdProperty().autoincrement();
        Property toCollegeFk = toCollege.addLongProperty("idOrders").unique().getProperty();
        toCollege.addToOne(orders, toCollegeFk);*/



        //  ./app/src/main/java/   ----   com/torpre/restasillas/model is the full path
        new DaoGenerator().generateAll(schema, "./app/src/main/java");

    }

}
