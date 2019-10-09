package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStcokException;
import jpabook.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void 상품_주문() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("Primary Java", "기리", "dd", 25000, 10);

        int orderCount = 2;
        //when
        long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order saved = orderRepository.findOne(orderId);
        Assertions.assertThat(OrderStatus.ORDER).isEqualTo(saved.getStatus());
        Assertions.assertThat(1).isEqualTo(saved.getOrderItems().size());
        Assertions.assertThat(25000 * orderCount).isEqualTo(saved.getTotalPrice());
        Assertions.assertThat(8).isEqualTo(book.getStockQuantity());
    }

    @Test(expected = NotEnoughStcokException.class)
    public void 상품_주문_재고_수량_초과() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("Primary Java", "기리", "dd", 25000, 10);
        int orderCount = 11;

        //when
        long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        fail("재고 보다 많은 수량의 경우 예외가 발생한다");
    }

    @Test
    public void 주문_취소() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("Java", "Giri", "ㅁㄴㅇㄹ", 50000, 50);

        int orderCount = 2;
        long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //when
        orderService.cancel(orderId);

        //then
        Order canceled = orderRepository.findOne(orderId);
        Assertions.assertThat(canceled.getStatus()).isEqualTo(OrderStatus.CANCEL);
        Assertions.assertThat(50).isEqualTo(book.getStockQuantity());
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("동길");
        member.setAddress(new Address("서울", "남산센트럴자이", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, String author, String isbn, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

}