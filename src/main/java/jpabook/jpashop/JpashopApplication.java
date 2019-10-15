package jpabook.jpashop;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class JpashopApplication {

    @Autowired
    private MemberService memberService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderService orderService;

    @PostConstruct
    public void setUp(){
        Member member = new Member();
        Address address = new Address("서울", "여기", "저기");
        member.setName("기리");
        member.setAddress(address);
        memberService.join(member);

        Book book = new Book();
        book.setName("java");
        book.setPrice(10000);
        book.setStockQuantity(100);
        book.setAuthor("giri");
        book.setIsbn("12312");
        itemService.save(book);

        Book book2 = new Book();
        book2.setName("python");
        book2.setPrice(15000);
        book2.setStockQuantity(100);
        book2.setAuthor("giri");
        book2.setIsbn("12312");
        itemService.save(book2);
    }

    public static void main(String[] args) {
        SpringApplication.run(JpashopApplication.class, args);
    }

}
