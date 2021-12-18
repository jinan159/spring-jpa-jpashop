package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    @DisplayName("상품을 주문합니다")
    public void order() throws Exception {
        // given
        Member member = createMember();

        int bookPrice = 10000;
        int bookCount = 10;

        Book book = createBook(bookPrice, bookCount);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        Order order = orderRepository.findOne(orderId);

        // then
        // 상품 주문시 상태는 ORDER
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
        // 주문한 상품 종류수가 정확해야 한다
        assertThat(order.getOrderItems().size()).isEqualTo(1);
        // 주문 가격은 가격*수량 이다
        assertThat(order.getTotalPrice()).isEqualTo(bookPrice * orderCount);
        // 주문한 수량만큼 재고가 감소해야 한다
        assertThat(book.getStockQuantity()).isEqualTo(bookCount - orderCount);
    }

    @Test
    @DisplayName("재고수량이 초과될 경우 오류가 발생합니다")
    public void stockOver() throws Exception {
        Member member = createMember();

        int bookPrice = 10000;
        int bookCount = 2;

        Book book = createBook(bookPrice, bookCount);

        int orderCount = 5;

        // then
        // 재고 수량보다 주문 수량이 많으면 예외가 발생한다
        assertThrows(
                NotEnoughStockException.class,
                () -> orderService.order(member.getId(), book.getId(), orderCount));
    }

    private Book createBook(int bookPrice, int bookCount) {
        Book book = new Book();
        book.setName("좋은 책");
        book.setPrice(bookPrice);
        book.setIsbn("123123");
        book.setStockQuantity(bookCount);
        em.persist(book);
        return book;
    }

    @Test
    @DisplayName("주문이 취소됩니다")
    public void cancelOrder() throws Exception {
        // given
        Member member = createMember();

        int bookPrice = 10000;
        int bookCount = 10;
        Book book = createBook(bookPrice, bookCount);
        
        // when
        Long orderId = orderService.order(member.getId(), book.getId(), 2);
        orderService.cancelOrder(orderId);
        Order cancelledOrder = orderRepository.findOne(orderId);

        // then
        // 취소된 주문은 상태가 CANCEL
        assertThat(cancelledOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        // 주문이 취소되면 재고가 다시 원복되야 한다
        assertThat(book.getStockQuantity()).isEqualTo(bookCount);
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("jwkim");
        member.setAddress(new Address("부산", "진구", "123456"));
        em.persist(member);
        return member;
    }

}