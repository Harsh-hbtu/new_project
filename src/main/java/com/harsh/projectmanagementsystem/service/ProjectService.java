package com.harsh.projectmanagementsystem.service;

import com.harsh.projectmanagementsystem.modal.Project;


public interface ProjectService {


     Project createProject(Project project, Long userID)throws Exception;
    
} 
