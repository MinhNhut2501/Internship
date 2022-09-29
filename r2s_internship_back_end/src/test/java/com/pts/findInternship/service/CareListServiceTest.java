
package com.pts.findInternship.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.r2s.findInternship.dto.CandidateDTO;
import com.r2s.findInternship.dto.CareListDTO;
import com.r2s.findInternship.dto.JobDTO;
import com.r2s.findInternship.dto.UserDetailsDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import com.r2s.findInternship.dto.show.ResponeMessage;
import com.r2s.findInternship.entity.Candidate;
import com.r2s.findInternship.entity.CareList;
import com.r2s.findInternship.entity.Job;
import com.r2s.findInternship.entity.User;
import com.r2s.findInternship.exception.ExceptionCustom;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperCandidate;
import com.r2s.findInternship.mapstructmapper.MapperCareList;
import com.r2s.findInternship.mapstructmapper.MapperJob;
import com.r2s.findInternship.repository.CandidateRepository;
import com.r2s.findInternship.repository.CareListRepository;
import com.r2s.findInternship.repository.JobRepository;
import com.r2s.findInternship.service.impl.CareListServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CareListServiceTest {
    @Mock
    private MessageSource messageSource;
    @Mock
    private MapperCareList mapperCareList;
    @Mock
    private MapperCandidate mapperCandidate;
    @Mock
    private MapperJob mapperJob;
    @Mock
    private CareListRepository careListRepository;
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private JobRepository jobRepository;
    @InjectMocks
    private CareListServiceImpl careListService;
    private static final List<Job> jobs = new ArrayList<>();
    private static final List<JobDTO> jobDTOs = new ArrayList<>();
    private static final List<Candidate> candidates = new ArrayList<>();
    private static final List<CandidateDTO> candidateDTOs = new ArrayList<>();
    private static final List<CareList> cares = new ArrayList<>();
    private static final List<CareListDTO> careDTOs = new ArrayList<>();
    private void assertActualWithExpected(CareListDTO actual, CareListDTO expected) {
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getNote()).isEqualTo(expected.getNote());
        assertThat(actual.getJobCare().getId()).isEqualTo(expected.getJobCare().getId());
    }
    @BeforeAll
    static void setUp() {
        for (int i = 1; i <= 3; ++i) {
            User user = new User(); user.setId(i); user.setUsername("user" + i);
            Candidate candidate = new Candidate(); candidate.setId(i); candidate.setUser(user);
            UserDetailsDTO userDetailsDTO = new UserDetailsDTO(); userDetailsDTO.setId(i); userDetailsDTO.setUsername("user" + i);
            CandidateDTO candidateDTO = new CandidateDTO(); candidateDTO.setId(i); candidateDTO.setUser(userDetailsDTO);
            candidates.add(candidate);
            candidateDTOs.add(candidateDTO);
        }
        for (int i = 1; i <= 3; ++i) {
            Job job = new Job(); job.setId(i); job.setName("job" + i);
            job.setCreateDate(LocalDate.of(2022, 7, 13)); job.setTimeStart(LocalDate.of(2022, 8, 13)); job.setTimeEnd(LocalDate.of(2022, 10, 13));
            JobDTO jobDTO = new JobDTO(); jobDTO.setId(i); jobDTO.setName("job" + i);
            jobDTO.setCreateDate(LocalDate.of(2022, 7, 13)); jobDTO.setTimeStartStr(LocalDate.of(2022, 8, 13).toString()); jobDTO.setTimeEndStr(LocalDate.of(2022, 10, 13).toString());
            jobs.add(job);
            jobDTOs.add(jobDTO);
        }
        int id = 1;
        for (int i = 0; i < candidates.size(); ++i) {
            for (int j = 0; j < jobs.size(); ++j) {
                cares.add(new CareList(id, jobs.get(j), candidates.get(i), LocalDate.now(), "care" + id));
                careDTOs.add(new CareListDTO(id, jobDTOs.get(j), candidateDTOs.get(i), LocalDate.now(), "care" + id));
                id++;
            }
        }
    }
    // when passed
    @DisplayName("JUnit test for find by Id method")
    @Test
    void testFindById_shouldReturnCareDTO_whenCallingFindByIdSuccesfully() {
        // fake data
        CareList care = cares.get(0);
        CareListDTO careDTO = careDTOs.get(0);
        // cover the service
        Mockito.when(careListRepository.findById(1)).thenReturn(Optional.of(care));
        Mockito.when(mapperCareList.map(care)).thenReturn(careDTO);
        // get the actual result
        CareListDTO actualResult = careListService.findById(1);
        // check the service
        assertActualWithExpected(actualResult, careDTO);
    }
    @DisplayName("JUnit test for create method")
    @Test
    void testCreateACare_shouldReturnCareDTO_whenAllConditionIsSatisfiedAndCallingSaveSuccesfully() {
        // fake data
        Candidate candidate = candidates.get(0);
        CandidateDTO candidateDTO = candidateDTOs.get(0);
        JobDTO jobDTO = jobDTOs.get(0);
        Job job = jobs.get(0);
        CareList care = cares.get(0);
        CareListDTO careDTO = careDTOs.get(0);
        // cover the service
        Mockito.when(candidateRepository.findById(anyInt())).thenReturn(Optional.of(candidate));
        Mockito.when(jobRepository.findById(anyInt())).thenReturn(Optional.of(job));
        Mockito.when(mapperCandidate.map(any(Candidate.class))).thenReturn(candidateDTO);
        Mockito.when(mapperJob.map(any(Job.class))).thenReturn(jobDTO);
        Mockito.when(careListRepository.existsByUsernameAndJobId(any(String.class), anyInt())).thenReturn(false);
        Mockito.when(mapperCareList.map(any(CareListDTO.class))).thenReturn(care);
        Mockito.when(careListRepository.save(any(CareList.class))).thenReturn(care);
        Mockito.when(mapperCareList.map(any(CareList.class))).thenReturn(careDTO);
        // get the actual result
        CareListDTO actualResult = careListService.save(careDTO);
        // check the service with assert
        assertActualWithExpected(actualResult, careDTO);
    }
    @DisplayName("JUnit test for find all method")
    @Test
    void testFindAll_shouldReturnCareDTOList_whenCallingFindAllSuccesfully() {
        Mockito.when(careListRepository.findAll()).thenReturn(cares);
        for (int i = 0; i < cares.size(); ++i)
            Mockito.when(mapperCareList.map(cares.get(i))).thenReturn(careDTOs.get(i));

        List<CareListDTO> actualResult = careListService.findAll();
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.size()).isEqualTo(careDTOs.size());
        for (int i = 0; i < actualResult.size(); ++i)
            assertActualWithExpected(actualResult.get(i), careDTOs.get(i));
    }
    @DisplayName("JUnit test for update method")
    @Test
    void testUpdateCare_shouldReturnCareDTO_whenAllConditionIsSatisfiedAndCallingUpdateSuccesfully() {
        CareList oldCare = cares.get(0);
        CareList newCare = cares.get(1); newCare.setId(1);
        CareListDTO newCareDTO = careDTOs.get(1); newCareDTO.setId(1);

        Mockito.when(careListRepository.findById(anyInt())).thenReturn(Optional.of(oldCare));
        Mockito.when(careListRepository.save(any(CareList.class))).thenReturn(newCare);
        Mockito.when(mapperCareList.map(any(CareList.class))).thenReturn(newCareDTO);

        CareListDTO actualResult = careListService.update(1, newCareDTO);

        assertActualWithExpected(actualResult, newCareDTO);

        newCare.setId(2); newCareDTO.setId(2); newCareDTO.setId(2);
    }
    @DisplayName("JUnit test for find by username method")
    @Test
    void testFindByUsername_shouldReturnCareDTO_whenCallingFindByUsernameSuccesfully() {
        List<CareList> careByUsers = new ArrayList<>();
        List<CareListDTO> careByUserDTOShows = new ArrayList<>();
        for (int i = 0; i < cares.size(); ++i) {
            if (cares.get(i).getCandidateCare().getUser().getUsername().equals("user1")) {
                careByUsers.add(cares.get(i));
                careByUserDTOShows.add(careDTOs.get(i));
            }
        }

        Mockito.when(careListRepository.findAllByUsername(any(String.class))).thenReturn(careByUsers);
        for (int i = 0; i < careByUsers.size(); ++i)
            Mockito.when(mapperCareList.map(careByUsers.get(i))).thenReturn(careByUserDTOShows.get(i));

        List<CareListDTO> actualResult = careListService.findByUsername("user1");

        assertThat(actualResult.size()).isEqualTo(careByUserDTOShows.size());
        for (int i = 0; i < actualResult.size(); ++i)
            assertActualWithExpected(actualResult.get(i), careByUserDTOShows.get(i));
    }
    @DisplayName("JUnit test for find by username and job id method")
    @Test
    void testFindByUsernameAndJobId_shouldReturnCareDTO_whenAllConditionsSatisfiedAndCallingFindByUsernameAndJobIdSuccesfully() {
        CareList care = cares.get(0);
        CareListDTO careDTOShow = careDTOs.get(0);

        Mockito.when(careListRepository.findByUsernameAndJobId(anyString(), anyInt())).thenReturn(Optional.of(care));
        Mockito.when(mapperCareList.map(any(CareList.class))).thenReturn(careDTOShow);

        CareListDTO actualResult = careListService.findByUsernameAndJobId("user1", 1);

        assertActualWithExpected(actualResult, careDTOShow);
    }
    @DisplayName("JUnit test for delete method")
    @Test
    void testDeleteById_shouldReturnResponseMessage_whenAllConditionsSatisfiedAndCallingDeleteByIdSuccesfully() {
        CareList care = cares.get(0);
        String contextMessage = "Candidate %s đã xóa một công việc khỏi danh sách quan tâm";
        String message = String.format(contextMessage, care.getCandidateCare().getUser().getUsername());
        ResponeMessage expectedResponse = new ResponeMessage(200, message);

        Mockito.when(careListRepository.findById(anyInt())).thenReturn(Optional.of(care));
        Mockito.doNothing().when(careListRepository).deleteById(anyInt());
        Mockito.when(messageSource.getMessage(any(), any(), any())).thenReturn(message);

        ResponeMessage responeMessage = careListService.deleteById(1);

        Mockito.verify(careListRepository, Mockito.times(1)).deleteById(1);
        assertThat(responeMessage.getMessage()).isEqualTo(expectedResponse.getMessage());
    }
    // when fail
    @DisplayName("JUnit test for find by id and throw an exception")
    @Test
    void testFindById_shouldThrowException_whenFindByIdThrowException() {
        String exceptionNotFoundWithId = "CareList not found with id: 1";
        Mockito.when(careListRepository.findById(10)).thenThrow(new ResourceNotFound("CareList", "id", "1"));
        Throwable actualException = assertThrows(ResourceNotFound.class, () -> careListService.findById(10));
        assertThat(actualException).isNotNull();
        assertThat(actualException.toString()).isEqualTo(exceptionNotFoundWithId);
    }
    @DisplayName("JUnit test for find by username and job id then throw an exception")
    @Test
    void testFindByUsernameAndJobId_shouldThrowException_whenFindByUsernameAndJobIdCalled() {
        String exceptionNotFoundByUsernameAndJobId = "CareList not found with (username, jobId): (abcdef, 10)";
        Mockito.when(careListRepository.findByUsernameAndJobId("abcdef", 10)).thenThrow(new ResourceNotFound("CareList", "(username, jobId)", "(abcdef, 10)"));
        Throwable actualException = assertThrows(ResourceNotFound.class, () -> careListService.findByUsernameAndJobId("abcdef", 10));
        assertThat(actualException).isNotNull();
        assertThat(actualException.toString()).isEqualTo(exceptionNotFoundByUsernameAndJobId);
    }

    @DisplayName("JUnit test for save when find by candidate Id then throw an exception")
    @Test
    void testSaveCare_shouldReturnException_whenFindByCandidateIdFailed() {
        CandidateDTO candidateDTO = new CandidateDTO(); candidateDTO.setId(10);
        JobDTO jobDTO = new JobDTO(); jobDTO.setId(1);
        CareListDTO careDTO = new CareListDTO();
        careDTO.setCandidateCare(candidateDTO);
        careDTO.setJobCare(jobDTO);
        Mockito.when(candidateRepository.findById(10)).thenThrow(new ResourceNotFound("Candidate", "id", "10"));
        assertThrows(ResourceNotFound.class, () -> careListService.save(careDTO));
    }
    @DisplayName("JUnit test for add a care and throw an exception when find the job after found the candidate")
    @Test
    void testSaveCare_shouldReturnException_whenFindByCandidateIdSuccessAndFindByJobIdFailed() {
        String exception = "Job not found with id: 10";

        CandidateDTO candidateDTO = new CandidateDTO(); candidateDTO.setId(1);
        JobDTO jobDTO = new JobDTO(); jobDTO.setId(10);
        Candidate candidate = new Candidate(); candidate.setId(1);

        CareListDTO careDTO = new CareListDTO();
        careDTO.setCandidateCare(candidateDTO);
        careDTO.setJobCare(jobDTO);

        Mockito.when(candidateRepository.findById(1)).thenReturn(Optional.of(candidate));
        Mockito.when(jobRepository.findById(10)).thenThrow(new ResourceNotFound("Job", "id", "10"));
        Throwable actualException = assertThrows(ResourceNotFound.class, () -> careListService.save(careDTO));
        assertThat(actualException).isNotNull();
        assertThat(actualException.toString()).isEqualTo(exception);
    }
    @DisplayName("JUnit test for add a care and throw an exception")
    @Test
    void testAddCare_shouldThrowAnException_whenFindByUsernameAndJobIdFailed() {
        Job job = new Job(); job.setId(1);
        User user = new User(); user.setUsername("user1");
        Candidate candidate = new Candidate(); candidate.setId(1); candidate.setUser(user);

        JobDTO jobDTO = new JobDTO(); jobDTO.setId(1);
        UserDetailsDTO userDTO = new UserDetailsDTO(); userDTO.setUsername("user1");
        CandidateDTO candidateDTO = new CandidateDTO(); candidateDTO.setId(1); candidateDTO.setUser(userDTO);
        CareListDTO careDTO = new CareListDTO(); careDTO.setJobCare(jobDTO); careDTO.setCandidateCare(candidateDTO);
        CareList care = new CareList(); care.setJobCare(job); care.setCandidateCare(candidate);

        Mockito.when(candidateRepository.findById(1)).thenReturn(Optional.of(candidate));
        Mockito.when(jobRepository.findById(1)).thenReturn(Optional.of(job));
        Mockito.when(careListRepository.existsByUsernameAndJobId("user1", 1)).thenReturn(true);

        assertThrows(ExceptionCustom.class, () -> careListService.save(careDTO));
        Mockito.verify(careListRepository, Mockito.times(0)).save(care);
    }
    @DisplayName("JUnit test for find a care by username and job id then throw an exception")
    @Test 
    void testUpdateCare_shouldThrowAnException_whenFindByIdCareFailed() {
        // wrong data
        CareListDTO careListDTO = new CareListDTO();
        // cover the find by id
        Mockito.when(careListRepository.findById(10)).thenThrow(new ResourceNotFound("CareList", "id", "10"));
        assertThrows(ResourceNotFound.class, () -> careListService.update(10, careListDTO));
    }
}
