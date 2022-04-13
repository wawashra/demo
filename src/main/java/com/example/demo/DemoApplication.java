package com.example.demo;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
import java.util.*;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        BatchReceivingDTO batchReceivingDTO = new BatchReceivingDTO();
        List<BatchReceivingItem> lItem = new ArrayList<>();
        BatchReceivingItem bi1 = new BatchReceivingItem();
        bi1.setProviderName("تجربة");
        bi1.setTotalRequestNetArabicText("تسعه و ستون الف جنيه ");

        batchReceivingDTO.setTotalRequestedNetArabicText("تشت تشت تشت تشت");
        lItem.add(bi1);
        batchReceivingDTO.setItems(lItem);
        String fileName = "report.pdf";
        File file = new File(fileName);
        Resource resource = new ClassPathResource("reports/ARBatchReceivingReport.jrxml");

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(batchReceivingDTO.getItems());
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("totalRequestedNet", batchReceivingDTO.getTotalRequestedNet());
            parameters.put("totalRequestedNetArabicText", batchReceivingDTO.getTotalRequestedNetArabicText());
            parameters.put(JRParameter.REPORT_LOCALE, Locale.US);

            JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
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

    public static class BatchReceivingDTO {

        private List<BatchReceivingItem> items;
        private Double totalRequestedNet;
        private String totalRequestedNetEnglishText;
        private String totalRequestedNetArabicText;

        public BatchReceivingDTO() {
            items = new ArrayList();
            totalRequestedNet = 0D;
        }

        public List<BatchReceivingItem> getItems() {
            return items;
        }

        public void setItems(List<BatchReceivingItem> items) {
            this.items = items;
        }

        public Double getTotalRequestedNet() {
            return totalRequestedNet;
        }

        public void setTotalRequestedNet(Double totalRequestedNet) {
            this.totalRequestedNet = totalRequestedNet;
        }

        public String getTotalRequestedNetEnglishText() {
            return totalRequestedNetEnglishText;
        }

        public void setTotalRequestedNetEnglishText(String totalRequestedNetEnglishText) {
            this.totalRequestedNetEnglishText = totalRequestedNetEnglishText;
        }

        public String getTotalRequestedNetArabicText() {
            return totalRequestedNetArabicText;
        }

        public void setTotalRequestedNetArabicText(String totalRequestedNetArabicText) {
            this.totalRequestedNetArabicText = totalRequestedNetArabicText;
        }
    }

    public static class BatchReceivingItem {
        private String providerLicense;
        private String providerName;
        private String submissionCode;
        private String receptionDate;
        private String activityDate;
        private String encounterType;
        private Long totalClaimCount;
        private Double totalRequestNet;
        private String totalRequestNetEnglishText;
        private String totalRequestNetArabicText;

        public String getProviderLicense() {
            return providerLicense;
        }

        public void setProviderLicense(String providerLicense) {
            this.providerLicense = providerLicense;
        }

        public String getProviderName() {
            return providerName;
        }

        public void setProviderName(String providerName) {
            this.providerName = providerName;
        }

        public String getSubmissionCode() {
            return submissionCode;
        }

        public void setSubmissionCode(String submissionCode) {
            this.submissionCode = submissionCode;
        }

        public String getReceptionDate() {
            return receptionDate;
        }

        public void setReceptionDate(String receptionDate) {
            this.receptionDate = receptionDate;
        }

        public String getActivityDate() {
            return activityDate;
        }

        public void setActivityDate(String activityDate) {
            this.activityDate = activityDate;
        }

        public String getEncounterType() {
            return encounterType;
        }

        public void setEncounterType(String encounterType) {
            this.encounterType = encounterType;
        }

        public Long getTotalClaimCount() {
            return totalClaimCount;
        }

        public void setTotalClaimCount(Long totalClaimCount) {
            this.totalClaimCount = totalClaimCount;
        }

        public Double getTotalRequestNet() {
            return totalRequestNet;
        }

        public String getTotalRequestNetEnglishText() {
            return totalRequestNetEnglishText;
        }

        public void setTotalRequestNetEnglishText(String totalRequestNetEnglishText) {
            this.totalRequestNetEnglishText = totalRequestNetEnglishText;
        }

        public String getTotalRequestNetArabicText() {
            return totalRequestNetArabicText;
        }

        public void setTotalRequestNetArabicText(String totalRequestNetArabicText) {
            this.totalRequestNetArabicText = totalRequestNetArabicText;
        }

        public void setTotalRequestNet(Double totalRequestNet) {
            this.totalRequestNet = totalRequestNet;
        }
    }

}


