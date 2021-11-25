package com.tmax.eTest.Admin.ManageUser.model.dto;

import com.tmax.eTest.Contents.dto.ListDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserContentsPopupDTO {

    private ListDTO.Video video;
    private ListDTO.Book book;
    private ListDTO.Wiki wiki;
    private ListDTO.Article article;

}
