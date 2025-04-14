package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class KangarooController {
    private Map<Integer, Kangaroo> kangaroos;

    @PostConstruct
    public void init(){
        kangaroos = new HashMap<>();
        kangaroos.put(1, new Kangaroo(1, "Kenny", 2.0, 85.0, "Male", false));
        kangaroos.put(2, new Kangaroo(2, "Lenny", 2.1, 83.0, "Female", true));
    }

    @GetMapping("/kangaroos")
    public List<Kangaroo> findAll(){
        return  kangaroos.values().stream().toList();
    }

    @GetMapping("/kangaroos/{id}")
    public Kangaroo findById(@PathVariable int id){
        Kangaroo kangaroo = kangaroos.get(id);
        if(kangaroo == null){
            throw new ZooException("Kangaroo with id " + id + " not found!", HttpStatus.NOT_FOUND);
        }
        return kangaroo;
    }

    @PostMapping("/kangaroos")
    public Kangaroo save(@RequestBody Kangaroo kangaroo){
        if (kangaroo.getName() == null || kangaroo.getName().isBlank()) {
            throw new ZooException("Name cannot be blank", HttpStatus.BAD_REQUEST);
        }

        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;
    }

    @PutMapping("/kangaroos/{id}")
    public Kangaroo update(@PathVariable int id, @RequestBody Kangaroo kangaroo){
        if(kangaroos.get(id) == null){
            throw new ZooException("Kangaroo with id " + id + " not found!", HttpStatus.NOT_FOUND);
        }
        kangaroos.put(id, new Kangaroo(id,kangaroo.getName(), kangaroo.getHeight(), kangaroo.getWeight(), kangaroo.getGender(), kangaroo.getIsAggressive()));
        return kangaroos.get(id);
    }

    @DeleteMapping("/kangaroos/{id}")
    public Kangaroo delete(@PathVariable int id){
        Kangaroo kangaroo = kangaroos.get(id);
        if(kangaroo == null){
            throw new ZooException("Kangaroo with id " + id + " not found!", HttpStatus.NOT_FOUND);
        }
        kangaroos.remove(kangaroo);
        return kangaroo;
    }
}
