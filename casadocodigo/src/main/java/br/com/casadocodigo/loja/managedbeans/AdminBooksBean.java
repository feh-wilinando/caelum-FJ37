package br.com.casadocodigo.loja.managedbeans;

import br.com.casadocodigo.loja.daos.AuthorDAO;
import br.com.casadocodigo.loja.daos.BookDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.infra.MessagesHelper;
import br.com.casadocodigo.loja.models.Author;
import br.com.casadocodigo.loja.models.Book;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nando on 15/02/16.
 */
@Model
public class AdminBooksBean {

    private Book product = new Book();

    @Inject
    private FileSaver fileSaver;

    @Inject
    private BookDAO bookDAO;

    @Inject
    private AuthorDAO authorDAO;

    private List<Author> authors = new ArrayList<>();

    @Inject
    private MessagesHelper messageHelper;
    private Part summary;
    private Part cover;

    @Transactional
    public String  save(){

        String summaryPath = fileSaver.write("summaries", summary);
        String coverPath = fileSaver.write("covers", cover);
        product.setSummaryPath(summaryPath);
        product.setCoverPath(coverPath);

        bookDAO.save(product);

        messageHelper.addFlash(new FacesMessage("Livro cadastrado com sucesso"));

        return "/livros/list?faces-redirect=true";
    }

    public Book getProduct() {
        return product;
    }

    public List<Author> getAuthors() {
        return authors;
    }


    @PostConstruct
    private void load(){
        this.authors = authorDAO.list();
    }


    public Part getSummary() {
        return summary;
    }

    public void setSummary(Part summary) {
        this.summary = summary;
    }

    public Part getCover() {
        return cover;
    }

    public void setCover(Part cover) {
        this.cover = cover;
    }
}
