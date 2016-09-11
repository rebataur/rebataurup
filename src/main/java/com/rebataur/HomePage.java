package com.rebataur;

import com.rebataur.formpanels.FileUploadPanel;
import com.rebataur.formpanels.PlotPanel;
import com.rebataur.service.RebataurService;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;

public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    
    private String result = "init";

    public HomePage(final PageParameters parameters) throws SQLException, IOException {
        super(parameters);
        
            
            
        
         
        
        
        add(new FileUploadPanel("file_upload_panel"));
        add(new PlotPanel("plot_panel"));
    }
    
    private RebataurService getRebataurService(){
        return ((WicketApplication)this.getApplication()).getRebataurService();
    }
}
