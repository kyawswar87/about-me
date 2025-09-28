package com.example.aboutme;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AboutmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AboutmeApplication.class, args);
	}

	@Bean
	public ChatClient chatClient(ChatClient.Builder chatClientBuilder, PgVectorStore vectorStore) {
		var retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
        		.documentRetriever(VectorStoreDocumentRetriever.builder()
                .similarityThreshold(0.30)
                .vectorStore(vectorStore)
                .build())
        		.build();
        return chatClientBuilder
			.defaultAdvisors(retrievalAugmentationAdvisor, new SimpleLoggerAdvisor())
			.defaultSystem("You are a helpful assistant that can answer questions about Kyaw Swa Aung.")
			.build();
	}
}
