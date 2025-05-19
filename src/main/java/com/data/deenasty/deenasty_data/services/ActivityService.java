package com.data.deenasty.deenasty_data.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.data.deenasty.deenasty_data.models.Activity;
import com.data.deenasty.deenasty_data.repositories.ActivityRepository;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository){
        this.activityRepository = activityRepository;
    }

    public List<Activity> getAllActivities(){
        return activityRepository.findAll();
    }
    
    public Optional<Activity> getActivityById(UUID id) {
        return activityRepository.findById(id);
    }

    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public Optional<Activity> updateActivity(UUID id, Activity activityDetails) {
        return activityRepository.findById(id).map(activity -> {
            activity.setName(activityDetails.getName());
            return activityRepository.save(activity);
        });
    }

    public boolean deleteActivity(UUID id) {
        return activityRepository.findById(id).map(activity -> {
            activityRepository.delete(activity);
            return true;
        }).orElse(false);
    }

}
