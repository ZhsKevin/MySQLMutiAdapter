package com.hswag.sqlSyntaxConverter.validator;

import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class XMLValidator {

    public static void main(String[] args) throws Exception {
//
       // builder.parse(new File("C:\\Users\\lrf\\IdeaProjects\\test\\CustomerDao (2).xml"));
    }

    public static String validMapper(String content) {
       // boolean isValid = true;
        String errormsg = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            factory.setNamespaceAware(false);
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(false);
            factory.setCoalescing(false);
            factory.setExpandEntityReferences(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new XMLMapperEntityResolver());
            //builder.setErrorHandler(new MyErrorHandler());
            builder.parse(getStringStream(content));
        }catch (Exception e){
           // isValid = false;
            errormsg = e.getMessage();
            e.printStackTrace();

        }
        return errormsg;
    }

    public static String validMapper(String filepath,String content) {
        // boolean isValid = true;
        String errormsg = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            factory.setNamespaceAware(false);
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(false);
            factory.setCoalescing(false);
            factory.setExpandEntityReferences(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new XMLMapperEntityResolver());
            //builder.setErrorHandler(new MyErrorHandler());
            builder.parse(getStringStream(content));
        }catch (Exception e){
            // isValid = false;
            errormsg = filepath+"===>"+e.getMessage();
            e.printStackTrace();

        }
        return errormsg;
    }


    private  static InputStream getStringStream(String sInputString){
        if (sInputString != null && !sInputString.trim().equals("")){
            try{
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
                return tInputStringStream;
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

}
