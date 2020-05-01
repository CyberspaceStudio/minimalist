package com.artofsolving.jodconverter;

//
// JODConverter - Java OpenDocument Converter
// Copyright (C) 2004-2007 - Mirko Nasato <mirko@artofsolving.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
// http://www.gnu.org/copyleft/lesser.html
//

import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.DocumentFormatRegistry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 重写 BasicDocumentFormatRegistry 文档格式
 * @author HuGuangJun
 */
public class BasicDocumentFormatRegistry implements DocumentFormatRegistry {

    private List/* <DocumentFormat> */ documentFormats = new ArrayList();

    public void addDocumentFormat(DocumentFormat documentFormat) {
        documentFormats.add(documentFormat);
    }

    protected List/* <DocumentFormat> */ getDocumentFormats() {
        return documentFormats;
    }

    /**
     * @param extension
     *            the file extension
     * @return the DocumentFormat for this extension, or null if the extension
     *         is not mapped
     */
    @Override
    public DocumentFormat getFormatByFileExtension(String extension) {
        if (extension == null) {
            return null;
        }
        //将文件名后缀统一转化
        if (extension.indexOf("doc") >= 0) {
            extension = "doc";
        }
        if (extension.indexOf("ppt") >= 0) {
            extension = "ppt";
        }
        if (extension.indexOf("xls") >= 0) {
            extension = "xls";
        }
        String lowerExtension = extension.toLowerCase();
        for (Iterator it = documentFormats.iterator(); it.hasNext();) {
            DocumentFormat format = (DocumentFormat) it.next();
            if (format.getFileExtension().equals(lowerExtension)) {
                return format;
            }
        }
        return null;
    }

    @Override
    public DocumentFormat getFormatByMimeType(String mimeType) {
        for (Iterator it = documentFormats.iterator(); it.hasNext();) {
            DocumentFormat format = (DocumentFormat) it.next();
            if (format.getMimeType().equals(mimeType)) {
                return format;
            }
        }
        return null;
    }
}