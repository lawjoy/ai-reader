//package com.hmall.aireader.ai.rag;
//
//import dev.langchain4j.data.document.Document;
//import dev.langchain4j.data.document.DocumentSplitter;
//import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
//import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
//import dev.langchain4j.data.embedding.Embedding;
//import dev.langchain4j.data.segment.TextSegment;
//import dev.langchain4j.model.embedding.EmbeddingModel;
//import dev.langchain4j.rag.content.retriever.ContentRetriever;
//import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
//import dev.langchain4j.store.embedding.EmbeddingStore;
//import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
//import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
//import jakarta.annotation.Resource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.FilenameFilter;
//import java.util.List;
//
///**
// * Rag
// */
//@Configuration
//public class RagConfig {
//
//    @Resource
//    private EmbeddingModel qwenEmbeddingModel;
//
//    @Resource
//    private EmbeddingStore<TextSegment> embeddingStore;
//
//
//    @Bean
//    public ContentRetriever contentRetriever(){
//        //=====RAG=====
//        //1.加载文件
//        List<Document> documents = FileSystemDocumentLoader.loadDocuments("src/main/resources/docs");
//        //2.分割文档，每个文档一千个字符 200个重叠
//        DocumentByParagraphSplitter paragraphSplitter =
//                new DocumentByParagraphSplitter(1000, 200);
//        // 3. 自定义文档加载器
//        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
//                .documentSplitter(paragraphSplitter)
//                // 为了提高搜索质量，为每个 TextSegment 添加文档名称
//                .textSegmentTransformer(textSegment -> TextSegment.from(textSegment.metadata().getString("file_name")
//                        + "\n" + textSegment.text(),textSegment.metadata()))
//                // 使用指定的向量模型
//                .embeddingModel(qwenEmbeddingModel)
//                .embeddingStore(embeddingStore)
//                .build();
//        // 加载文档
//        ingestor.ingest(documents);
//        // 4. 自定义内容查询器
//        ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
//                .embeddingModel(qwenEmbeddingModel)
//                .embeddingStore(embeddingStore)
//                .maxResults(5) // 最多 5 个检索结果
//                .minScore(0.75) // 过滤掉分数小于 0.75 的结果
//                .build();
//
//        return contentRetriever;
//    }
//}
