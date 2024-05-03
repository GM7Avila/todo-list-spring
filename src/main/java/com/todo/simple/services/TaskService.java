package com.todo.simple.services;

import com.todo.simple.models.Task;
import com.todo.simple.models.User;
import com.todo.simple.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new RuntimeException(
                "User not found! Id: " + id + ", type: " + Task.class.getName() + "."
        ));
    }

    @Transactional
    public Task create(Task obj){
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        // obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj){
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete (Long id){
        findById(id);
        try{
            this.taskRepository.deleteById(id);
        } catch(Exception e){
            throw new RuntimeException("Não foi possível excluir - existem entidades relacionadas!");
        }
    }
}
