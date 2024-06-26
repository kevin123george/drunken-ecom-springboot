package com.astra.drunken.services;

import com.astra.drunken.models.AddressType;
import com.astra.drunken.models.Order;
import com.astra.drunken.models.User;
import com.astra.drunken.repositories.OrderRepo;
import com.astra.drunken.repositories.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;

    public OrderService(OrderRepo orderRepo, UserRepo userRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    public Optional<Order> getOrderByUserAndActive(Authentication authentication) {
        var user = userRepo.findByUserName(authentication.getName());
        var order = orderRepo.findByUserAndIsActive(user.get(), true);
        if (order.get().size() ==0) {
            var newOrder = new Order();
            newOrder.setUser(user.get());
            return Optional.of(orderRepo.save(newOrder));
        }
        return order.get().stream().findFirst();
    }

    public List<Order> getOrderByUserAndNotActive(Authentication authentication) {
        var user = userRepo.findByUserName(authentication.getName());
        return orderRepo.findByUserAndIsActive(user.get(), false).get();
    }


    public void checkoutOrder(Authentication authentication, Long orderId) {
        var order = orderRepo.findById(orderId);
        processOrder(authentication, orderId);
        order.get().setIsActive(false);
        orderRepo.save(order.get());
    }

    /**
     //     * @param authentication
     //     * @return
     //     */
//    public Optional<Address> usersAddress(Authentication authentication){
//        var user = userRepo.findByUserName(authentication.getName());
//        return Optional.ofNullable(user.get().getAddress());
//    }

    /**
     * I check if the customer already have a active Cart if so return else return new Cart.
     *
     * @param user - the current user
     * @return Cart
     */
    public Order getOrderByUserAndActive(User user) {
        var oldOrder = orderRepo.findByUserAndIsActive(user, true);
        if (oldOrder.get().size() != 0) {
            return oldOrder.get().stream().findFirst().get();
        } else {
            var newOrder = new Order();
            newOrder.setUser(user);
            return orderRepo.save(newOrder);
//            return newOrder;
        }
    }


    public void processOrder(Authentication authentication, Long orderId) {
        var user = userRepo.findByUserName(authentication.getName());
        var order = orderRepo.findById(orderId);
        var address = user.get().getAddress();
        order.get().setIsActive(false);
        if (address == null) {
            throw new RuntimeException("address cannot be empty");
        }
        orderRepo.save(order.get());
    }

    public Boolean doIhaveAnyAddress(Authentication authentication) {
        var user = userRepo.findByUserName(authentication.getName());
        var address = user.get().getAddress();
        if (address == null) {
            return false;
        }
        return true;
    }

    public AtomicBoolean doIhaveBillingAddress(Authentication authentication) {
        var user = userRepo.findByUserName(authentication.getName());
        var address = user.get().getAddress();
        AtomicBoolean flag = new AtomicBoolean(false);
        if (address.size() > 0 ) {
            address.stream().forEach(address1 -> {
                if(address1.getAddressType() == AddressType.billing){
                    flag.set(true);
                }
            });
        }
        return flag;
    }

    public AtomicBoolean doIhaveDeliveryAddress(Authentication authentication) {
        var user = userRepo.findByUserName(authentication.getName());
        var address = user.get().getAddress();
        AtomicBoolean flag = new AtomicBoolean(false);
        if (address.size() > 0 ) {
            address.stream().forEach(address1 -> {
                if(address1.getAddressType() == AddressType.delivery){
                    flag.set(true);
                }
            });
        }
        return flag;
    }

    @Transactional
    public void savOrder(Order order) {
        orderRepo.save(order);
    }


}
