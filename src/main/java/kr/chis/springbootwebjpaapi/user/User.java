package kr.chis.springbootwebjpaapi.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bs_user")
@Data
public class User {
    @Id
    private Long id;

    private String userName;
    private String email;
    private String cellPhone;


}
