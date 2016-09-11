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

import com.rebataur.formpanels.LinePlot.LinePlotModel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 *
 * @author PRanjan
 */
public class LinePlot extends Panel{
   
    public LinePlot(String id) {
        super(id);
        
        Form<LinePlotModel> form = new Form<LinePlotModel>("scatter_plot_form", new CompoundPropertyModel<LinePlotModel>(new LinePlotModel())){
            @Override
            protected void onSubmit() {
                super.onSubmit(); 
                LinePlotModel scatterPlotModel = this.getModelObject();
           
                
                
            }
            
        };
        
        form.add(new TextField<String>("x")).add(new TextField<String>("y"));
        add(form);
        
    }
   
    class LinePlotModel{
        private String measureColumn,measureTable;

        public LinePlotModel() {
        }

        public String getMeasureColumn() {
            return measureColumn;
        }

        public void setMeasureColumn(String measureColumn) {
            this.measureColumn = measureColumn;
        }

        public String getMeasureTable() {
            return measureTable;
        }

        public void setMeasureTable(String measureTable) {
            this.measureTable = measureTable;
        }

   
        
    }
}
