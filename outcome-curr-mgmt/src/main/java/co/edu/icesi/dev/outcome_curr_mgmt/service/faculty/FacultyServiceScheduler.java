package co.edu.icesi.dev.outcome_curr_mgmt.service.faculty;

import co.edu.icesi.dev.outcome_curr.mgmt.model.stdoutdto.faculty.FacultyOutDTO;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyServiceScheduler {
    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceScheduler.class);
    private final FacultyService facultyService;

    // Cada día
    @Scheduled(cron = "0 0 0 * * ?")
    @SchedulerLock(name = "FacultyServiceScheduler_cleanInactiveFaculties", lockAtMostFor = "5m", lockAtLeastFor = "2m")
    public void cleanInactiveFaculties() {
        logger.info("Scheduled task: Cleaning inactive faculties.");
        List<FacultyOutDTO> faculties = facultyService.getFaculties();
        faculties.stream()
                .filter(faculty -> faculty.facIsActive() == 'N')
                .forEach(faculty -> {
                    try {
                        facultyService.deleteFaculty(faculty.facId());
                        logger.info("Inactive faculty deleted: {}", faculty.facId());
                    } catch (Exception e) {
                        logger.error("Failed to delete faculty {}: {}", faculty.facId(), e.getMessage());
                    }
                });
    }

}