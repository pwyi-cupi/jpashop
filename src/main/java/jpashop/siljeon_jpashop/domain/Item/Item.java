package jpashop.siljeon_jpashop.domain.Item;

import jakarta.persistence.*;
import jpashop.siljeon_jpashop.domain.Category;
import jpashop.siljeon_jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
public abstract class Item {

    @Id @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories=new ArrayList<>();

    //==비즈니스 로직==//
    public void addStock(int quantity){
        stockQuantity+=quantity;
    }

    public void removeStock(int quantity){
        int restStock=stockQuantity-quantity;
        if(restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        stockQuantity=restStock;
    }
}
