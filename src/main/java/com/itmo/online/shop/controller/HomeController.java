package com.itmo.online.shop.controller;

import com.itmo.online.shop.db.entity.Article;
import com.itmo.online.shop.service.ArticleService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

  private final ArticleService articleService;

  public HomeController(ArticleService articleService) {
    this.articleService = articleService;
  }

  @RequestMapping("/")
  public String index(Model model) {
    List<Article> articles = articleService.findFirstArticles();
    model.addAttribute("articles", articles);
    return "index";
  }
}
