package com.shopping.base.domain.other;

import com.shopping.base.domain.IdEntity;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "shopping_template")
public class Template extends IdEntity {

    private String info;
    private String type;
    private String title;

    @Lob
    @Column(columnDefinition="LongText")
    private String content;
    private String mark;
    private boolean open;

    private String code_id;

    private String msg_url;

    private String platform_type;                  //接收端标志

    private String store_type;
}
