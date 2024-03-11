package com.heisyenberg.springbookshop.controllers;

import com.heisyenberg.springbookshop.models.CartItem;
import com.heisyenberg.springbookshop.models.User;
import com.heisyenberg.springbookshop.services.CartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CartItemsController {
    private final CartItemsService cartItemsService;

    @Autowired
    public CartItemsController(CartItemsService cartItemsService) {
        this.cartItemsService = cartItemsService;
    }

    @GetMapping("/cart")
    public String getCart(HttpSession session, Model model,
                          @AuthenticationPrincipal User user) {
        session.setAttribute("cartItemsCount",
                cartItemsService.getCartItemsCount(user));
        List<CartItem> cartItems = cartItemsService.getCartItems(user);
        model.addAttribute("cartItems", cartItems);
        double totalSum = cartItems.stream()
                .mapToDouble(
                        item -> item.getBook().getPrice() * item.getQuantity())
                .sum();
        model.addAttribute("totalSum", totalSum);
        return "/cart";
    }

    @PostMapping("/addToCart/{item}")
    public String addToCart(@PathVariable("item") Long item,
                            @AuthenticationPrincipal User user) {
        cartItemsService.addItem(user, item);
        return "redirect:/books";
    }

    @PostMapping("/updateCartItemQuantity")
    public String updateCartItemQuantity(@RequestParam("bookId") Long bookId,
                                         @RequestParam("action") String action,
                                         @AuthenticationPrincipal User user) {
        cartItemsService.updateItemQuantity(user, bookId, action);
        return "redirect:/cart";
    }

    @PostMapping("/removeFromCart/{item}")
    public String removeFromCart(@PathVariable("item") Long item,
                                 @AuthenticationPrincipal User user) {
        cartItemsService.removeItem(user, item);
        return "redirect:/cart";
    }
}
