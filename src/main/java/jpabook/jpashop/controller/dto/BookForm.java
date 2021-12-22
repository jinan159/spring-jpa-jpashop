package jpabook.jpashop.controller.dto;

import jpabook.jpashop.domain.item.Book;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class BookForm {

    private Long id;

    @NotBlank(message = "이름은 빈값으로 할 수 없습니다")
    private String name;

    @NotNull(message = "가격을 입력해 주세요")
    @Min(value = 0, message = "가격은 0원 이상이어야 합니다")
    private int price;

    @NotNull(message = "권수를 입력해 주세요")
    @Min(value = 0, message = "권수는 0 보다 작을 수 없습니다")
    private int stockQuantity;

    @NotBlank(message = "저자를 입력해 주세요")
    private String author;

    @NotBlank(message = "ISBN은 빈값으로 할 수 없습니다")
    private String isbn;

    public Book toEntity() {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        book.setAuthor(author);
        book.setIsbn(isbn);
        return book;
    }
}
