package com.shopping.base.domain.other;

import com.shopping.base.domain.IdEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@ToString(exclude={"childs","parent"})
@Table(name = "shopping_area")
public class Area extends IdEntity {

    @Column(name="areaname")
    private String areaName;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.REMOVE})
    private List<Area> childs = new ArrayList();

    @ManyToOne(fetch = FetchType.LAZY)
    private Area parent;

    private int sequence;

    private int level;

    @Column(columnDefinition = "bit default false")
    private boolean common;
}
