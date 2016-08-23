package com.torpre.rentasillas.model;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    public static void main(String[] args) throws IOException, SQLException {
        OrmLiteConfigUtil.writeConfigFile("ormlite_config.txt");
    }
}
