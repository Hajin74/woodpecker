package org.example.woodpeckerback.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    private boolean isDeleted;

    @Builder
    public Note(Book book, User user, String content) {
        this.book = book;
        this.user = user;
        this.content = content;
        this.isDeleted = false;
    }

    public void softDelete() {
        this.isDeleted = true;
    }

}
