package com.example.aboutme;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;

@Component
public class PdfReader {
     private final PgVectorStore vectorStore;

    public PdfReader(PgVectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void ingestPdf(String filePath) {
        // Read paragraphs from PDF
        ParagraphPdfDocumentReader reader = new ParagraphPdfDocumentReader(
            filePath,
            PdfDocumentReaderConfig.builder().build()
        );

        List<Document> docs = reader.get();

        // Store in PgVector (auto-generates embeddings + inserts into db)
        vectorStore.add(docs);
    }
}
