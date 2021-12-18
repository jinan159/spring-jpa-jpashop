package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired private ItemService itemService;
    @Autowired private ItemRepository itemRepository;

    @Nested
    @DisplayName("앨범")
    class AlbumTest{

        @Test
        @DisplayName("한개 저장합니다")
        public void save() throws Exception {
            // given
            Album album = new Album();
            album.setName("앨범 이름");

            // then
            saveItem(album);
        }

        @Test
        @DisplayName("저장 후, 수정합니다")
        public void update() throws Exception {
            // given
            Album album = new Album();
            album.setName("앨범 이름");

            // then
            updateItem(album);
        }

        @Test
        @DisplayName("하나만 조회합니다")
        public void findOne() throws Exception {
            // given
            Album album = new Album();
            album.setName("앨범 이름");

            // then
            findOneItem(album);
        }

        @Test
        @DisplayName("모두 조회합니다")
        public void findAll() throws Exception {
            // given
            List<Item> albums = new ArrayList<>();
            albums.add(new Album());
            albums.add(new Album());
            albums.add(new Album());

            // then
            findAllItem(albums);
        }

        @Test
        @DisplayName("아티스트와 기타정보를 저장합니다")
        public void saveAlbum() throws Exception {
            // given
            Album album = new Album();
            album.setArtist("jinan");
            album.setEtc("jinan");

            // when
            Long savedId = itemService.saveItem(album);
            Album savedAlbum = (Album) itemRepository.findOne(savedId);

            // then
            assertThat(savedAlbum).isEqualTo(album);
            assertThat(savedAlbum.getArtist()).isEqualTo(album.getArtist());
            assertThat(savedAlbum.getEtc()).isEqualTo(album.getEtc());
        }
    }

    @Nested
    @DisplayName("책")
    class BookTest{

        @Test
        @DisplayName("한권을 저장합니다")
        public void save() throws Exception {
            // given
            Book book = new Book();
            book.setName("책이름");

            // then
            saveItem(book);
        }

        @Test
        @DisplayName("저장 후, 수정합니다")
        public void update() throws Exception {
            // given
            Book book = new Book();
            book.setName("책이름");

            // then
            updateItem(book);
        }

        @Test
        @DisplayName("하나만 조회합니다")
        public void findOne() throws Exception {
            // given
            Book book = new Book();
            book.setName("my book");

            // then
            findOneItem(book);
        }

        @Test
        @DisplayName("모두 조회합니다")
        public void findAll() throws Exception {
            // given
            List<Item> books = new ArrayList<>();
            books.add(new Book());
            books.add(new Book());
            books.add(new Book());

            // then
            findAllItem(books);
        }

        @Test
        @DisplayName("저자와 ISBM을 저장합니다")
        public void saveBook() throws Exception {
            // given
            Book book = new Book();
            book.setAuthor("jinan");
            book.setIsbn("123456789");

            // when
            Long savedId = itemService.saveItem(book);
            Book savedBook = (Book) itemRepository.findOne(savedId);

            // then
            assertThat(savedBook).isEqualTo(book);
            assertThat(savedBook.getAuthor()).isEqualTo(book.getAuthor());
            assertThat(savedBook.getIsbn()).isEqualTo(book.getIsbn());
        }
    }

    @Nested
    @DisplayName("영화")
    class MovieTest{

        @Test
        @DisplayName("한권을 저장합니다")
        public void save() throws Exception {
            // given
            Movie movie = new Movie();
            movie.setName("책이름");

            // then
            saveItem(movie);
        }

        @Test
        @DisplayName("저장 후, 수정합니다")
        public void update() throws Exception {
            // given
            Movie movie = new Movie();
            movie.setName("책이름");

            // then
            updateItem(movie);
        }

        @Test
        @DisplayName("하나만 조회합니다")
        public void findOne() throws Exception {
            // given
            Movie movie = new Movie();
            movie.setName("책이름");

            // then
            findOneItem(movie);
        }

        @Test
        @DisplayName("모두 조회합니다")
        public void findAll() throws Exception {
            // given
            List<Item> movies = new ArrayList<>();
            movies.add(new Movie());
            movies.add(new Movie());
            movies.add(new Movie());

            // then
            findAllItem(movies);
        }

        @Test
        @DisplayName("배우와 감독을 저장합니다")
        public void saveMovie() throws Exception {
            // given
            Movie movie = new Movie();
            movie.setActor("jinan");
            movie.setDirector("jwkim");

            // when
            Long savedId = itemService.saveItem(movie);
            Movie savedMovie = (Movie) itemRepository.findOne(savedId);

            // then
            assertThat(savedMovie).isEqualTo(movie);
            assertThat(savedMovie.getActor()).isEqualTo(movie.getActor());
            assertThat(savedMovie.getDirector()).isEqualTo(movie.getDirector());
        }
    }

    private void saveItem(Item item) throws Exception {
        // given
        Item newItem = item;

        // when
        Long savedId = itemService.saveItem(newItem);
        Item findItem = itemRepository.findOne(savedId);

        // then
        assertThat(findItem).isEqualTo(newItem);
    }

    public void updateItem(Item item) throws Exception {
        // given
        Item newItem = item;

        // when
        Long savedId = itemService.saveItem(newItem);
        Item savedItem = itemRepository.findOne(savedId);

        String updatedName = "updated name";
        savedItem.setName(updatedName);
        Long updatedId = itemService.saveItem(savedItem);

        Item updatedItem = itemRepository.findOne(updatedId);

        // then
        assertThat(updatedItem.getName()).isEqualTo(updatedName);
        assertThat(updatedItem).isSameAs(newItem);
    }

    public void findOneItem(Item item) throws Exception {
        // given
        Item newItem = item;

        // when
        Long savedId = itemService.saveItem(newItem);
        Item findItem = itemRepository.findOne(savedId);

        // then
        assertThat(findItem).isNotNull();
        assertThat(findItem).isEqualTo(newItem);
    }

    public void findAllItem(List<Item> items) throws Exception {
        // when
        for (Item item : items) {
            itemService.saveItem(item);
        }
        List<Item> savedItems = itemService.findItems();

        // then
        assertThat(savedItems.size()).isEqualTo(items.size());
    }

}