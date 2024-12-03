package co.edu.icesi.dev.outcome_curr_mgmt.service.faculty;

import co.edu.icesi.dev.outcome_curr.mgmt.model.stdindto.faculty.FacultyInDTO;
import co.edu.icesi.dev.outcome_curr.mgmt.model.stdoutdto.faculty.FacultyOutDTO;
import co.edu.icesi.dev.outcome_curr_mgmt.exception.OutCurrException;
import co.edu.icesi.dev.outcome_curr_mgmt.exception.OutCurrExceptionType;
import co.edu.icesi.dev.outcome_curr_mgmt.mapper.faculty.FacultyMapper;
import co.edu.icesi.dev.outcome_curr_mgmt.model.entity.faculty.Faculty;
import co.edu.icesi.dev.outcome_curr_mgmt.model.enums.ChangeLogAction;
import co.edu.icesi.dev.outcome_curr_mgmt.persistence.faculty.FacultyRepository;
import co.edu.icesi.dev.outcome_curr_mgmt.service.provider.faculty.FacultyProvider;
import co.edu.icesi.dev.outcome_curr_mgmt.service.validator.faculty.UserPermAccess;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {
    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;
    private final FacultyProvider facultyProvider;
    private final MeterRegistry meterRegistry;

    private Counter createFacultyCounter;
    private Counter updateFacultyCounter;
    private Counter deleteFacultyCounter;
    private Timer getFacultyTimer;

    @PostConstruct
    public void initMetrics() {
        createFacultyCounter = meterRegistry.counter("faculty.create.count", "type", "create");
        updateFacultyCounter = meterRegistry.counter("faculty.update.count", "type", "update");
        deleteFacultyCounter = meterRegistry.counter("faculty.delete.count", "type", "delete");
        getFacultyTimer = meterRegistry.timer("faculty.get.time", "type", "get");
    }

    @Override
    @Transactional
    public FacultyOutDTO createFaculty(FacultyInDTO facultyInDTO) {
        MDC.put("operation", "CREATE");
        MDC.put("facultyName", facultyInDTO.facNameEng());
        logger.info("Creating a new faculty with name: {}", facultyInDTO.facNameEng());
        try {
            FacultyOutDTO result = facultyProvider.saveFaculty(facultyInDTO);
            createFacultyCounter.increment();
            logger.info("Faculty successfully created. ID: {}, Name: {}", result.facId(), result.facNameEng());
            MDC.put("status", "success");
            return result;
        } catch (Exception e) {
            MDC.put("status", "error");
            MDC.put("errorType", e.getClass().getSimpleName());
            logger.error("Error creating faculty: {}", e.getMessage(), e);
            throw e;
        } finally {
            MDC.clear();
        }
    }

    @Override
    @Transactional
    public FacultyOutDTO getFacultyByFacId(long facId) {
        MDC.put("operation", "GET");
        MDC.put("facultyId", String.valueOf(facId));
        logger.info("Getting a faculty by its id: {}", facId);
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            facultyProvider.validateAccess(0L, UserPermAccess.QUERY);
            FacultyOutDTO result = facultyMapper.facultyToFacultyOutDTO(facultyProvider.findFacultyByFacId(facId));
            MDC.put("status", "success");
            logger.info("Faculty retrieved successfully. ID: {}, Name: {}", result.facId(), result.facNameEng());
            return result;
        } catch (Exception e) {
            MDC.put("status", "error");
            MDC.put("errorType", e.getClass().getSimpleName());
            logger.error("Error retrieving faculty: {}", e.getMessage(), e);
            throw e;
        } finally {
            sample.stop(getFacultyTimer);
            MDC.clear();
        }
    }

    @Override
    @Transactional
    public FacultyOutDTO updateFaculty(long facId, FacultyInDTO facultyToUpdate) {
        MDC.put("operation", "UPDATE");
        MDC.put("facultyId", String.valueOf(facId));
        logger.info("Updating the faculty with ID: {}", facId);
        try {
            facultyProvider.validateAccess(facId, UserPermAccess.ADMIN);
            facultyProvider.checkIfSpaNameIsAlreadyUsed(facultyToUpdate.facNameSpa());
            facultyProvider.checkIfEngNameIsAlreadyUsed(facultyToUpdate.facNameEng());

            Faculty faculty = facultyProvider.findFacultyByFacId(facId);
            FacultyOutDTO facultyBefore = facultyMapper.facultyToFacultyOutDTO(faculty);

            faculty.setFacIsActive(facultyToUpdate.isActive().charAt(0));
            faculty.setFacNameSpa(facultyToUpdate.facNameSpa());
            faculty.setFacNameEng(facultyToUpdate.facNameEng());

            facultyRepository.save(faculty);
            facultyProvider.addActionToChangelog(ChangeLogAction.UPDATE, facId, "FACULTY", faculty, facultyBefore);
            updateFacultyCounter.increment();
            logger.info("Faculty successfully updated. ID: {}, New Name: {}", faculty.getFacId(), faculty.getFacNameEng());
            MDC.put("status", "success");
            return facultyMapper.facultyToFacultyOutDTO(faculty);
        } catch (Exception e) {
            MDC.put("status", "error");
            MDC.put("errorType", e.getClass().getSimpleName());
            logger.error("Error updating faculty: {}", e.getMessage(), e);
            throw e;
        } finally {
            MDC.clear();
        }
    }

    @Override
    @Transactional
    public void deleteFaculty(long facId) {
        MDC.put("operation", "DELETE");
        MDC.put("facultyId", String.valueOf(facId));
        logger.info("Deleting a faculty with ID: {}", facId);
        try {
            facultyProvider.validateAccess(facId, UserPermAccess.ADMIN);
            Faculty facultyToDelete = facultyProvider.findFacultyByFacId(facId);

            if (facultyToDelete.getAcadPrograms().isEmpty() && facultyToDelete.getCourses().isEmpty()) {
                facultyRepository.delete(facultyToDelete);
                facultyProvider.addActionToChangelog(ChangeLogAction.DELETE, facId, "FACULTY", null, facultyToDelete);
                deleteFacultyCounter.increment();
                logger.info("Faculty {} was successfully deleted.", facId);
                MDC.put("status", "success");
            } else {
                MDC.put("status", "error");
                MDC.put("errorType", "ValidationException");
                logger.error("Error: a faculty can't be deleted if it has associated data. Faculty ID: {}", facId);
                throw new OutCurrException(OutCurrExceptionType.FACULTY_NOT_DELETED);
            }
        } catch (Exception e) {
            MDC.put("status", "error");
            MDC.put("errorType", e.getClass().getSimpleName());
            logger.error("Error deleting faculty: {}", e.getMessage(), e);
            throw e;
        } finally {
            MDC.clear();
        }
    }

    @Override
    public FacultyOutDTO getFacultyByFacNameInSpa(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFacultyByFacNameInSpa'");
    }

    @Override
    public FacultyOutDTO getFacultyByFacNameInEng(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFacultyByFacNameInEng'");
    }

    @Override
    public List<FacultyOutDTO> getFaculties() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFaculties'");
    }
}
