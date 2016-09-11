/*
 * Copyright 2016 PRanjan.
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
package com.rebataur.service;

import com.rebataur.DBUtil;
import com.rebataur.HomePage;
import com.rebataur.model.PlotModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;

/**
 *
 * @author PRanjan
 */
public class RebataurService {

    private transient ResultSet rs = null;

  

    
    private byte[] getPlot(final String sql) throws SQLException {
        byte[] res = null;
        rs = DBUtil.executeSQL(sql);
        try {
            while (rs.next()) {

                res = rs.getBytes(1);                
                return res;

            }
        } catch (SQLException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public byte[] getPlot(PlotModel plotModel) throws SQLException {
        String sql = "";
        if(plotModel.getPlotType().equals("Word Cloud")){
            sql = String.format("SELECT plot_word_cloud('%s','%s',%s)",plotModel.getTableName(),plotModel.getColumnName(),plotModel.getLimit());
        }else if(plotModel.getPlotType().equals("Scatter Plot")){
            
            sql = String.format("SELECT plot_scatter('%s','%s',%s)",plotModel.getTableName(),plotModel.getColumnName(),plotModel.getLimit());
            System.out.println(sql);
        }else if(plotModel.getPlotType().equals("Line Plot")){
            
            sql = String.format("SELECT plot_line('%s','%s',%s)",plotModel.getTableName(),plotModel.getColumnName(),plotModel.getLimit());
            System.out.println(sql);
        }
        return getPlot(sql);
    }

}
