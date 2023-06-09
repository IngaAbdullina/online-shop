package com.itmo.online.shop.controller;

import com.itmo.online.shop.db.ArticleBuilder;
import com.itmo.online.shop.db.entity.Article;
import com.itmo.online.shop.db.entity.Brand;
import com.itmo.online.shop.db.entity.Category;
import com.itmo.online.shop.db.entity.Size;
import com.itmo.online.shop.exception.ApiException;
import com.itmo.online.shop.service.ArticleService;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/article")
public class ArticleController {

  private final ArticleService articleService;

  public ArticleController(ArticleService articleService) {
    this.articleService = articleService;
  }

  @RequestMapping("/add")
  public String addArticle(Model model) {
    Article article = new Article();
    model.addAttribute("article", article);
    model.addAttribute("allSizes", articleService.getAllSizes());
    model.addAttribute("allBrands", articleService.getAllBrands());
    model.addAttribute("allCategories", articleService.getAllCategories());
    return "addArticle";
  }

  @PostMapping(value = "/add")
  public String addArticlePost(@ModelAttribute("article") Article article,
      HttpServletRequest request) {
    Article newArticle = new ArticleBuilder()
        .withTitle(article.getTitle())
        .stockAvailable(article.getStock())
        .withPrice(article.getPrice())
        .imageLink(article.getPicture())
        .sizesAvailable(Arrays.asList(request.getParameter("size").split("\\s*,\\s*")))
        .ofCategories(Arrays.asList(request.getParameter("category").split("\\s*,\\s*")))
        .ofBrand(Arrays.asList(request.getParameter("brand").split("\\s*,\\s*")))
        .build();
    articleService.saveArticle(newArticle);
    return "redirect:article-list";
  }

  @RequestMapping("/article-list")
  public String articleList(Model model) {
    List<Article> articles = articleService.findAllArticles();
    model.addAttribute("articles", articles);
    return "articleList";
  }

  @RequestMapping("/edit")
  public String editArticle(@RequestParam("id") Long id, Model model) throws ApiException {
    Article article = articleService.findArticleById(id);
    String preselectedSizes = "";
    for (Size size : article.getSizes()) {
      preselectedSizes += (size.getValue() + ",");
    }
    String preselectedBrands = "";
    for (Brand brand : article.getBrands()) {
      preselectedBrands += (brand.getName() + ",");
    }
    String preselectedCategories = "";
    for (Category category : article.getCategories()) {
      preselectedCategories += (category.getName() + ",");
    }
    model.addAttribute("article", article);
    model.addAttribute("preselectedSizes", preselectedSizes);
    model.addAttribute("preselectedBrands", preselectedBrands);
    model.addAttribute("preselectedCategories", preselectedCategories);
    model.addAttribute("allSizes", articleService.getAllSizes());
    model.addAttribute("allBrands", articleService.getAllBrands());
    model.addAttribute("allCategories", articleService.getAllCategories());
    return "editArticle";
  }

  @PostMapping(value = "/edit")
  public String editArticlePost(@ModelAttribute("article") Article article,
      HttpServletRequest request) {
    Article newArticle = new ArticleBuilder()
        .withTitle(article.getTitle())
        .stockAvailable(article.getStock())
        .withPrice(article.getPrice())
        .imageLink(article.getPicture())
        .sizesAvailable(Arrays.asList(request.getParameter("size").split("\\s*,\\s*")))
        .ofCategories(Arrays.asList(request.getParameter("category").split("\\s*,\\s*")))
        .ofBrand(Arrays.asList(request.getParameter("brand").split("\\s*,\\s*")))
        .build();
    newArticle.setId(article.getId());
    articleService.saveArticle(newArticle);
    return "redirect:article-list";
  }

  @RequestMapping("/delete")
  public String deleteArticle(@RequestParam("id") Long id) {
    articleService.deleteArticleById(id);
    return "redirect:article-list";
  }
}