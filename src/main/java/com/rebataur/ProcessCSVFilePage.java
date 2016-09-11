package com.rebataur;

import com.rebataur.formpanels.FileUploadPanel;
import com.rebataur.formpanels.ProcessCSVPanel;
import com.rebataur.service.RebataurService;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.logging.log4j.core.layout.CsvLogEventLayout;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

public class ProcessCSVFilePage extends WebPage {

    private static final long serialVersionUID = 1L;

    private String result = "init";
    private ProcessCSVPanel processCSVPanel;

    
    public ProcessCSVFilePage(final PageParameters parameters) throws SQLException, IOException {
        super(parameters);

        File folder = new File(FileUploadPanel.UPLOAD_FOLDER);
        File[] listOfFiles = folder.listFiles();

        List<String> csvFiles = new ArrayList<String>();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().contains(".csv")) {
                csvFiles.add(file.getName());

            }
        }

        ListView listview = new ListView("file_lists", Model.of(csvFiles)) {
            protected void populateItem(ListItem item) {
                item.add(new Label("file_name", item.getModel()));
                item.add(new Link("process") {
                    @Override
                    public void onClick() {
                        String processFileName = FileUploadPanel.UPLOAD_FOLDER + item.getModelObject().toString();
                        ProcessCSVPanel panel;
                        try {
                            panel = new ProcessCSVPanel("process_file_panel", new Model(processFileName));
                            processCSVPanel.replaceWith(panel);
                            processCSVPanel = panel;
                        } catch (IOException ex) {
                            Logger.getLogger(ProcessCSVFilePage.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });
            }
        };
        add(listview);

        processCSVPanel = new ProcessCSVPanel("process_file_panel", new Model());
        processCSVPanel.setOutputMarkupPlaceholderTag(true);
        add(processCSVPanel);

    }

    private RebataurService getRebataurService() {
        return ((WicketApplication) this.getApplication()).getRebataurService();
    }
}
