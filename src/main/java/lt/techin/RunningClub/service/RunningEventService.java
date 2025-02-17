package lt.techin.RunningClub.service;

import lt.techin.RunningClub.model.RunningEvent;
import lt.techin.RunningClub.repository.RunningEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunningEventService {
    RunningEventRepository runningEventRepository;

    @Autowired
    public RunningEventService(RunningEventRepository runningEventRepository) {
        this.runningEventRepository = runningEventRepository;
    }

    public List<RunningEvent> findAllEvents() {
        return runningEventRepository.findAll();
    }

    public RunningEvent saveEvent(RunningEvent runningEvent) {
        return runningEventRepository.save(runningEvent);
    }
}
