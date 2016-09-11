/*
 * Copyright 2016 PRanjan3.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rebataur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.postgresql.ds.PGPoolingDataSource;

/**
 *
 * @author PRanjan3
 */
public class DBUtil {

    public static PGPoolingDataSource source = new PGPoolingDataSource();
    public static boolean inited = false;

    public static void init() {

        source.setDataSourceName("A Data Source");
        source.setServerName("localhost");
        source.setDatabaseName("postgres");
        source.setUser("postgres");
        source.setPassword("postgres");
        source.setMaxConnections(1000);

    }

    public static Connection getConn() {
        if (!inited) {
            init();
            inited = true;
        }
        Connection conn = null;
        try {
            conn = source.getConnection();

            // use connection
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static ResultSet executeSQL(String sql) throws SQLException {
        Connection con = null;
        try {
            con = getConn();
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            con.close();
            return rs;

        } catch (SQLException ex) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            con.close();
        }
        return null;
    }

    public static void executeSQLNoResult(String sql) throws SQLException {
        Connection con = null;
        try {
            con = getConn();
            Statement stat = con.createStatement();
            stat.execute(sql);
            con.close();

        } catch (SQLException ex) {
            System.out.println(sql);
//            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            con.close();
        }


    }

    public static void executeSQLBatch(String[] sql) throws SQLException {
        Connection con = null;
        try {
            con = getConn();
            Statement stat = con.createStatement();
            for (int i = 0; i < sql.length; i++) {

                stat.addBatch(sql[i]);
            }
            stat.executeBatch();

        } catch (SQLException ex) {
//            for (int i = 0; i < sql.length; i++) {
//                System.out.println(sql[i]);
//            }
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            con.close();
        }

    }

    public static void main(String[] args) throws SQLException {
        init();
        System.out.println(getConn());
        System.out.println(executeSQL("select 1"));
    }

    public static DataSource getDS() {
        if (!inited) {
            init();
            inited = true;
        }
        return source;
    }
}
