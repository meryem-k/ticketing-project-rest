package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserService userService;

    public TaskController(TaskService taskService, ProjectService projectService, UserService userService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping
    @RolesAllowed({"Manager"})

    public ResponseEntity<ResponseWrapper> getTask() {
        List<TaskDTO> taskDTOList = taskService.listAllTasks();
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved", taskDTOList, HttpStatus.OK));
    }

    @GetMapping("/{taskId}")
    @RolesAllowed({"Manager"})

    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable("taskId") Long taskId){

        TaskDTO taskDTO = taskService.findById(taskId);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully retrieved",taskDTO, HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed({"Manager"})

    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO taskDTO){
        taskService.save(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper("Task successfully created",HttpStatus.CREATED));
    }

    @DeleteMapping("/{taskId}")
    @RolesAllowed({"Manager"})

    public ResponseEntity<ResponseWrapper> deleteTask(@PathVariable("taskId") Long taskId){

        taskService.delete(taskId);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully deleted", HttpStatus.OK));

    }

    @PutMapping
    @RolesAllowed({"Manager"})

    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully updated",HttpStatus.OK));
    }


    @GetMapping("/employee/pending-tasks")
    @RolesAllowed({"Eployee"})
    public ResponseEntity<ResponseWrapper> employeePendingTasks(){
        List<TaskDTO> taskDTOList = taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved", taskDTOList, HttpStatus.OK));
    }


    @PutMapping("/employee/update")
    @RolesAllowed("Eployee")
    public ResponseEntity<ResponseWrapper> employeeUpdateTasks(@RequestBody TaskDTO task){
        taskService.update(task);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully updated",HttpStatus.OK));
    }


    @GetMapping("/employee/archive")
    @RolesAllowed("Eployee")
    public ResponseEntity<ResponseWrapper> employeeArchivedTasks() {

       List<TaskDTO> taskDTOList = taskService.listAllTasksByStatus(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved", taskDTOList, HttpStatus.OK));
    }

















}