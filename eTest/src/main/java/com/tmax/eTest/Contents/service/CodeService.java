package com.tmax.eTest.Contents.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.tmax.eTest.Common.model.meta.MetaCodeMaster;
import com.tmax.eTest.Common.repository.meta.MetaCodeMasterRepository;
import com.tmax.eTest.Contents.dto.CodeCreateDTO;
import com.tmax.eTest.Contents.dto.CodeDTO;
import com.tmax.eTest.Contents.exception.ContentsException;
import com.tmax.eTest.Contents.exception.ErrorCode;
import com.tmax.eTest.Contents.repository.support.MetaCodeMasterRepositorySupport;
import com.tmax.eTest.Contents.util.CommonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CodeService {

  @Autowired
  private CommonUtils commonUtils;

  @Autowired
  private MetaCodeMasterRepositorySupport metaCodeMasterRepositorySupport;

  @Autowired
  private MetaCodeMasterRepository metaCodeMasterRepository;

  public List<CodeDTO> getCodeList(String domain) {
    List<MetaCodeMaster> metaCodeMasters = metaCodeMasterRepositorySupport.findMetaCodesByDomain(domain);
    return convertCodeDTO(metaCodeMasters);
  }

  @Transactional
  public CodeDTO createCode(CodeCreateDTO codeCreateDTO) {
    String domain = codeCreateDTO.getDomain();
    String code = codeCreateDTO.getCode();
    String codeId = domain + code;

    MetaCodeMaster metaCodeMaster = MetaCodeMaster.builder().metaCodeId(codeId).code(code).domain(domain)
        .codeName(codeCreateDTO.getName()).build();

    MetaCodeMaster createCode = null;
    if (metaCodeMasterRepository.existsById(codeId))
      throw new ContentsException(ErrorCode.DB_ERROR, "Code already exists in Meta Code Table");
    else
      createCode = metaCodeMasterRepository.save(metaCodeMaster);
    return convertCodeToDTO(createCode);
  }

  @Transactional
  public CodeDTO updateCode(String codeId, CodeCreateDTO codeCreateDTO) {
    MetaCodeMaster metaCodeMaster = metaCodeMasterRepositorySupport.findMetaCodeById(codeId);
    metaCodeMaster.setCodeName(codeCreateDTO.getName());
    MetaCodeMaster updateCode = metaCodeMasterRepository.save(metaCodeMaster);
    return convertCodeToDTO(updateCode);
  }

  public CodeDTO convertCodeToDTO(MetaCodeMaster metaCodeMaster) {
    return CodeDTO.builder().codeId(metaCodeMaster.getMetaCodeId()).code(metaCodeMaster.getCode())
        .domain(metaCodeMaster.getDomain()).name(metaCodeMaster.getCodeName()).build();
  }

  public List<CodeDTO> convertCodeDTO(List<MetaCodeMaster> metaCodeMasters) {
    return metaCodeMasters.stream().map(e -> convertCodeToDTO(e)).collect(Collectors.toList());
  }
}
