package com.r2s.findInternship.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PartnerCreateDTO extends PartnerDTO {
	private UserCreationDTO userCreationDTO;
}
