package com.example.app.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.InputStream;

@Configuration
public class FirebaseInitializer {

    @PostConstruct
    public void initialize() {
        try {
            // Cargar el archivo desde /resources
            InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("firebase-token.json");

            if (serviceAccount == null) {
                throw new IllegalStateException("No se pudo encontrar firebase-token.json en resources");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("✅ Firebase inicializado correctamente");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al inicializar Firebase", e);
        }
    }
}
