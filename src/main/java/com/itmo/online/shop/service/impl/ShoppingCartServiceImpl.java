package com.itmo.online.shop.service.impl;

import com.itmo.online.shop.db.entity.Article;
import com.itmo.online.shop.db.entity.CartItem;
import com.itmo.online.shop.db.ShoppingCart;
import com.itmo.online.shop.db.entity.User;
import com.itmo.online.shop.repository.CartItemRepository;
import com.itmo.online.shop.service.ShoppingCartService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

  private final CartItemRepository cartItemRepository;

  public ShoppingCartServiceImpl(CartItemRepository cartItemRepository) {
    this.cartItemRepository = cartItemRepository;
  }

  @Override
  public ShoppingCart getShoppingCart(User user) {
    return new ShoppingCart(cartItemRepository.findAllByUserAndOrderIsNull(user));
  }

  @Override
  @Cacheable("itemcount")
  public int getItemsNumber(User user) {
    return cartItemRepository.countDistinctByUserAndOrderIsNull(user);
  }

  @Override
  public CartItem findCartItemById(Long cartItemId) {
    return cartItemRepository.getReferenceById(cartItemId);
  }

  @Override
  @CacheEvict(value = "itemcount", allEntries = true)
  public CartItem addArticleToShoppingCart(Article article, User user, int qty, String size) {
    ShoppingCart shoppingCart = this.getShoppingCart(user);
    CartItem cartItem = shoppingCart.findCartItemByArticleAndSize(article.getId(), size);
    if (cartItem != null && cartItem.hasSameSizeThan(size)) {
      cartItem.addQuantity(qty);
    } else {
      cartItem = new CartItem();
      cartItem.setUser(user);
      cartItem.setArticle(article);
      cartItem.setQuantity(qty);
    }
    cartItem.setSize(size);
    return cartItemRepository.save(cartItem);
  }

  @Override
  @CacheEvict(value = "itemcount", allEntries = true)
  public void removeCartItem(CartItem cartItem) {
    cartItemRepository.deleteById(cartItem.getId());
  }

  @Override
  @CacheEvict(value = "itemcount", allEntries = true)
  public void updateCartItem(CartItem cartItem, Integer qty) {
    if (qty == null || qty <= 0) {
      this.removeCartItem(cartItem);
    } else if (cartItem.getArticle().hasStock(qty)) {
      cartItem.setQuantity(qty);
      cartItemRepository.save(cartItem);
    }
  }

  @Override
  @CacheEvict(value = "itemcount", allEntries = true)
  public void clearShoppingCart(User user) {
    cartItemRepository.deleteAllByUserAndOrderIsNull(user);
  }
}
