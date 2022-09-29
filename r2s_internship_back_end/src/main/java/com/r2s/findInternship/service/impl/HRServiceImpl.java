package com.r2s.findInternship.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.r2s.findInternship.dto.*;
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
import com.r2s.findInternship.common.ERole;
import com.r2s.findInternship.common.Estatus;
import com.r2s.findInternship.common.FileUpload;
import com.r2s.findInternship.dto.show.HRDTOShow;
import com.r2s.findInternship.entity.HR;
import com.r2s.findInternship.entity.User;
import com.r2s.findInternship.exception.InternalServerErrorException;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperHR;
import com.r2s.findInternship.mapstructmapper.MapperUser;
import com.r2s.findInternship.repository.HRRepository;
import com.r2s.findInternship.repository.UserRepository;
import com.r2s.findInternship.service.CompanyService;
import com.r2s.findInternship.service.HRService;
import com.r2s.findInternship.service.RoleService;
import com.r2s.findInternship.service.StatusService;
import com.r2s.findInternship.service.UserService;
import com.r2s.findInternship.util.UpdateFile;


@Service
public class HRServiceImpl implements HRService {

    @Autowired
    private HRRepository hrRepository;
    @Autowired
    private MapperHR mapper;
    @Autowired
    private MapperUser mapperUser;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UpdateFile updateFile;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StatusService statusService;
    public final static Logger logger = LoggerFactory.getLogger("info");


    @Override
    public HRDTO findById(Integer id) {
        return this.mapper.map(this.getById(id));
    }

    @Override
    public HRDTOShow findByIdToShow(Integer id) {
        return this.mapper.mapHRShow(this.getById(id));
    }

    @Override
    public List<HRDTOShow> findAll() {
        return hrRepository.findAll().stream().map(hr -> this.mapper.mapHRShow(hr)).collect(Collectors.toList());
    }

    @Override
    public PaginationDTO findAllPagination(int no, int limit) {
        Pageable pageable = PageRequest.of(no, limit);
        List<Object> HRList = hrRepository.findAll(pageable).toList().stream().map(item -> mapper.mapHRShow(item)).collect(Collectors.toList());
        Page<HR> HRPage = hrRepository.findAll(pageable);
        return new PaginationDTO(HRList, HRPage.isFirst(), HRPage.isLast(), HRPage.getTotalPages(),
                HRPage.getTotalElements(), HRPage.getSize(), HRPage.getNumber());
    }


    @Override
    public HRDTO save(HRDTO dto) {
        if (dto.getUser() == null)// Khong co user thi throw loi
        {
            throw new InternalServerErrorException(this.messageSource.getMessage("error.userMust", null, null));
        }
        //set role
        // Check company
        CompanyDTO companyDTO = companyService.findById(dto.getCompany().getId());
        dto.setCompany(companyDTO);
        // set Avatar
        if (dto.getUser().getFileAvatar() != null)// TRUONG HOP CO FILE UPLOAD
        {
            FileUpload fileAva = new FileUpload();
            fileAva.setFile(dto.getUser().getFileAvatar());
            this.updateFile.update(fileAva);
            dto.getUser().setAvatar(fileAva.getOutput());
        }
        dto.getUser().setRole(roleService.findByName(ERole.HR));
        dto.getUser().setStatus(this.statusService.findByName(Estatus.Active));
        UserDTO userDTO = this.userService.save(dto.getUser());//Luu
        User user = this.userService.findByUsername(userDTO.getUsername());
        HR hr = this.mapper.map(dto);
        hr.setUser(user);
        logger.info(String.format("HR saved with username: %s", hr.getUser().getUsername()));
        Optional<HR> entity = this.hrRepository.findByUserName(hr.getUser().getUsername());
        if (entity.isPresent()) {
            hr.setId(entity.get().getId());
        }
        return this.mapper.map(this.hrRepository.save(hr));

    }

    @Override
    public HRDTOShow update(int id, HRDTO dto) {
        HRDTO hrDTO = this.findById(id);
        dto.getUser().setPassword(hrDTO.getUser().getPassword());
        dto.getUser().setConfirmPassword(hrDTO.getUser().getConfirmPassword());;
        HR entity = this.mapper.map(dto);
        entity.setId(hrDTO.getId());

        if (dto.getUser() != null) {
            if (!this.hrRepository.findByUserName(dto.getUser().getUsername()).isPresent())//check user is Candidate?
                throw new ResourceNotFound("HR", "User", dto.getUser().getUsername());
            User user = this.userService.findByUsername(dto.getUser().getUsername());
            if (dto.getUser().getFileAvatar() != null)// CASE UPLOAD AVATAR
            {
                FileUpload fileUp = new FileUpload();
                fileUp.setFile(dto.getUser().getFileAvatar());
                String url = dto.getUser().getAvatar();
                if (url != null)
                    this.updateFile.deleteFile(url);
                this.updateFile.update(fileUp);
                user.setAvatar(fileUp.getOutput());
                // UPDATE CANDIDATE
            }

            user.setAvatar(user.getAvatar());
            user.setFirstName(dto.getUser().getFirstName());
            user.setLastName(dto.getUser().getLastName());
            user.setPhone(dto.getUser().getPhone());
            user.setGender(dto.getUser().getGender());
            if (!user.getEmail().equals(dto.getUser().getEmail())) {

                if (userService.existsByEmail(dto.getUser().getEmail())) {
                    throw new InternalServerErrorException(this.messageSource.getMessage("error.emailExists", null, null));
                }

            }
            user.setEmail(dto.getUser().getEmail());
            UserCreationDTO userCreationDTO = this.mapperUser.mapToCreate(user);
            entity.setUser(this.mapperUser.map(this.userService.update(userCreationDTO)));// CAP NHAT USER
        } else
            throw new InternalServerErrorException(messageSource.getMessage("error.usernameNotNull", null, null));

        logger.info("Update Candidate with username: " + entity.getUser().getUsername());
        return this.mapper.mapHRShow(this.hrRepository.save(entity));


    }

    @Override
    public HR getById(Integer id) {
        return hrRepository.findById(id).orElseThrow(() -> new ResourceNotFound("HR not found with id: " + id));

    }

    @Override
    public void deleteById(Integer id) {
        hrRepository.deleteById(id);
    }

    public void delete(HR entity) {
        hrRepository.delete(entity);
    }

    @Override
    public HRDTO readJson(String value, MultipartFile fileAvt) {
        HRDTO hrdto = new HRDTO();
        try {
            ObjectMapper ob = new ObjectMapper();
            hrdto = ob.readValue(value, HRDTO.class);
        } catch (JsonProcessingException ex) // LOG ERROR
        {
            ex.printStackTrace();
        } catch (Exception e) {
            throw new InternalServerErrorException(this.messageSource.getMessage("error.readJson", null, null));
        }
        if (fileAvt != null) {
            hrdto.getUser().setFileAvatar(fileAvt);
        }
        return hrdto;
    }

    @Override
    public HRDTOShow findByUserId(long id) {
        if (hrRepository.findByUserId(id) == null) {
            throw new ResourceNotFound("HR", "id of User", String.valueOf(id));
        }
        return mapper.mapHRShow(hrRepository.findByUserId(id));

    }

}
