package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class KoalaController {
    private Map<Integer, Koala> koalas;

    @PostConstruct
    public void init(){
        koalas = new HashMap<>();
        koalas.put(1, new Koala(1, "Kara", 20.0, 15.0, "Female"));
        koalas.put(2, new Koala(2, "Sara", 19.0, 17.0, "Male"));
    }

    @GetMapping("/koalas")
    public List<Koala> findAll(){
        return  koalas.values().stream().toList();
    }

    @GetMapping("/koalas/{id}")
    public Koala findById(@PathVariable int id){
        Koala koala = koalas.get(id);
        if(koala == null){
            throw new ZooException("Kangaroo with id " + id + " not found!", HttpStatus.NOT_FOUND);
        }
        return koala;
    }

    @PostMapping("/koalas")
    public Koala save(@RequestBody Koala koala){
        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping("/koalas/{id}")
    public Koala update(@PathVariable int id, @RequestBody Koala koala){
        if(koalas.get(id) == null){
            throw new ZooException("Kangaroo with id " + id + " not found!", HttpStatus.NOT_FOUND);
        }
        koalas.put(id, new Koala(id,koala.getName(), koala.getWeight(), koala.getSleepHour(), koala.getGender()));
        return koalas.get(id);
    }

    @DeleteMapping("/koalas/{id}")
    public Koala delete(@PathVariable int id){
        Koala koala = koalas.get(id);
        if(koala == null){
            throw new ZooException("Kangaroo with id " + id + " not found!", HttpStatus.NOT_FOUND);
        }
        koalas.remove(koala);
        return koala;
    }
}
