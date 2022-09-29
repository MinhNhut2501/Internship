package com.r2s.findInternship.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r2s.findInternship.common.FileUpload;
import com.r2s.findInternship.dto.ApplyListDTO;
import com.r2s.findInternship.dto.CandidateDTO;
import com.r2s.findInternship.dto.JobDTO;
import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.entity.ApplyList;
import com.r2s.findInternship.exception.InternalServerErrorException;
import com.r2s.findInternship.exception.ResourceNotFound;
//import com.r2s.findInternship.exception.ServiceUnavailableErrorException;
import com.r2s.findInternship.mapstructmapper.MapperApplyList;
import com.r2s.findInternship.repository.ApplyListRepository;
import com.r2s.findInternship.service.ApplyListService;
import com.r2s.findInternship.service.CandidateService;
import com.r2s.findInternship.service.JobService;
import com.r2s.findInternship.util.UpdateFile;

@Service
public class ApplyListServiceImpl implements ApplyListService {

    @Autowired
    private ApplyListRepository applyListRepository;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private JobService jobService;
    @Autowired
    private MapperApplyList mapper;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UpdateFile updateFile;
    @Autowired
    ServletContext application;
    public final static Logger logger = LoggerFactory.getLogger("info");

    @Override
    public ApplyListDTO findById(Integer id) {
        return this.mapper.map(this.getById(id));

    }

    @Override
    public PaginationDTO findByCandidateIdPaging(int candidateID, int no, int limit) {
        List<Object> companies = this.applyListRepository.getApplyListByCandidateId(candidateID).stream()
                .map(item -> this.mapper.map(item)).collect(Collectors.toList());

        PaginationDTO pageDTO = customPagination(companies, no, limit);
        if (pageDTO == null) {
            throw new ResourceNotFound("Apply", "query", null);
        }
        return pageDTO;
    }

    @Override
    public PaginationDTO findByJobIdPaging(int jobId, int no, int limit) {
        List<Object> companies = this.applyListRepository.getApplyListByJobId(jobId).stream()
                .map(item -> this.mapper.map(item)).collect(Collectors.toList());

        PaginationDTO pageDTO = customPagination(companies, no, limit);
        if (pageDTO == null) {
            throw new ResourceNotFound("Apply", "query", null);
        }
        return pageDTO;
    }

    @Override
    public PaginationDTO findByUsernamePaging(String username, int no, int limit) {
        Pageable pageable = PageRequest.of(no, limit);
        List<Object> applyList = this.applyListRepository.findByUsername(username, pageable).toList().stream().map(item -> mapper.map(item)).collect(Collectors.toList());
        Page<ApplyList> applyPage = this.applyListRepository.findByUsername(username, pageable);
        return new PaginationDTO(applyList, applyPage.isFirst(), applyPage.isLast(), applyPage.getTotalPages(),
                applyPage.getTotalElements(), applyPage.getSize(), applyPage.getNumber());
    }

    @Override
    public PaginationDTO findAllPaging(int no, int limit) {
        Pageable pageable = PageRequest.of(no, limit);// Page: 0 and Member: 10
        List<Object> applies = this.applyListRepository.findAll(pageable).toList().stream()
                .map(item -> this.mapper.map(item)).collect(Collectors.toList());
        Page<ApplyList> pageApplyList = this.applyListRepository.findAll(pageable);
        PaginationDTO pageUserDTO = new PaginationDTO(applies, pageApplyList.isFirst(), pageApplyList.isLast(),
                pageApplyList.getTotalPages(), pageApplyList.getTotalElements(), pageApplyList.getSize(), pageApplyList.getNumber());
        return pageUserDTO;
    }

    @Override
    public ApplyListDTO save(ApplyListDTO dto) {


        ApplyList entity = new ApplyList();
        //Save apply
        dto.setCreateDate(LocalDate.now());
        CandidateDTO candidateDTO = candidateService.findById(dto.getCandidate().getId());
        dto.setCandidate(candidateDTO);
        JobDTO jobDTO = jobService.findById(dto.getJobApp().getId());
        dto.setJobApp(jobDTO);
        //Save file CV from profile if CV null
        if (dto.getCV() == null) {
            dto = setCVApplyFromProfile(dto);
        }
        entity = applyListRepository.save(mapper.map(dto));
        logger.info(String.format("Candidate id: %s apply Job id:%s   successfully", dto.getCandidate().getId(), dto.getJobApp().getId()));
        return this.mapper.map(entity);

    }

    // Hàm lấy tên file theo CV của candidate
    public String getFileName(String pathFile) {
        String fileName = new String();
        int position = pathFile.lastIndexOf("/");
        fileName = pathFile.substring(position);
        return fileName;
    }

    @Override
    public ApplyListDTO update(int id, ApplyListDTO dto) {
        ApplyList entity = this.getById(id);
        dto.setId(id);
        CandidateDTO candidateDTO = candidateService.findById(dto.getCandidate().getId());
        dto.setCandidate(candidateDTO);
        JobDTO jobDTO = jobService.findById(dto.getJobApp().getId());
        dto.setJobApp(jobDTO);
        entity = mapper.map(dto);

        try {
        } catch (Exception e) {
            throw new InternalServerErrorException(this.messageSource.getMessage("error.ApplylistSave", null, null));
        }
        applyListRepository.save(entity);
        logger.info(String.format("Apply id: %s update successfully", dto.getId()));
        return this.mapper.map(entity);

    }

    // Set CVApply from profile
    public ApplyListDTO setCVApplyFromProfile(ApplyListDTO dto) {

        //Path of CVApply
        String foulderCVApply = "/CVApply";
        String fileCVApplyPath = application.getRealPath("/") + foulderCVApply;
        String folderParent = String.valueOf(LocalDate.now().getYear());
        //CHECK file is exists. IF IT'S NOT EXISTS, I CREATE FOLDER
        File file = new File(fileCVApplyPath + String.format("/%s/", folderParent));
        if (!file.exists()) {
            file.mkdir();
        }
        //Path save CVApply by this year
        String srcPath = fileCVApplyPath + String.format("/%s/", folderParent);
        String urlStr = "http://localhost:8085" + dto.getCandidate().getCV();
        // Name fileCV of Candidate
        String fileName = getFileName(dto.getCandidate().getCV());
        // Check name file
        java.nio.file.Path mypath = Paths.get(srcPath + fileName);
        int i = 1;
        String applyCV = null;
        String fileNameTemp = null;
        try {
            //RENAME FILE WITH STRING
            while (Files.exists(mypath)) {
                applyCV = fileName.substring(0, fileName.lastIndexOf("."));
                applyCV = applyCV + "(" + i + ")";
                applyCV += fileName.substring(fileName.lastIndexOf("."));
                fileNameTemp = applyCV;
                mypath = Paths.get(srcPath + fileNameTemp);
                i++;
            }


            URL url = new URL(urlStr);
            //Lấy file Cv từ Candidate
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            if (fileNameTemp == null) {
                FileOutputStream fos = new FileOutputStream(srcPath + fileName);
                dto.setCV(foulderCVApply + String.format("/%s", folderParent) + fileName);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                fos.close();
                rbc.close();
            } else {
                FileOutputStream fos = new FileOutputStream(srcPath + fileNameTemp);
                dto.setCV(foulderCVApply + String.format("/%s", folderParent) + fileNameTemp);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                fos.close();
                rbc.close();

            }
        } catch (FileNotFoundException e) {
            throw new ResourceNotFound("File CV", "id of Cadidate", String.valueOf(dto.getCandidate().getId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info(String.format("Apply %s save the CV %s successfully", dto.getCandidate().getId(), dto.getCV()));
        return dto;
    }


    @Override
    public ApplyList getById(Integer id) {
        return applyListRepository.findById(id).orElseThrow(() -> new ResourceNotFound("ApplyList", "id", String.valueOf(id)));

    }

    @Override
    public boolean deleteById(Integer id) {
        try {
            applyListRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFound("ApplyList", "id", String.valueOf(id));

        }
        logger.info(String.format("Apply id : %s successfully delete ", id.toString()));
        return true;
    }

    @Override
    public ApplyListDTO readJson(String value, MultipartFile fileCV) {
        ApplyListDTO applyListDTO = new ApplyListDTO();
        try {
            ObjectMapper ob = new ObjectMapper();
            applyListDTO = ob.readValue(value, ApplyListDTO.class);
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException(this.messageSource.getMessage("error.readJson", null, null));
        }
        if (fileCV != null)// Set file Logo
            applyListDTO.setFileCV(fileCV);

        if (applyListDTO.getFileCV() != null) // Set path of file Logo
        {
            FileUpload file = new FileUpload();
            file.setFile(fileCV);
            this.updateFile.uploadCVApply(file);
            applyListDTO.setCV(file.getOutput());
            ;
            logger.info(String.format("Apply %s save the CV %s successfully", applyListDTO.getCandidate().getId(), file.getOutput()));

        }
        return applyListDTO;
    }

    @Override
    public boolean checkApply(int jobId, int candidateId) {

        for (ApplyList applyList : applyListRepository.findAll()) {

            if (candidateId == (applyList.getCandidate().getId()) && jobId == applyList.getJobApp().getId()) {
                return false;
            }
        }
        return true;

    }

    @Override
    public PaginationDTO customPagination(List<Object> list, int page, int limmit) {
        List<Object> companiesInAPage = new ArrayList<Object>();
        if (list.size() == 0) {
            return null;
        }
        int totalPages = 0;
        int totalItems = list.size();
        boolean first = false;
        boolean last = false;
        totalPages = totalItems / limmit;
        if (totalItems % limmit > 0) {
            totalPages++;
        }
        if (page == 0) {
            first = true;
        }
        if ((page + 1) / totalPages >= 1) {
            last = true;
        }
        if (page >= 0) {
            int n = page * limmit;
            for (int i = n; i < n + limmit & i < list.size(); i++) {
                companiesInAPage.add(list.get(i));
            }
        }
        PaginationDTO pageUserDTO = new PaginationDTO(companiesInAPage, first, last, totalPages, totalItems, limmit, page);
        return pageUserDTO;
    }


}
