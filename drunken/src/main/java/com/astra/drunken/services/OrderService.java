package com.astra.drunken.services;

import com.astra.drunken.models.AddressType;
import com.astra.drunken.models.Order;
import com.astra.drunken.models.OrderItem;
import com.astra.drunken.models.User;
import com.astra.drunken.repositories.OrderRepo;
import com.astra.drunken.repositories.UserRepo;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.lowagie.text.DocumentException;
import okhttp3.*;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
//@DependsOn(value = "Firebase")
public class OrderService {
    private static final Gson gson = new Gson();
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final GoogleCloudFileUpload googleCloudFileUpload;

    public OrderService(OrderRepo orderRepo, UserRepo userRepo, GoogleCloudFileUpload googleCloudFileUpload) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.googleCloudFileUpload = googleCloudFileUpload;
    }

    public Optional<Order> getOrderByUserAndActive(Authentication authentication) {
        var user = userRepo.findByUserName(authentication.getName());
        var order = orderRepo.findByUserAndIsActive(user.get(), true);
        if (order.get().size() == 0) {
            var newOrder = new Order();
            newOrder.setUser(user.get());
            return Optional.of(orderRepo.save(newOrder));
        }
        return order.get().stream().findFirst();
    }

    public List<Order> getAllOders() {
        var order = orderRepo.findByIsActive(false);
        return order.get();
    }

    public List<Order> getOrderByUserAndNotActive(Authentication authentication) {
        var user = userRepo.findByUserName(authentication.getName());
        return orderRepo.findByUserAndIsActive(user.get(), false).get();
    }


    public void checkoutOrder(Authentication authentication, Long orderId) throws ExecutionException, InterruptedException, DocumentException, IOException {
        var order = orderRepo.findById(orderId);
        processOrder(authentication, orderId);
        order.get().setIsActive(false);

         FirebaseApp.getApps();
        Firestore dbFireStore = FirestoreClient.getFirestore();
        var hashList = new LinkedHashMap<>();
        hashList.put("orderTotal", order.get().getPrice());
        hashList.put("userId", order.get().getUser().getId());
        hashList.put("itemCount", order.get().getOrderItems().size());
        hashList.put("postalCode", order.get().getUser().getAddress().stream().findFirst().get().getPostalCode());
        List<String> products = new ArrayList<>();
        order.get().getOrderItems().forEach(orderItem -> {
            products.add(orderItem.getBeverage().getId().toString());
        });
        var sourceSet = order.get().getOrderItems();
        List<OrderItem> targetList = new ArrayList<>();
        hashList.put("orderItem", products);
        ApiFuture<WriteResult> collectionApiFuture = dbFireStore.collection("orders").document(order.get().getId().toString()).set(hashList);


        var data = parseThymeleafTemplate(order.get());
        var fileNamek = order.get().getId().toString() + "_" + "bill";
        generatePdfFromHtml(data, fileNamek);


        orderRepo.save(order.get());
        String a = "Cool";
        a = a.replace("\"","");
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n\t\"email\": \"kevinatde@gmail.com\",\n\t\"filename\":\""+fileNamek+"\"\n}");
        System.out.println("sss");
        System.out.println("{\n\t\"email\": \"kevinatde@gmail.com\",\n\t\"filename\":\""+fileNamek+"\"\n}");
        Request request = new Request.Builder()
                .url("https://europe-west3-dsam-drunken.cloudfunctions.net/rest-end-gen-pdf")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println("-------------------------------");
        System.out.println(response);
        System.out.println("-------------------------------");
        //upload to google storage
//        googleCloudFileUpload.upload(order.get().getUser().getId().toString()+"_"+order.get().getId().toString());
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
        if (address.size() > 0) {
            address.stream().forEach(address1 -> {
                if (address1.getAddressType() == AddressType.billing) {
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
        if (address.size() > 0) {
            address.stream().forEach(address1 -> {
                if (address1.getAddressType() == AddressType.delivery) {
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


    private String parseThymeleafTemplate(Order order) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);


        Context context = new Context();
        context.setVariable("order", order);
//        model.addAttribute("address", userService.getUserAddressTo(authentication));
        context.setVariable("userInfo", null);

        return templateEngine.process("pdfgen.html", context);
    }

    public void generatePdfFromHtml(String html, String fileName) throws IOException, DocumentException {
//        String outputFolder = "./pdf" + File.separator + "thymeleaf.pdf";
//        OutputStream outputStream = new FileOutputStream(outputFolder);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        renderer.createPDF(baos);

        byte[] byteArray = baos.toByteArray();
        googleCloudFileUpload.uploadByte(byteArray, fileName);
        baos.close();
//        outputStream.close();
    }

}
