package com.todo.simple.repositories;

import com.todo.simple.models.Task;
import com.todo.simple.models.projection.TaskProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<TaskProjection> findByUser_Id(Long id);

}
