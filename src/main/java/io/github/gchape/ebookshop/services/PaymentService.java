package io.github.gchape.ebookshop.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gchape.ebookshop.entities.Order;
import io.github.gchape.ebookshop.repositories.IOrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {
    private final ObjectMapper objectMapper;
    private final IOrderRepository orderRepository;

    public PaymentService(ObjectMapper objectMapper, IOrderRepository orderRepository) {
        this.objectMapper = objectMapper;
        this.orderRepository = orderRepository;
    }

    public void parseItems(HttpServletRequest request) {
        var json = readRequestBody(request);

        try {
            UserOrder order = objectMapper.readValue(json, UserOrder.class);

            order.items.forEach(item -> {
                orderRepository.save(new Order(order.user, item.isbn, item.quantity, LocalDate.now()));
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String readRequestBody(HttpServletRequest request) {
        try (var reader = request.getReader()) {
            var json = new StringBuilder();

            for (String line; (line = reader.readLine()) != null; ) {
                json.append(line);
            }

            return json.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class UserOrder {
        private List<Item> items;
        private String user;

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public static class Item {
            private String isbn;
            private Integer quantity;

            public Item() {
            }

            public Item(String isbn, Integer quantity) {
                this.isbn = isbn;
                this.quantity = quantity;
            }

            public String getIsbn() {
                return isbn;
            }

            public void setIsbn(String isbn) {
                this.isbn = isbn;
            }

            public Integer getQuantity() {
                return quantity;
            }

            public void setQuantity(Integer quantity) {
                this.quantity = quantity;
            }
        }
    }
}
