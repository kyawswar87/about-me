package com.example.aboutme;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.stereotype.Service;

@Service
public class AboutMeService {

    private final PgVectorStore vectorStore;

    public AboutMeService(PgVectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }
    
    String findALLPDF() {
        try {
            List<Document> documents = this.vectorStore.similaritySearch(SearchRequest.builder().query("").topK(5).build());
            
            StringBuilder result = new StringBuilder();
            result.append("PDF Verification Results:\n");
            result.append("Total documents found: ").append(documents.size()).append("\n\n");
            
            for (int i = 0; i < Math.min(documents.size(), 5); i++) {
                Document doc = documents.get(i);
                result.append("Document ").append(i + 1).append(":\n");
                result.append("ID: ").append(doc.getId()).append("\n");
                result.append("Content preview: ").append(doc.getFormattedContent().substring(0, Math.min(100, doc.getFormattedContent().length()))).append("...\n");
                result.append("Metadata: ").append(doc.getMetadata()).append("\n\n");
            }
            
            return result.toString();
        } catch (Exception e) {
            return "Error verifying PDF: " + e.getMessage();
        }
    }
}
