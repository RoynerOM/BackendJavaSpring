package com.example.demo.Controllers;

import com.example.demo.Models.Resource;
import com.example.demo.Services.ResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class ResourceController {
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/resource")
    public ResponseEntity<List<Resource>> findAll(){
        return ResponseEntity.ok().body(resourceService.findAll());
    }

    @PostMapping("/resource")
    public ResponseEntity<Resource> save(@RequestBody @Valid Resource Resource){
        return ResponseEntity.ok().body(resourceService.save(Resource));
    }

    @GetMapping("/resource/{id}")
    public ResponseEntity<Resource> findById(@PathVariable long id)throws Exception{
        return ResponseEntity.ok().body(resourceService.findById(id));
    }

    @PutMapping("/resource")
    public ResponseEntity<Resource> update(@RequestBody Resource Resource){
        return ResponseEntity.ok().body(resourceService.update(Resource));
    }

    @DeleteMapping("/resource/{id}")
    public ResponseEntity<Resource> delete(@PathVariable long id)throws Exception{
        resourceService.delete(id);
        return ResponseEntity.ok().build();
    }
}
