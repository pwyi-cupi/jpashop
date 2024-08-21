package jpashop.siljeon_jpashop.repository;

import jakarta.persistence.EntityManager;
import jpashop.siljeon_jpashop.domain.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId()==null){
            em.persist(item); //신규 아이템은 등록
        }else{
            em.merge(item); //이미 DB에 있는 아이템은 업데이트
        }
    }
    public Item findOne(Long itemId){
        return em.find(Item.class, itemId);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
