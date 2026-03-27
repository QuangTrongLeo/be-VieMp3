package be_viemp3.viemp3.service.ai;

import be_viemp3.viemp3.dto.request.ai.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChatAIService {
    private final ChatClient chatClient;
    private final JdbcTemplate jdbcTemplate;

    public ChatAIService(ChatClient.Builder builder, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        // 1. Khởi tạo bộ nhớ hội thoại
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(30)
                .build();

        // 2. Build ChatClient với đầy đủ Advisors mặc định
        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    public String chatAI(ChatRequest request) {
        String question = request.getMessage();

        // 1. Generate SQL
        String sql = generateSql(question);

        // 2. Nếu không phải SQL → trả luôn
        if (!AISqlUtils.isSafeSelect(sql)) {
            return sql;
        }

        try {
            // 3. Execute SQL
            List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);

            // 4. Format answer
            return formatAnswer(question, data);

        } catch (Exception e) {
            return "Không thể truy vấn dữ liệu: " + e.getMessage();
        }
    }

    private String generateSql(String question) {
        return chatClient.prompt()
                .system(AIConstant.DB_SCHEMA)
                .user(question)
                .call()
                .content();
    }

    private String formatAnswer(String question, List<Map<String, Object>> data) {
        return chatClient.prompt()
                .system("Dựa trên dữ liệu sau, hãy trả lời người dùng: " + data)
                .user(question)
                .call()
                .content();
    }
}