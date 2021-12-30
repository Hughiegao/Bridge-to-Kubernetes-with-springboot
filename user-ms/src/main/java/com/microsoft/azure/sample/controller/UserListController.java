/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.sample.controller;

import com.microsoft.azure.sample.dao.UserItemRepository;
import com.microsoft.azure.sample.model.TodoItem;
import com.microsoft.azure.sample.model.UserItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
public class UserListController {

    @Autowired
    private UserItemRepository todoItemRepository;

    public UserListController() {
    }

    /**
     * HTTP GET
     */
    @RequestMapping(value = "/api/userlist/{index}",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getTodoItem(@PathVariable("index") String index) {
        try {
            return new ResponseEntity<UserItem>(todoItemRepository.findById(index).get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(index + " not found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * HTTP GET ALL
     */
    @RequestMapping(value = "/api/userlist", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllTodoItems() {
        try {
            /*
            List<UserItem> mylist = new ArrayList<>();
                UserItem item = new UserItem("123","not a name","not a role");
                mylist.add(item);
            return new ResponseEntity<>(mylist, HttpStatus.OK);
            */
            return new ResponseEntity<>(todoItemRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Nothing found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * HTTP POST NEW ONE
     */
    @RequestMapping(value = "/api/userlist", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addNewTodoItem(@RequestBody UserItem item) {
        try {
            item.setID(UUID.randomUUID().toString());
            todoItemRepository.save(item);
            return new ResponseEntity<String>("Entity created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("Entity creation failed", HttpStatus.CONFLICT);
        }
    }

    /**
     * HTTP PUT UPDATE
     */
    @RequestMapping(value = "/api/userlist", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTodoItem(@RequestBody UserItem item) {
        try {
            todoItemRepository.deleteById(item.getID());
            todoItemRepository.save(item);
            return new ResponseEntity<String>("Entity updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Entity updating failed", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * HTTP DELETE
     */
    @RequestMapping(value = "/api/userlist/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteTodoItem(@PathVariable("id") String id) {
        try {
            todoItemRepository.deleteById(id);
            return new ResponseEntity<String>("Entity deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Entity deletion failed", HttpStatus.NOT_FOUND);
        }

    }
    
    /**
     * HTTP GET ALL todos
     */
    
    @Value("#{environment.URI_TODOS}")
    private String URI_TODOS;
    
    @RequestMapping(value = "/api/userlist/getalltodos", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getTodoItem() {
        try {
            System.out.println(URI_TODOS);
            RestTemplate restTemplate = new RestTemplate();          
            return new ResponseEntity<>(restTemplate.getForObject(URI_TODOS, TodoItem[].class), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Nothing found", HttpStatus.NOT_FOUND);
        }
    }
}
