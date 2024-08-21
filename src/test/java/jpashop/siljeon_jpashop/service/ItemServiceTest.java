package jpashop.siljeon_jpashop.service;

import jpashop.siljeon_jpashop.domain.Item.Album;
import jpashop.siljeon_jpashop.domain.Item.Book;
import jpashop.siljeon_jpashop.domain.Item.Item;
import jpashop.siljeon_jpashop.exception.NotEnoughStockException;
import jpashop.siljeon_jpashop.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 상품저장(){
        Item book=new Book();
        itemService.saveItem(book);
        Item findItem = itemService.findOne(book.getId());

        assertEquals(book, itemRepository.findOne(findItem.getId()));
    }

    @Test
    public void 상품개수_증가(){
        Item book=new Book();
        book.setStockQuantity(100);
        book.addStock(1);
        assertEquals(book.getStockQuantity(), 101);
    }

    @Test
    public void 상품개수_감소_오류테스트(){
        Item album=new Album();
        album.setStockQuantity(1);
        try {
            album.removeStock(2);
        }catch (NotEnoughStockException e){
            return;
        }
        fail("예외가 발생해야 한다.");
    }

}