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

/**
 *
 * @author PRanjan3
 */
public class UploadFileModel {
    
    private String fieldName;
    private enum FIELD_TYPE {
        TEXT,INTEGER,BIGINT,DOUBLE,DATE,BOOL,MONEY
    }
    
    private String applyRegex;
    private FIELD_TYPE fieldType;

    public UploadFileModel() {
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public FIELD_TYPE getFieldType() {
        return fieldType;
    }

    public void setFieldType(FIELD_TYPE fieldType) {
        this.fieldType = fieldType;
    }

    public String getApplyRegex() {
        return applyRegex;
    }

    public void setApplyRegex(String applyRegex) {
        this.applyRegex = applyRegex;
    }
    
    
}
