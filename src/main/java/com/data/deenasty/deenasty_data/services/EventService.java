package com.data.deenasty.deenasty_data.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.data.deenasty.deenasty_data.models.Event;
import com.data.deenasty.deenasty_data.repositories.EventRepository;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(UUID id) {
        return eventRepository.findById(id);
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Optional<Event> updateEvent(UUID id, Event eventDetails) {
        return eventRepository.findById(id).map(event -> {
            event.setDescription(eventDetails.getDescription());
            event.setStartYear(eventDetails.getStartYear());
            event.setEndYear(eventDetails.getEndYear());
            return eventRepository.save(event);
        });
    }

    public boolean deleteEvent(UUID id) {
        return eventRepository.findById(id).map(event -> {
            eventRepository.delete(event);
            return true;
        }).orElse(false);
    }
    
}
