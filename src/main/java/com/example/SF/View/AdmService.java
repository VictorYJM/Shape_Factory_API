package com.example.SF.View;

import com.example.SF.Model.Adm;
import com.example.SF.Repository.AdmRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdmService {
    private final AdmRepository admRepository;

    @Autowired
    public AdmService(AdmRepository admRepository) {
        this.admRepository = admRepository;
    }

    public List<Adm> getAll() {
        try {
            return admRepository.getAll();
        }

        catch (Exception e) {
            System.out.println("Cannot get adms: " + e.getMessage());
            return List.of();
        }
    }

    public boolean verify(String email, String password) {
        try {
            return admRepository.verify(email, password);
        }

        catch (Exception e) {
            System.out.println("Cannot verify adm: " + e.getMessage());
            return false;
        }
    }

    @Transactional
    public Adm add(String email, String password, Double salary) {
        try {
            Adm adm = new Adm();
            adm.setAdmEmail(email);
            adm.setAdmPassword(password);
            adm.setAdmSalary(salary);
            adm.setAdmActive(true);

            return admRepository.save(adm);
        }

        catch (DataIntegrityViolationException e) {
            System.out.println("Email already exists");
            return null;
        }

        catch (Exception e) {
            System.out.println("Cannot add adm: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    public void updateSalary(UUID id, Double salary) {
        try {
            admRepository.updateSalary(id, salary);
        }

        catch (Exception e) {
            System.out.println("Cannot change adm's salary: " + e.getMessage());
        }
    }

    @Transactional
    public void updatePassword(UUID id, String password) {
        try {
            admRepository.updatePassword(id, password);
        }

        catch (Exception e) {
            System.out.println("Cannot change adm's password: " + e.getMessage());
        }
    }

    @Transactional
    public void exclude(UUID id) {
        try {
            admRepository.exclude(id);
        }

        catch (Exception e) {
            System.out.println("Cannot exclude adm: " + e.getMessage());
        }
    }
}
