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
package com.rebataur.formpanels;

import com.rebataur.DBUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;

import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

/**
 *
 * @author PRanjan
 */
public class ProcessCSVPanel extends Panel {

    private static List<String> dataTypes = Arrays.asList(new String[]{"BOOL", "NUMERIC", "INT", "BIGINT", "TEXT", "DATE"});
    private static final List<String> DATA_TYPES = Arrays.asList(new String[]{"BOOL", "NUMERIC", "INT", "BIGINT", "TEXT", "DATE"});
    private String selected = "TEXT";
    private String tableName = "";

    public ProcessCSVPanel(String id, IModel<String> model) throws FileNotFoundException, IOException {
        super(id, model);

        // Read the first line of csv
        String[] firstLineCol = null, secondLineCol = null;
        if (model != null && model.getObject() != null) {

            BufferedReader br = new BufferedReader(new FileReader(model.getObject()));
            firstLineCol = br.readLine().split(",");

            boolean anyFieldBlank = true;
            while (anyFieldBlank) {
                secondLineCol = br.readLine().split(",");
                for (int i = 0; i < secondLineCol.length; i++) {
                    if (secondLineCol[i] == null || secondLineCol[i].isEmpty() || secondLineCol.length != firstLineCol.length) {
                        anyFieldBlank = true;
                        break;
                    } else {
                        anyFieldBlank = false;
                    }
                }
            }

        } else {
            firstLineCol = new String[]{"No files found"};
            secondLineCol = new String[]{"No Column found"};
        }

        final String[] columns = firstLineCol;

        final List<DataModel> dataModels = new ArrayList<DataModel>();
        for (int i = 0; i < firstLineCol.length; i++) {
            DataModel dt = new DataModel();
            dt.setDateType(firstLineCol[i] + " ( " + secondLineCol[i] + " )");
            dataModels.add(dt);
        }
        // Create a form with data drop downs
        Form fieldForm = new Form("field_form", new Model()) {
            @Override
            protected void onSubmit() {
                super.onSubmit();
                // create SQL query to create data

                //
                try {

                    String dropTable = "DROP TABLE IF EXISTS " + tableName + ";";
                    String colType = " ";
                    String defDT = "TEXT";
                    for (int i = 0; i < columns.length; i++) {

                        defDT = dataModels.get(i).getDataTypeValue() == null ? "TEXT" : dataModels.get(i).getDataTypeValue();

                        colType += columns[i].trim().toLowerCase().replaceAll("[^a-zA-Z]+", "").replace("-", "_").replace(" ", "_").replaceAll("\\^([0-9]+)", " ").trim() + " " + defDT;
                        if (i != columns.length - 1) {
                            colType += ",";
                        }
                    }
                    String createTable = "CREATE TABLE IF NOT EXISTS " + tableName + " ( " + colType + ");";
                    System.out.println(dropTable);
                    DBUtil.executeSQLNoResult(dropTable);
                    System.out.println(createTable);
                    DBUtil.executeSQLNoResult(createTable);

                    //
                    int insertSQLCount = 10;
                    String[] insertSQLArr = new String[insertSQLCount];

                    Reader in = new FileReader(model.getObject().toString());
                    Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
                    boolean firstLine = true;
                    for (CSVRecord record : records) {
                        if (firstLine) {
                            firstLine = false;
                        } else {

                            String values = "";
                            for (int i = 0; i < columns.length; i++) {
                                String val = record.get(i);
                                defDT = dataModels.get(i).getDataTypeValue() == null ? "TEXT" : dataModels.get(i).getDataTypeValue();

                                
                                if (defDT.equals("TEXT") || defDT.equals("DATE")) {
                                    if (defDT.equals("TEXT")) {
                                        values += "'" + val.trim().replace("'", "") + "'";
                                    } else {
                                        values += "'" + val + "'";
                                    }

                                } else if (defDT.equals("INT") || defDT.equals("BIGINT") || defDT.equals("NUMERIC")) {
                                    if (val.isEmpty() || val.equals("")) {
                                        val = "0";
                                    }
                                    values += val.replaceAll("[^\\d.]", "");
                                }

                                if (i != columns.length - 1) {
                                    values += ",";
                                }

                            }

                            String insertSQL = "INSERT INTO " + tableName + " VALUES(" + values + ");";
                            if (insertSQLCount <= 9) {

                                insertSQLArr[insertSQLCount++] = insertSQL;
                            } else {

                                try {
                                    insertSQLCount = 0;
                                    DBUtil.executeSQLBatch(insertSQLArr);
                                } catch (Exception ex) {
                                    System.out.println("=====================================");
                                    System.out.println(insertSQL);
                                }

                            }

                        }
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ProcessCSVPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ProcessCSVPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ProcessCSVPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };

        // table_name
        TextField tableName = new TextField("table_name", new PropertyModel(this, "tableName"));
        fieldForm.add(tableName);

        final List<String> secondCol = new ArrayList();
        ListView formFieldInputs = new ListView("form_field_inputs", Model.of(dataModels)) {
            @Override
            protected void populateItem(ListItem item) {
                DataModel dt = (DataModel) item.getModelObject();
                item.add(new Label("field_name", dt.getDataType()));

                item.add(new DropDownChoice<String>("data_type", new PropertyModel(dt, "dataTypeValue"), DATA_TYPES) {
                    @Override
                    protected boolean wantOnSelectionChangedNotifications() {
                        return true;
                    }

                    @Override
                    protected void onSelectionChanged(String newSelection) {
                        super.onSelectionChanged(newSelection);

                    }

                    @Override
                    protected String getNullKeyDisplayValue() {
                        return "TEXT";
                    }

                });
            }
        };

        fieldForm.add(formFieldInputs);
        add(fieldForm);
        // on submit create sql and create the table

        // upload the data into the table.
    }

}

class DataModel implements Serializable {

    public DataModel() {
    }
    private String dataTypeValue;
    private String dataType;

    public String getDataType() {
        return dataType;
    }

    public void setDateType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataTypeValue() {
        return dataTypeValue;
    }

    public void setDataTypeValue(String dataTypeValue) {
        this.dataTypeValue = dataTypeValue;
    }

}
