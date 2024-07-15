package com.todo.simple.services;

import com.todo.simple.models.Task;
import com.todo.simple.models.User;
import com.todo.simple.models.enums.ProfileEnum;
import com.todo.simple.repositories.TaskRepository;
import com.todo.simple.security.UserSpringSecurity;
import com.todo.simple.services.exceptions.AuthorizationException;
import com.todo.simple.services.exceptions.DataBindingViolationException;
import com.todo.simple.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {
        Task task = this.taskRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
            "Task not found! Id: " + id + ", Type: " + Task.class.getName()
        ));

        UserSpringSecurity userSpringSecurity = UserService.authenticated();

        // não esta logado OU não é admin E a tarefa não é do usuário
        if(Objects.isNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !userHasTask(userSpringSecurity, task)) {
            throw new AuthorizationException("Access denied");
        }

        return task;
    }

    public List<Task> findAllByUser() {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if(Objects.isNull(userSpringSecurity)) {
            throw new AuthorizationException("Access denied");
        }

        List<Task> tasks = this.taskRepository.findByUser_Id(userSpringSecurity.getId());
        return tasks;

    }

    @Transactional
    public Task create(Task obj) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if(Objects.isNull(userSpringSecurity)) {
            throw new AuthorizationException("Access denied");
        }

        User user = userService.findById(userSpringSecurity.getId());

        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj) {
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
        }
    }

    // retorna se o usuario logado tem a tarefa - verifica se a task é do usuário
    private Boolean userHasTask(UserSpringSecurity userSpringSecurity, Task task) {
        return task.getUser().getId().equals(userSpringSecurity.getId());
    }

}
