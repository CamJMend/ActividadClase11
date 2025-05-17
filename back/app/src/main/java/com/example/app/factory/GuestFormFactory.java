package com.example.app.factory;

import com.example.app.model.FormFactory;
import com.example.app.model.GenericField;
import com.example.app.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GuestFormFactory implements FormFactory {

    private final FirebaseService firebaseService;

    @Autowired
    public GuestFormFactory(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @Override
    public List<FormField> createFormFields() {
        List<FormField> fields = new ArrayList<>();

        try {
            List<Map<String, Object>> firestoreFields = firebaseService.getGuestFormFields();

            for (Map<String, Object> fieldData : firestoreFields) {
                String type = (String) fieldData.get("type");
                String label = (String) fieldData.get("label");
                Boolean required = (Boolean) fieldData.get("required");

                if (type != null && label != null && required != null) {
                    fields.add(new GenericField(type, label, required));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fields;
    }
}
