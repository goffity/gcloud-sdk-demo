package com.goffity.demo.gcloud.gcloudsdkdemo.controller;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

@RestController
public class HelloController {

    private Storage storage;

    @RequestMapping("/")
    public String index() {
        return "return index";
    }

    @RequestMapping("/list")
    public String listBucket() throws IOException {

        if (storage == null) {
            Credentials credential = GoogleCredentials.fromStream(new FileInputStream("/Users/goffity/Development/google_cloud_key/goffity google cloud-36fc4c294b60.json"));
            storage = StorageOptions.newBuilder().setCredentials(credential).setProjectId("refined-asset-193707").build().getService();
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Bucket currentBucket : storage.list().iterateAll()) {
            stringBuilder.append(currentBucket).append("\n");
        }

        return stringBuilder.toString();
    }

    @RequestMapping("/create/{name}")
    public String createBucket(@PathVariable(value = "name") String bucketName) throws IOException {

        if (storage == null) {
            Credentials credential = GoogleCredentials.fromStream(new FileInputStream("/Users/goffity/Development/google_cloud_key/goffity google cloud-36fc4c294b60.json"));
            storage = StorageOptions.newBuilder().setCredentials(credential).setProjectId("refined-asset-193707").build().getService();
        }

        bucketName += "-" + new Date().getTime();
        Bucket bucket = storage.create(BucketInfo.of(bucketName));

        return String.format("Bucket %s created", bucket.getName());
    }
}
