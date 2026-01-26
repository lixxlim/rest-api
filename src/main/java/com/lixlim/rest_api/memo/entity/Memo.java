package com.lixlim.rest_api.memo.entity;

import com.lixlim.rest_api._common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t1_memo")
@Builder
@AllArgsConstructor
public class Memo extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
}
