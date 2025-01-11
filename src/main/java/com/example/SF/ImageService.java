package com.example.SF;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class ImageService {
    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.apiKey}")
    private String supabaseApiKey;

    @Value("${supabase.bucket.name}")
    private String bucketName;

    public String uploadImage(MultipartFile imageFile) {
        String fileName = imageFile.getOriginalFilename();
        String imageUrl = supabaseUrl + "/storage/v1/object/" + bucketName + "/" + fileName;

        try {
            OkHttpClient client = new OkHttpClient();

            // Verifica se a imagem já existe
            Request checkRequest = new Request.Builder()
                    .url(imageUrl)
                    .addHeader("Authorization", "Bearer " + supabaseApiKey)
                    .head() // Usamos HEAD para apenas verificar a existência
                    .build();

            try (Response checkResponse = client.newCall(checkRequest).execute()) {
                if (checkResponse.isSuccessful()) {
                    // A imagem já existe, retorne a URL
                    return imageUrl;
                }
            }

            // Se não existe, faz o upload da nova imagem
            File tempFile = File.createTempFile("upload-", fileName);
            imageFile.transferTo(tempFile);

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", fileName,
                            RequestBody.create(tempFile, MediaType.parse(imageFile.getContentType())))
                    .build();

            Request uploadRequest = new Request.Builder()
                    .url(imageUrl)
                    .addHeader("Authorization", "Bearer " + supabaseApiKey)
                    .post(requestBody)
                    .build();

            try (Response uploadResponse = client.newCall(uploadRequest).execute()) {
                if (!uploadResponse.isSuccessful()) {
                    System.out.println("Upload failed: " + uploadResponse.message());
                    return "";
                }
                return imageUrl; // Retorna a URL após o upload
            }

        }

        catch (Exception e) {
            System.out.println("Cannot insert image: " + e.getMessage());
            return "";
        }
    }

    public void deleteImageFromBucket(String imageUrl) {
        try {
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            OkHttpClient client = new OkHttpClient();

            Request deleteRequest = new Request.Builder()
                    .url(supabaseUrl + "/storage/v1/object/" + bucketName + "/" + fileName)
                    .addHeader("Authorization", "Bearer " + supabaseApiKey)
                    .delete()
                    .build();

            try (Response response = client.newCall(deleteRequest).execute()) {
                if (!response.isSuccessful()) {
                    System.out.println("Failed to delete image: " + response.message());
                }
            }
        }

        catch (Exception e) {
            System.out.println("Cannot delete image from bucket");
        }
    }
}