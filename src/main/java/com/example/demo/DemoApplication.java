package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.io.File;
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        String fileName = "report.pdf";
        File file = new File(fileName);
        Resource resource = new ClassPathResource("reports/jasperReport.jrxml");

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JREmptyDataSource());
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            SimpleOutputStreamExporterOutput simpleOutputStreamExporterOutput = new SimpleOutputStreamExporterOutput(bos);
            exporter.setExporterOutput(simpleOutputStreamExporterOutput);
            SimplePdfExporterConfiguration simplePdfExporterConfiguration = new SimplePdfExporterConfiguration();
            simplePdfExporterConfiguration.setMetadataAuthor("TheGeekyAsian");
            exporter.setConfiguration(simplePdfExporterConfiguration);
            exporter.exportReport();
            FileUtils.writeByteArrayToFile(file, bos.toByteArray());
            simpleOutputStreamExporterOutput.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
