package isa.projekat.Projekat.model.hotel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "RoomType")
public class RoomType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public RoomType() {
        super();
    }

    public RoomType(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
