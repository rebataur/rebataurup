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
package com.rebataur.model;

import com.rebataur.DBUtil;
import java.util.List;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.codejargon.fluentjdbc.api.mapper.ObjectMappers;
import org.codejargon.fluentjdbc.api.query.Mapper;
import org.codejargon.fluentjdbc.api.query.Query;

/**
 *
 * @author PRanjan
 */
public class ModelService {

    public ModelService() {

    }

    private FluentJdbc getFluentJDBC() {
        FluentJdbc fluentJdbc = new FluentJdbcBuilder()
                .connectionProvider(DBUtil.getDS())
                .build();
        return fluentJdbc;
    }

    private Query getQuery() {
        Query query = getFluentJDBC().query();
        return query;
    }

    public List<PGTable> getPGTable(String schemaName) {

        ObjectMappers objectMappers = ObjectMappers.builder().build();
        Mapper<PGTable> pgTableMapper = objectMappers.forClass(PGTable.class);
        List<PGTable> pgTables = getQuery().select("SELECT * FROM information_schema.tables WHERE table_schema = ?")
                .params(schemaName)
                .listResult(pgTableMapper);
        return pgTables;
    }

       public List<PGColumn> getPGTableColumn(String tableName) {

        ObjectMappers objectMappers = ObjectMappers.builder().build();
        Mapper<PGColumn> pgColumnMapper = objectMappers.forClass(PGColumn.class);
        List<PGColumn> pgColumns = getQuery().select("SELECT * FROM information_schema.columns WHERE table_name = ?")
                .params(tableName)
                .listResult(pgColumnMapper);
        
        return pgColumns;
    }

    public static void main(String[] args) {
        List<PGTable> list = new ModelService().getPGTable("public");
        for (PGTable pGTable : list) {
            System.out.println(pGTable.getTableName());
        }
        
        List<PGColumn> columnList = new ModelService().getPGTableColumn("salaries");
        for (PGColumn pGColumn : columnList) {
            System.out.println(pGColumn.getColumnName() + " Type : " + pGColumn.getDataType());
            
        }      
    }
}
