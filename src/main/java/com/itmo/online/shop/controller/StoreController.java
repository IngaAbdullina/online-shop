package com.itmo.online.shop.controller;

import com.itmo.online.shop.db.entity.Article;
import com.itmo.online.shop.db.entity.Brand;
import com.itmo.online.shop.form.ArticleFilterForm;
import com.itmo.online.shop.service.ArticleService;
import com.itmo.online.shop.type.SortFilter;
import java.util.stream.Collectors;
import javax.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StoreController {

  private final ArticleService articleService;

  public StoreController(ArticleService articleService) {
    this.articleService = articleService;
  }

  @RequestMapping("/store")
  public String store(@ModelAttribute("filters") ArticleFilterForm filters, Model model) {
    Integer page = filters.getPage();
    int pageNumber = (page == null || page <= 0) ? 0 : page - 1;
    SortFilter sortFilter = new SortFilter(filters.getSort());
    Page<Article> pageResult = articleService.findArticlesByCriteria(
        PageRequest.of(pageNumber, 9, sortFilter.getSortType()),
        filters.getPriceLow(), filters.getPriceHigh(),
        filters.getSize(), filters.getCategory(), filters.getBrand(), filters.getSearch());
    model.addAttribute("allCategories", articleService.getAllCategories());
    model.addAttribute("allBrands", articleService.getAllBrands());
    model.addAttribute("allSizes", articleService.getAllSizes());
    model.addAttribute("articles", pageResult.getContent());
    model.addAttribute("totalitems", pageResult.getTotalElements());
    model.addAttribute("itemsperpage", 9);
    return "store";
  }


  @RequestMapping("/article-detail")
  public String articleDetail(@PathParam("id") Long id, Model model) {
    Article article = articleService.findArticleById(id);
    String brands = article.getBrands().stream()
        .map(brand -> String.format("%s", brand.getName()))
        .collect(Collectors.joining(", "));
    String categories = article.getCategories().stream()
        .map(category -> String.format("%s", category.getName()))
        .collect(Collectors.joining(", "));
    model.addAttribute("article", article);
    model.addAttribute("notEnoughStock", model.asMap().get("notEnoughStock"));
    model.addAttribute("addArticleSuccess", model.asMap().get("addArticleSuccess"));
    model.addAttribute("brands", brands);
    model.addAttribute("categories", categories);
    return "articleDetail";
  }
}
