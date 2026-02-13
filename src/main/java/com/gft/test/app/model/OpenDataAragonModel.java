package com.gft.test.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OPEN_DATA_ARAGON",
       uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenDataAragonModel {
    @Id
    private int id;

    private String name;
    private boolean available;
}
