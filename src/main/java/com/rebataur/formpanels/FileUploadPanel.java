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

import com.rebataur.ProcessCSVFilePage;
import java.io.File;
import java.io.IOException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.util.lang.Bytes;

/**
 *
 * @author PRanjan3
 */
public class FileUploadPanel extends Panel {

    private FileUploadField fileUpload;
    public static String UPLOAD_FOLDER = "D:\\DATA_DUMP\\";

    public FileUploadPanel(String id) throws IOException {
        super(id);
        Form<?> form = new Form<Void>("file_upload_form") {
            @Override
            protected void onSubmit() {

                final FileUpload uploadedFile = fileUpload.getFileUpload();
                if (uploadedFile != null) {

                    // write to a new file
                    File newFile = new File(UPLOAD_FOLDER
                            + uploadedFile.getClientFileName());
                    System.out.println(newFile.getAbsolutePath());
                    if (newFile.exists()) {
                        newFile.delete();
                    }

                    try {
                        newFile.createNewFile();
                        uploadedFile.writeTo(newFile);                        
                        info("saved file: " + uploadedFile.getClientFileName());
                        setResponsePage(ProcessCSVFilePage.class);
                    } catch (Exception e) {
                        throw new IllegalStateException("Error");
                    }
                }

            }

        };

        // Enable multipart mode (need for uploads file)
        form.setMultiPart(true);

        // max upload size, 10k
        form.setMaxSize(Bytes.gigabytes(2));

        form.add(fileUpload = new FileUploadField("fileUpload"));

        add(form);

//        RepeatingView listFiles = new RepeatingView("list_files");
//        Files.walk(Paths.get(UPLOAD_FOLDER)).forEach((Path filePath) -> {
//
//            listFiles.add(new Label(filePath.getFileName().toString()));
//
//        });
//
//        File folder = new File(UPLOAD_FOLDER);
//        File[] listOfFiles = folder.listFiles();
//
//        for (File file : listOfFiles) {
//            if (file.isFile()) {
//                System.out.println(file.getName());
//                listFiles.add(new Label(listFiles.newChildId(),file.getName()));
//            }
//        }
//
//        add(listFiles);

    }

}
