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
package com.rebataur.formpanels;

import com.rebataur.WicketApplication;
import com.rebataur.model.PlotModel;
import com.rebataur.service.RebataurService;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

/**
 *
 * @author PRanjan
 */
public class PlotPanel extends Panel {

    private final List<String> plotTypes = Arrays.asList(new String[]{"Scatter Plot", "Line Plot", "Word Cloud"});
    private final List<String> sqlLimits = Arrays.asList(new String[]{"100","1000","10000"});
    private final String tableName = "", columnName = "", plotType = "";
    private  PlotImagePanel plotImagePanel = new PlotImagePanel("plot_image_panel", new Model());
    

  

    public PlotPanel(String id) {
        super(id);

        Form<PlotModel> form = new Form<PlotModel>("plot_form",new CompoundPropertyModel<PlotModel>(new PlotModel())) {
            @Override
            protected void onSubmit() {
                PlotModel model = this.getModelObject();
                super.onSubmit();
                PlotImagePanel newPlotImagePanel = new PlotImagePanel("plot_image_panel", new Model(model) );
                plotImagePanel.replaceWith(newPlotImagePanel);
                plotImagePanel = newPlotImagePanel;
                
            }

        };

        
        form.add(new TextField("tableName"));
        form.add(new TextField("columnName"));
        form.add(new DropDownChoice("plotType", plotTypes));
        form.add(new DropDownChoice("limit", sqlLimits));
        add(form);
        
        add(plotImagePanel);
    }

    private RebataurService getRebataurService() {
        return ((WicketApplication) this.getApplication()).getRebataurService();
    }
}
