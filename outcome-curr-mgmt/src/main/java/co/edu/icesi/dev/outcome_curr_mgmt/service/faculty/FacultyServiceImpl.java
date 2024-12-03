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
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {
    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;
    private final FacultyProvider facultyProvider;
    private final MeterRegistry meterRegistry;

    @Override
    @Transactional
    public FacultyOutDTO createFaculty(FacultyInDTO facultyInDTO) {
        MDC.put("operation", "CREATE");
        logger.info("Creating a new faculty.");
        try {
            FacultyOutDTO result = facultyProvider.saveFaculty(facultyInDTO);
            logger.info("Faculty successfully created. {}", result);
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
        logger.info("Getting a faculty by its id.");
        try {
            facultyProvider.validateAccess(0L, UserPermAccess.QUERY);
            FacultyOutDTO result = facultyMapper.facultyToFacultyOutDTO(facultyProvider.findFacultyByFacId(facId));
            MDC.put("status", "success");
            logger.info("Faculty retrieved successfully. {}", result);
            return result;
        } catch (Exception e) {
            MDC.put("status", "error");
            MDC.put("errorType", e.getClass().getSimpleName());
            logger.error("Error retrieving faculty: {}", e.getMessage(), e);
            throw e;
        } finally {
            MDC.clear();
        }
    }

    @Override
    @Transactional
    public FacultyOutDTO updateFaculty(long facId, FacultyInDTO facultyToUpdate) {
        MDC.put("operation", "UPDATE");
        MDC.put("facultyId", String.valueOf(facId));
        logger.info("Updating the faculty {}.", facId);
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
            logger.info("Faculty successfully updated. {}", faculty);
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
        logger.info("Deleting a faculty.");
        try {
            facultyProvider.validateAccess(facId, UserPermAccess.ADMIN);
            Faculty facultyToDelete = facultyProvider.findFacultyByFacId(facId);

            if (facultyToDelete.getAcadPrograms().isEmpty() && facultyToDelete.getCourses().isEmpty()) {
                facultyRepository.delete(facultyToDelete);
                facultyProvider.addActionToChangelog(ChangeLogAction.DELETE, facId, "FACULTY", null, facultyToDelete);
                logger.info("Faculty {} was successfully deleted.", facId);
                MDC.put("status", "success");
            } else {
                MDC.put("status", "error");
                MDC.put("errorType", "ValidationException");
                logger.error("Error: a faculty can't be deleted if it has associated data.");
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
