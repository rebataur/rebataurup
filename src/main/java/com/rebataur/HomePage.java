package com.rebataur;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    private transient ResultSet rs = null;
    private String result = "init";
    public HomePage(final PageParameters parameters) throws SQLException {
        super(parameters);

        add(new Label("version", getApplication().getFrameworkSettings().getVersion()));

        final TextField firstNumber = new TextField("first_num", new Model());
        final TextField secondNumber = new TextField("second_num", new Model());

        
        final Label pyMaxRes = new Label("pyMaxRes", new PropertyModel(this,"result"));
        pyMaxRes.setOutputMarkupId(true);
        add(pyMaxRes);
        Form pyMaxForm = new Form("py_max_form") {
            @Override
            protected void onSubmit() {
                super.onSubmit();

                try {
                    System.out.println("----------------");
                    String pyMaxSql = "SELECT pymax( " + firstNumber.getModelObject() + ", " + secondNumber.getModelObject() + ")";
                    System.out.println(pyMaxSql);
                    rs = DBUtil.executeSQL(pyMaxSql);
                    String res = "";
                    while (rs.next()) {
                        result = rs.getString(1);
                        
                        
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };
        pyMaxForm.add(firstNumber).add(secondNumber);
        add(pyMaxForm);
        add(pyMaxRes);

    }
}
