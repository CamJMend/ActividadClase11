package com.example.app.service;

import org.springframework.stereotype.Service;

import com.google.firebase.cloud.FirestoreClient;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Service
public class FirebaseService {

    public List<Map<String,Object>> getAdminFormFields() throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference formCollection = db.collection("forms").document("admin").collection("form");
        
        ApiFuture<QuerySnapshot> querySnapshot = formCollection.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        List<Map<String, Object>> fields = new ArrayList<>();

        for (DocumentSnapshot doc : documents) {
            fields.add(doc.getData()); // Agrega el contenido completo del documento como un mapa clave-valor
        }

        return fields;
    }

    public List<Map<String,Object>> getGuestFormFields() throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference formCollection = db.collection("forms").document("guest").collection("form");
        
        ApiFuture<QuerySnapshot> querySnapshot = formCollection.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        List<Map<String, Object>> fields = new ArrayList<>();

        for (DocumentSnapshot doc : documents) {
            fields.add(doc.getData()); // Agrega el contenido completo del documento como un mapa clave-valor
        }

        return fields;
    }
}