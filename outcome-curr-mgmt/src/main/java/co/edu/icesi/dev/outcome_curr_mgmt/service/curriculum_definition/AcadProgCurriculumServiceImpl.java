package co.edu.icesi.dev.outcome_curr_mgmt.service.curriculum_definition;

import co.edu.icesi.dev.outcome_curr_mgmt.model.entity.curriculum_definition.AcadProgCurriculum;
import co.edu.icesi.dev.outcome_curr_mgmt.persistence.curriculum_definition.AcadProgCurriculumRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcadProgCurriculumServiceImpl implements AcadProgCurriculumService {

    private static final Logger logger = LoggerFactory.getLogger(AcadProgCurriculumServiceImpl.class);

    private final AcadProgCurriculumRepository acadProgCurriculumRepository;

    @Override
    public AcadProgCurriculum findAcadProgCurriculumById(long id) {
        MDC.put("operation", "findAcadProgCurriculumById");
        MDC.put("acadProgCurriculumId", String.valueOf(id));

        logger.info("Starting search for AcadProgCurriculum by ID.");

        return acadProgCurriculumRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("AcadProgCurriculum not found for ID: {}", id);
                    MDC.clear();
                    throw new RuntimeException("AcadProgCurriculum not found");
                });
    }
}
