package com.example.rag.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HRPolicyLoader {

    private final VectorStore vectorStore;

    @Value("classpath:HR_policies.pdf")
    Resource policyFile;

    public HRPolicyLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadHRPolicy() {
        TikaDocumentReader documentReader = new TikaDocumentReader(policyFile);
        List<Document> documents = documentReader.get();
        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(200)
                .withMaxNumChunks(400)
                .build();

        vectorStore.add(splitter.split(documents));
    }
}
