package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStcokException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//

    /**
     * 재고 수량을 증가
     */
    public void addStock(int stockQuantity){
        this.stockQuantity += stockQuantity;
    }

    /**
     * 재고 수량을 감소
     */
    public void removeStock(int stockQuantity){
        int restStock = this.stockQuantity - stockQuantity;

        if (restStock < 0) {
            throw new NotEnoughStcokException("need more stock");
        }

        this.stockQuantity = restStock;
    }
}
