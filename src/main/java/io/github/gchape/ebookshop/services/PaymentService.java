package io.github.gchape.ebookshop.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gchape.ebookshop.repositories.IOrderRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {
    private final ObjectMapper objectMapper;
    private final IOrderRepository orderRepository;

    public PaymentService(ObjectMapper objectMapper, IOrderRepository orderRepository) {
        this.objectMapper = objectMapper;
        this.orderRepository = orderRepository;
    }

    public Order parseOrder(BufferedReader reader) throws IOException {
        try (reader) {
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();

            JsonNode rootNode = objectMapper.readTree(json);
            String user = rootNode.get("user").asText();
            List<OrderItem> items = new ArrayList<>();

            for (JsonNode itemNode : rootNode.get("items")) {
                String isbn = itemNode.get("isbn").asText();
                int quantity = itemNode.get("quantity").asInt();
                items.add(new OrderItem(isbn, quantity));
            }

            return new Order(user, items);
        }
    }

    public void save(Order order) {
        order.items.forEach(item -> orderRepository.save(new io.github.gchape.ebookshop.entities.Order(order.user, item.isbn, item.quantity, LocalDate.now())));
    }

    public static class OrderItem {
        private final String isbn;
        private final int quantity;

        public OrderItem(String isbn, int quantity) {
            this.isbn = isbn;
            this.quantity = quantity;
        }

        public String getIsbn() {
            return isbn;
        }

        public int getQuantity() {
            return quantity;
        }
    }

    public static class Order {
        private final String user;
        private final List<OrderItem> items;

        public Order(String user, List<OrderItem> items) {
            this.user = user;
            this.items = items;
        }

        public String getUser() {
            return user;
        }

        public List<OrderItem> getItems() {
            return items;
        }
    }
}
