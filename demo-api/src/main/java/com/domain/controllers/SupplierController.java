package com.domain.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.dto.ResponseData;
import com.domain.dto.SearchData;
import com.domain.dto.SupplierData;
import com.domain.models.entities.Supplier;
import com.domain.services.SupplierService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ModelMapper modelMapper;

    // Endpoint untuk membuat Supplier baru
    @PostMapping
    public ResponseEntity<ResponseData<Supplier>> create(@Valid @RequestBody SupplierData supplierData, Errors errors) {

        ResponseData<Supplier> responseData = new ResponseData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Supplier supplier = modelMapper.map(supplierData, Supplier.class);
        responseData.setStatus(true);
        responseData.setPayload(supplierService.save(supplier));
        return ResponseEntity.ok(responseData);
    }
    // Endpoint untuk menampilkan seluruh supplier
    @GetMapping
    public Iterable<Supplier> findAll(){
        return supplierService.findAll();
    }

    // Endpoint untuk menampilkan supplier berdasarkan id nya
    @GetMapping("/{id}")
    public Supplier findOne(@PathVariable("id") Long id){
        return supplierService.findOne(id);
    }
    
    // Endpoint untuk update data supplier
    @PutMapping
    public ResponseEntity<ResponseData<Supplier>> update(@Valid @RequestBody SupplierData supplierData, Errors errors) {

        ResponseData<Supplier> responseData = new ResponseData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        // ADA 2 CARA PEMETAAN DATA : 
        
        // Supplier supplier = new Supplier();
        // supplier.setName(supplierData.getName());
        // supplier.setAddress(supplierData.getAddress());
        // supplier.setEmail(supplierData.getEmail());

        Supplier supplier = modelMapper.map(supplierData, Supplier.class);
        responseData.setStatus(true);
        responseData.setPayload(supplierService.save(supplier));
        return ResponseEntity.ok(responseData);
    }

    // Endpoint untuk mencari supplier dari email nya
    @PostMapping("/search/byemail")
    public Supplier getSupplierByEmail(@RequestBody SearchData searchData){
        return supplierService.findByEmail(searchData.getSearchKey());
    }

    // Endpoint untuk mencari supplier lewat email juga tetapi email yang dicari tidak harus lengkap
    @PostMapping("/search/byemaillike")
    public List<Supplier> getSupplierByEmailLike(@RequestBody SearchData searchData){
        return supplierService.findSupplierByEmailLike(searchData.getSearchKey());
    }

    // Endpoint untuk mencari supplier dari namanya
    @PostMapping("/search/byname")
    public List<Supplier> getByNameContains(@RequestBody SearchData searchData){
        return supplierService.findByNameContais(searchData.getSearchKey());
    }

    // Endpoint untuk mencari supplier dari nama awalnya saja
    @PostMapping("/search/namestarswith")
    public List<Supplier> getNameStartsWith(@RequestBody SearchData searchData){
        return supplierService.findByNameStartsWith(searchData.getSearchKey());
    }

    // Endpoint untuk mencari supplier dari nama atau emailnya 
    @PostMapping("/search/bynameoremail")
    public List<Supplier> getByNameOrEmail(@RequestBody SearchData searchData){
        return supplierService.findByNameContainsOrEmailContains(searchData.getSearchKey(), searchData.getOtherSearchKey());
    }

    // Endpoint untuk mencari supplier dari nama secara descending
    @PostMapping("/search/bynamedescending")
    public List<Supplier> getByNameDesc(@RequestBody SearchData searchData){
        return supplierService.findByNameContainsOrderByIdDesc(searchData.getSearchKey());
    }
}
