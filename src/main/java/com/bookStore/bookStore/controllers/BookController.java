package com.bookStore.bookStore.controllers;

import com.bookStore.bookStore.entity.Book;
import com.bookStore.bookStore.entity.MyBookList;
import com.bookStore.bookStore.service.BookService;
import com.bookStore.bookStore.service.MyBookListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
// Change Second
@Controller
public class BookController {

//Home page view
@Autowired
private BookService service;

    @Autowired
    private MyBookListService myBookService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/book_register")
    public String bookRegister() {
        return "bookRegister";
    }



    @GetMapping("/available_books")
    public ModelAndView getAllBook() {
        List<Book> list=service.getAllBook();
        return new ModelAndView("bookList","book",list);
    }

    @PostMapping("/save")
    public String addBook(@ModelAttribute Book b) {
        service.save(b);
        return "redirect:/available_books";
    }


    @GetMapping("/my_books")
    public String getMyBooks(Model model)
    {
        List<MyBookList>list=myBookService.getAllMyBooks();
        model.addAttribute("book",list);
        return "myBooks";
    }
    @RequestMapping("/mylist/{id}")
    public String getMyList(@PathVariable("id") int id) {
        Book b=service.getBookById(id);
        MyBookList mb=new MyBookList(b.getId(),b.getName(),b.getAuthor(),b.getPrice());
        myBookService.saveMyBooks(mb);
        return "redirect:/my_books";
    }

    @RequestMapping("/editBook/{id}")
    public String editBook(@PathVariable("id") int id,Model model) {
        Book b=service.getBookById(id);
        model.addAttribute("book",b);
        return "bookEdit";
    }
    @RequestMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id")int id) {
        service.deleteById(id);
        return "redirect:/available_books";
    }

    //Test
    @RequestMapping(value = "/getdata",method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public List<Book> getData(){

        Book book =new Book();
        book.setId(1);
        book.setAuthor("Haha");
        book.setName("Test");
        book.setPrice("1000Ks");

        Book book1 =new Book();
        book1.setId(2);
        book1.setAuthor("Ha");
        book1.setName("Te");
        book1.setPrice("5000Ks");

        List<Book> list= new ArrayList<>();
        list.add(book);
        list.add(book1);
        return list;
    }

    //Testing for Fetch
    @GetMapping("/show_books")
    @ResponseBody
    @CrossOrigin
    public List<Book> getAllBooks() {
        return service.getAllBook(); //  logic to fetch all books
    }

    //Testing fetch for post method
    @PostMapping(value = "/save_book",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin
    public String AddBook(@RequestBody Book b) {
        System.out.println(b.getName()+" "+b.getAuthor()+"  "+b.getPrice());
        service.save(b);
        return "Book added successfully!";
    }

}
