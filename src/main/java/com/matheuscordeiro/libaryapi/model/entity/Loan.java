package com.matheuscordeiro.libaryapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String customer;
    @JoinColumn
    @ManyToOne
    private Book book;
    @Column
    private LocalDate loanDate;
    @Column
    private Boolean returned;
}
