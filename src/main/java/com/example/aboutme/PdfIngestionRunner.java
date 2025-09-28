package com.example.aboutme;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PdfIngestionRunner implements CommandLineRunner{
    private final PdfReader pdfReader;

    public PdfIngestionRunner(PdfReader pdfReader) {
        this.pdfReader = pdfReader;
    }

    @Override
    public void run(String... args) {
        pdfReader.ingestPdf("KyawSwaAung.pdf");
        System.out.println("PDF paragraphs ingested into Postgres!");
    }
}
