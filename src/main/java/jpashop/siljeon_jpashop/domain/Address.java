package jpashop.siljeon_jpashop.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

//@Entity Member와 Delivery 엔티티에 각각 들어가기 때문에 @Entity 하지 않음
@Getter
@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipcode;

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
