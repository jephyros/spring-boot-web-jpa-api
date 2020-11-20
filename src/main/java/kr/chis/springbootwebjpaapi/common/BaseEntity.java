package kr.chis.springbootwebjpaapi.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author InSeok
 * Date : 2020/11/21
 * Remark :
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {
    //todo 날짜 자동저장?

    @CreatedDate
    @Column(name="create_date")
    private LocalDateTime createDate;

    //AuditorAware 인터페이스를 구현하여
    //SecurityContext 에서 인증정보를 가져와 사용 할수있으나 지금 RestAPI는 별도로 ID를 받을예정
//    @CreatedBy
//    @Column(name="create_id")
//    private String createId;

    @LastModifiedDate
    @Column(name="modify_date")
    private LocalDateTime modifyData;

    //AuditorAware 인터페이스를 구현하여
    //SecurityContext 에서 인증정보를 가져와 사용 할수있으나 지금 RestAPI는 별도로 ID를 받을예정

//    @LastModifiedBy
//    @Column(name="modify_id")
//    private String modifyId;


}
