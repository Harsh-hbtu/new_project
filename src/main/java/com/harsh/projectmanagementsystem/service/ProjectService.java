package com.harsh.projectmanagementsystem.service;

import java.util.*;

import com.harsh.projectmanagementsystem.modal.Project;

import com.harsh.projectmanagementsystem.modal.User;
import com.harsh.projectmanagementsystem.modal.Chat;


public interface ProjectService {


     Project createProject(Project project, User user)throws Exception;

     List<Project>getProjectByTeam(User user, String category, String tag) throws Exception;

     Project getProjectById(Long ProjectId) throws Exception;

     void deleteProject(Long projectId, Long userId) throws Exception;

     Project updateProject(Project updatedProject, Long id) throws Exception;

     Void addUserToProject(Long projectId, Long userId) throws Exception;

     void removeUserFromProject(Long projectId, Long userId) throws Exception;

     Chat getChatByProjectId(Long projectId) throws Exception;


    
} 
