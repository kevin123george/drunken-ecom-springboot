package com.astra.drunken.services;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;


@Service
public class FireBaseInit {

    @Autowired
    ResourceLoader resourceLoader;

    @PostConstruct
//    @Bean(name = "Firebase")
    public void initialization() {
        try {

//            ClassPathResource res = new ClassPathResource("test-7cf26-firebase-adminsdk-rtyoo-2bdd0489b9.json");
//            ClassLoader classLoader = this.getClass().getClassLoader();
//
//            URLClassLoader urlClassLoader = URLClassLoader.class.cast(classLoader);
//            URL resourceUrl = urlClassLoader.findResource("test-7cf26-firebase-adminsdk-rtyoo-2bdd0489b9.json");

//            FileInputStream serviceAccount =
//                    new FileInputStream(resourceUrl.getPath());
            // the stream holding the file content
            InputStream is = getClass().getClassLoader().getResourceAsStream("test-7cf26-firebase-adminsdk-rtyoo-2bdd0489b9.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(is))
                    .build();

            FirebaseApp.initializeApp(options);

            System.out.println("Firebase app initialized!");
            System.out.println("dsfsdfsdfsdfsdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
