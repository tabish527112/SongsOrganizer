package com.mtk.songsOrganizer;

import java.util.HashSet;
import java.util.Set;

import com.mtk.songsOrganizer.common.dto.CountryDTO;
import com.mtk.songsOrganizer.common.dto.ParamDTO;
import com.mtk.songsOrganizer.common.dto.ReplyDTO;
import com.mtk.songsOrganizer.dbLayer.DBOperations;

public class Test {
	public static void main(String[] args) {
		CountryDTO countryDTO = new CountryDTO();
		countryDTO.setCountryId(1);
		countryDTO.setCountryName("USA99");
		countryDTO.setVisibility(1);
		Set<String> fieldSet = new HashSet<>();
		fieldSet.add("countryName");
		ReplyDTO replyDTO = new DBOperations().addUpdate(new ParamDTO(countryDTO, null, null));
	}
}
