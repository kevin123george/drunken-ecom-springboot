package com.astra.drunken.services;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobTargetOption;
import com.google.cloud.storage.Storage.PredefinedAcl;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;


//this work we need to pass file now
@Service
public class GoogleCloudFileUpload {

    // get service by env var GOOGLE_APPLICATION_CREDENTIALS. Json file generated in API & Services -> Service account key
    private static final Storage storage = StorageOptions.getDefaultInstance().getService();
    private static final Storage storagse = StorageOptions.getDefaultInstance().getService();

//    public String upload(String fileName) {
//        try {
//            File file = new File("pdf/thymeleaf.pdf");
//            byte[] fileContent = Files.readAllBytes(file.toPath());
//            BlobInfo blobInfo = storage.create(
//                    BlobInfo.newBuilder("dsam-pdf", "receipt/"+fileName+".pdf").build(),fileContent
//            );
//            storage.get("dsam-pdf", "receipt/"+fileName+".pdf");
//            storage.reader(BlobId.of("dsam-pdf", "receipt/"+fileName+".pdf"));
//            return blobInfo.getMediaLink(); // Return file url
//        }catch(IllegalStateException e){
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }



    public String uploadByte(byte[] fileContent,String fileName) {
        try {
            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder("dsam-pdf", "receipt/"+fileName+".pdf").build(),fileContent
            );
            storage.get("dsam-pdf", "receipt/"+"ktestbyte"+".pdf");
            storage.reader(BlobId.of("dsam-pdf", "receipt/"+"ktestbyte"+".pdf"));
            return blobInfo.getMediaLink(); // Return file url
        }catch(IllegalStateException e){
            throw new RuntimeException(e);
        }
    }

}
