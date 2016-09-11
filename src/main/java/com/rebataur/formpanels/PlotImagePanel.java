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

import java.sql.SQLException;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.model.Model;

/**
 *
 * @author PRanjan
 */
public class PlotImagePanel extends Panel {

    public PlotImagePanel(String id,Model<PlotModel> model) {
        super(id);
        PlotModel plotModel = model.getObject();
        
        IResource imageResource = new DynamicImageResource() {
            @Override

            protected byte[] getImageData(IResource.Attributes attributes) {
                try {
                    return getRebataurService().getPlot(plotModel);
                } catch (SQLException ex) {

                }
                return null;
            }

        };

        NonCachingImage image = new NonCachingImage("plot", imageResource);

        add(image);
    }

    private RebataurService getRebataurService() {
        return ((WicketApplication) this.getApplication()).getRebataurService();
    }
}
